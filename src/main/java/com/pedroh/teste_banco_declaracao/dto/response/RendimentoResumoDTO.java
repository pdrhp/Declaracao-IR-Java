package com.pedroh.teste_banco_declaracao.dto.response;

import java.math.BigDecimal;

import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;
import com.pedroh.teste_banco_declaracao.domain.model.Rendimento;

public record RendimentoResumoDTO(
    TipoRendimento tipo,
    BigDecimal valor
) {
    public static RendimentoResumoDTO fromEntity(Rendimento rendimento) {
        return new RendimentoResumoDTO(
            rendimento.getTipo(),
            rendimento.getValor()
        );
    }
}