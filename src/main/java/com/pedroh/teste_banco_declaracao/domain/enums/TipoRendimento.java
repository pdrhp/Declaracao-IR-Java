package com.pedroh.teste_banco_declaracao.domain.enums;

public enum TipoRendimento {
    SALARIO("Salário"),
    ALUGUEL("Aluguel"),
    INVESTIMENTO("Investimento");

    private String descricao;

    TipoRendimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
