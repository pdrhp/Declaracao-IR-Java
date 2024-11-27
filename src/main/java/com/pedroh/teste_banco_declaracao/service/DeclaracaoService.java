package com.pedroh.teste_banco_declaracao.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pedroh.teste_banco_declaracao.domain.enums.StatusDeclaracao;
import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;
import com.pedroh.teste_banco_declaracao.domain.model.Contribuinte;
import com.pedroh.teste_banco_declaracao.domain.model.Declaracao;
import com.pedroh.teste_banco_declaracao.domain.model.Deducao;
import com.pedroh.teste_banco_declaracao.domain.model.Rendimento;
import com.pedroh.teste_banco_declaracao.dto.request.DeclaracaoDTO;
import com.pedroh.teste_banco_declaracao.dto.response.ConsultaDeclaracaoDTO;
import com.pedroh.teste_banco_declaracao.dto.response.DeclaracaoResponseDTO;
import com.pedroh.teste_banco_declaracao.exception.BusinessException;
import com.pedroh.teste_banco_declaracao.exception.ResourceNotFoundException;
import com.pedroh.teste_banco_declaracao.repository.ContribuinteRepository;
import com.pedroh.teste_banco_declaracao.repository.DeclaracaoRepository;
import com.pedroh.teste_banco_declaracao.util.ContribuinteValidator;
import com.pedroh.teste_banco_declaracao.util.DeclaracaoValidator;

import jakarta.transaction.Transactional;

@Service
public class DeclaracaoService {
    private final ContribuinteRepository contribuinteRepository;
    private final DeclaracaoRepository declaracaoRepository;
    private final CalculadoraImpostoService calculadoraImpostoService;

    public DeclaracaoService (
            ContribuinteRepository contribuinteRepository,
            DeclaracaoRepository declaracaoRepository,
            CalculadoraImpostoService calculadoraImpostoService
    ){
        this.contribuinteRepository = contribuinteRepository;
        this.declaracaoRepository = declaracaoRepository;
        this.calculadoraImpostoService = calculadoraImpostoService;
    }


    @Transactional
    public DeclaracaoResponseDTO submitDeclaracao(DeclaracaoDTO dto){
        DeclaracaoValidator.validar(dto);

        Contribuinte contribuinte = contribuinteRepository.findByCpf(dto.cpfContribuinte()).orElseThrow(() -> new ResourceNotFoundException("Contribuinte não encontrado"));

        if(declaracaoRepository.existsByContribuinteAndAnoExercicio(contribuinte, dto.anoExercicio())){
            throw new BusinessException(
                    "Ja existe uma declaração para o CPF " + dto.cpfContribuinte() + " no ano " + dto.anoExercicio()
            );
        }

        Declaracao declaracao = new Declaracao();
        declaracao.setContribuinte(contribuinte);
        declaracao.setAnoExercicio(dto.anoExercicio());
        declaracao.setStatus(StatusDeclaracao.ENVIADA);

        dto.rendimentos().forEach(rendimentoDTO -> {
            Rendimento rendimento = new Rendimento();
            rendimento.setTipo(rendimentoDTO.tipo());
            rendimento.setDescricao(rendimentoDTO.descricao());
            rendimento.setValor(rendimentoDTO.valor());
            declaracao.addRendimento(rendimento);
        });

        if (dto.deducoes() != null) {
            dto.deducoes().forEach(deducaoDTO -> {
                Deducao deducao = new Deducao();
                deducao.setTipo(deducaoDTO.tipo());
                deducao.setDescricao(deducaoDTO.descricao());
                deducao.setValor(deducaoDTO.valor());
                declaracao.addDeducao(deducao);
            });
        }

        BigDecimal baseCalculo = calcularBaseCalculo(declaracao);
        declaracao.setBaseCalculo(baseCalculo);

        BigDecimal impostoDevido = calculadoraImpostoService.calcularImposto(
                baseCalculo,
                declaracao.getDeducoes()
        );
        declaracao.setImpostoDevido(impostoDevido);

        Declaracao declaracaoSalva = declaracaoRepository.save(declaracao);

        return DeclaracaoResponseDTO.fromEntity(declaracaoSalva);
    }

    public List<ConsultaDeclaracaoDTO> consultar(
            String cpf,
            Integer ano,
            TipoRendimento tipoRendimento,
            StatusDeclaracao status
    ) {

        if (!ContribuinteValidator.validarCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        if (!contribuinteRepository.existsByCpf(cpf)) {
            throw new ResourceNotFoundException("Contribuinte não encontrado");
        }

        List<Declaracao> declaracoes = declaracaoRepository.buscarComFiltros(
                cpf, ano, status, tipoRendimento
        );

        return declaracoes.stream()
                .map(ConsultaDeclaracaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private BigDecimal calcularBaseCalculo(Declaracao declaracao) {
        BigDecimal totalRendimentos = declaracao.getRendimentos().stream()
                .map(Rendimento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDeducoes = declaracao.getDeducoes().stream()
                .map(Deducao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalRendimentos.subtract(totalDeducoes);
    }

}
