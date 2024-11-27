package com.pedroh.teste_banco_declaracao.dto.response;

import com.pedroh.teste_banco_declaracao.domain.model.Contribuinte;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContribuinteResponseDTO(
        long id,
        String nomeCompleto,
        String cpf,
        LocalDate dataNascimento,
        String email,
        BigDecimal rendaAnual
) {
    public static ContribuinteResponseDTO fromEntity(Contribuinte contribuinte){
        return new ContribuinteResponseDTO(
                contribuinte.getId(),
                contribuinte.getNomeCompleto(),
                contribuinte.getCpf(),
                contribuinte.getDataNascimento(),
                contribuinte.getEmail(),
                contribuinte.getRendaAnual()
        );
    }
}
