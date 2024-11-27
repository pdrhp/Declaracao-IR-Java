package com.pedroh.teste_banco_declaracao.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContribuinteDTO(
        String nomeCompleto,
        String cpf,
        LocalDate dataNascimento,
        String email,
        BigDecimal rendaAnual
) {}
