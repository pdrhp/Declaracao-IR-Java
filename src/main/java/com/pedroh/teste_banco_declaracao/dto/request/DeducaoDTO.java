package com.pedroh.teste_banco_declaracao.dto.request;

import com.pedroh.teste_banco_declaracao.domain.enums.TipoDeducao;

import java.math.BigDecimal;

public record DeducaoDTO(
        TipoDeducao tipo,
        String descricao,
        BigDecimal valor
) {}
