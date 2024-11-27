package com.pedroh.teste_banco_declaracao.exception.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        String mensagem,
        int status,
        String erro,
        LocalDateTime timestamp
) {
    public static ErrorResponse of(String mensagem, HttpStatus status){
        return new ErrorResponse(
                mensagem,
                status.value(),
                status.getReasonPhrase(),
                LocalDateTime.now()
        );
    }
}
