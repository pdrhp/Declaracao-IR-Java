package com.pedroh.teste_banco_declaracao.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.pedroh.teste_banco_declaracao.domain.enums.StatusDeclaracao;
import com.pedroh.teste_banco_declaracao.domain.model.Declaracao;
import com.pedroh.teste_banco_declaracao.domain.model.Rendimento;

public record ConsultaDeclaracaoDTO(
    Long id,
    Integer anoExercicio,
    StatusDeclaracao status,
    BigDecimal rendaTotal,
    BigDecimal impostoDevido,
    List<RendimentoResumoDTO> rendimentos
) {
    public static ConsultaDeclaracaoDTO fromEntity(Declaracao declaracao) {
        return new ConsultaDeclaracaoDTO(
            declaracao.getId(),
            declaracao.getAnoExercicio(),
            declaracao.getStatus(),
            calcularRendaTotal(declaracao.getRendimentos()),
            declaracao.getImpostoDevido(),
            declaracao.getRendimentos().stream()
                .map(RendimentoResumoDTO::fromEntity)
                .collect(Collectors.toList())
        );
    }

    private static BigDecimal calcularRendaTotal(List<Rendimento> rendimentos) {
        return rendimentos.stream()
            .map(Rendimento::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 