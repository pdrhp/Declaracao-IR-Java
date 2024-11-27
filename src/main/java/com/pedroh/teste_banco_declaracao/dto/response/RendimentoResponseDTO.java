package com.pedroh.teste_banco_declaracao.dto.response;

import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;
import com.pedroh.teste_banco_declaracao.domain.model.Rendimento;

import java.math.BigDecimal;

public record RendimentoResponseDTO(
        Long id,
        TipoRendimento tipo,
        String descricao,
        BigDecimal valor
) {
    public static RendimentoResponseDTO fromEntity(Rendimento rendimento){
        return new RendimentoResponseDTO(
                rendimento.getId(),
                rendimento.getTipo(),
                rendimento.getDescricao(),
                rendimento.getValor()
        );
    }
}
