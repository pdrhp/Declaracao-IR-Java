package com.pedroh.teste_banco_declaracao.dto.request;

import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;

import java.math.BigDecimal;

public record RendimentoDTO(
   TipoRendimento tipo,
   String descricao,
   BigDecimal valor
) {}
