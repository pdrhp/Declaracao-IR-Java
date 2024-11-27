package com.pedroh.teste_banco_declaracao.dto.response;

import com.pedroh.teste_banco_declaracao.domain.enums.StatusDeclaracao;
import com.pedroh.teste_banco_declaracao.domain.model.Declaracao;
import com.pedroh.teste_banco_declaracao.domain.model.Deducao;
import com.pedroh.teste_banco_declaracao.domain.model.Rendimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DeclaracaoResponseDTO(
        Long id,
        String cpfContribuinte,
        String nomeContribuinte,
        Integer anoExercicio,
        BigDecimal rendaTotal,
        BigDecimal totalDeducoes,
        BigDecimal baseCalculo,
        BigDecimal impostoDevido,
        StatusDeclaracao status,
        LocalDateTime dataSubmissao,
        List<RendimentoResponseDTO> rendimentos,
        List<DeducaoResponseDTO> deducoes
) {
    public static DeclaracaoResponseDTO fromEntity(Declaracao declaracao){
        return new DeclaracaoResponseDTO(
                declaracao.getId(),
                declaracao.getContribuinte().getCpf(),
                declaracao.getContribuinte().getNomeCompleto(),
                declaracao.getAnoExercicio(),
                calcularRendaTotal(declaracao.getRendimentos()),
                calcularTotalDeducoes(declaracao.getDeducoes()),
                declaracao.getBaseCalculo(),
                declaracao.getImpostoDevido(),
                declaracao.getStatus(),
                declaracao.getDataSubmissao(),
                declaracao.getRendimentos().stream()
                        .map(RendimentoResponseDTO::fromEntity)
                        .collect(Collectors.toList()),
                declaracao.getDeducoes().stream()
                        .map(DeducaoResponseDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }


    private static BigDecimal calcularRendaTotal(List<Rendimento> rendimentos){
        return rendimentos.stream()
                .map(Rendimento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal calcularTotalDeducoes(List<Deducao> deducoes){
        return deducoes.stream()
                .map(Deducao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
