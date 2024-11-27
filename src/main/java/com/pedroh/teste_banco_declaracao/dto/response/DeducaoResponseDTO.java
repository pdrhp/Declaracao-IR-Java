package com.pedroh.teste_banco_declaracao.dto.response;

import com.pedroh.teste_banco_declaracao.domain.enums.TipoDeducao;
import com.pedroh.teste_banco_declaracao.domain.model.Deducao;

import java.math.BigDecimal;

public record DeducaoResponseDTO(
        long id,
        TipoDeducao tipo,
        String descricao,
        BigDecimal valor
) {
    public static DeducaoResponseDTO fromEntity(Deducao deducao){
        return new DeducaoResponseDTO(
                deducao.getId(),
                deducao.getTipo(),
                deducao.getDescricao(),
                deducao.getValor()
        );
    }
}
