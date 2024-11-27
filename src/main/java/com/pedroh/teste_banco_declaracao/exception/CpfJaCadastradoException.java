package com.pedroh.teste_banco_declaracao.exception;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String cpf) {
        super("CPF " + cpf + " já cadastrado no sistema");
    }
}
