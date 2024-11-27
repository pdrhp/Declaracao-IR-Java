package com.pedroh.teste_banco_declaracao.domain.enums;

public enum TipoDeducao {
    DEPENDENTE("Dependente"),
    SAUDE("Gastos com saúde"),
    EDUCACAO("Gastos com Educação"),
    PREVIDENCIA("Previdência Privada");

    private String descricao;

    TipoDeducao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}
