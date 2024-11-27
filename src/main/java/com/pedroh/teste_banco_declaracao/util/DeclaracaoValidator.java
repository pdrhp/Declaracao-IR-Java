package com.pedroh.teste_banco_declaracao.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pedroh.teste_banco_declaracao.dto.request.DeclaracaoDTO;
import com.pedroh.teste_banco_declaracao.dto.request.DeducaoDTO;
import com.pedroh.teste_banco_declaracao.dto.request.RendimentoDTO;

public class DeclaracaoValidator {

    public static void validar(DeclaracaoDTO dto){
        List<String> erros = new ArrayList<>();

        validarCamposObrigatorios(dto, erros);
        validarAnoExercicio(dto, erros);
        validarRendimentos(dto, erros);
        validarDeducoes(dto, erros);

        if(!erros.isEmpty()){
            throw new IllegalArgumentException("Erros na declaração: " + String.join(", ", erros));
        }
    }

    private static void validarCamposObrigatorios(DeclaracaoDTO dto, List<String> erros){
        if(dto == null){
            erros.add("Declaração não pode ser nula");
            return;
        }

        if(dto.cpfContribuinte() == null || dto.cpfContribuinte().isBlank()){
            erros.add("CPF do Contribuinte é obrigatorio");
        }

        if(dto.anoExercicio() == null){
            erros.add("Ano de exercício é obrigatório");
        }
    }

    private static void validarAnoExercicio(DeclaracaoDTO dto, List<String> erros){
        if(dto.anoExercicio() != null){
            int anoAtual = LocalDateTime.now().getYear();
            if(dto.anoExercicio() > anoAtual){
                erros.add("Ano de exercicio não pode ser maior que o ano atual");
            }
            if(dto.anoExercicio() < (anoAtual - 5)){
                erros.add("Declaração não pode ser feita para anos anteriores a " + (anoAtual - 5));
            }
        }
    }

    private static void validarRendimentos(DeclaracaoDTO dto, List<String> erros){
        if(dto.rendimentos() == null || dto.rendimentos().isEmpty() ){
            erros.add("Declaração deve conter ao menos um rendimento");
            return;
        }

        for(RendimentoDTO rendimento : dto.rendimentos()){
            if(rendimento.tipo() == null){
                erros.add("Tipo de rendimento é obrigatorio");
            }

            if(rendimento.descricao() == null || rendimento.descricao().isBlank()){
                erros.add("Descrição do rendimento é obrigatória");
            }

            if(rendimento.valor() == null){
                erros.add("Valor do rendimento é obrigatório");
            } else if (rendimento.valor().compareTo(BigDecimal.ZERO) <= 0) {
                erros.add("Rendimento deve ter valor positivo");
            }
        }
    }

    private static void validarDeducoes(DeclaracaoDTO dto, List<String> erros){
        if (dto.deducoes() != null && !dto.deducoes().isEmpty()) {
            for (DeducaoDTO deducao : dto.deducoes()) {
                if (deducao.tipo() == null) {
                    erros.add("Tipo de dedução é obrigatório");
                }

                if (deducao.descricao() == null || deducao.descricao().isBlank()) {
                    erros.add("Descrição da dedução é obrigatória");
                }

                if (deducao.valor() == null) {
                    erros.add("Valor da dedução é obrigatório");
                } else if (deducao.valor().compareTo(BigDecimal.ZERO) <= 0) {
                    erros.add("Dedução deve ter valor positivo");
                }
            }

            validarLimitesDeducoes(dto, erros);
        }
    }

    private static void validarLimitesDeducoes(DeclaracaoDTO dto, List<String> erros) {
        BigDecimal totalDeducoes = dto.deducoes().stream()
                .map(DeducaoDTO::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRendimentos = dto.rendimentos().stream()
                .map(RendimentoDTO::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalDeducoes.compareTo(totalRendimentos.multiply(new BigDecimal("0.5"))) > 0) {
            erros.add("Total de deduções não pode ultrapassar 50% dos rendimentos");
        }
    }
}
