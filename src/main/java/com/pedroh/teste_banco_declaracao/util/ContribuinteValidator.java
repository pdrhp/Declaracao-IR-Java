package com.pedroh.teste_banco_declaracao.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.pedroh.teste_banco_declaracao.dto.request.ContribuinteDTO;

public class ContribuinteValidator {
    
    public static void validar(ContribuinteDTO dto){
        List<String> errors = new ArrayList<>();
        
        if(dto.nomeCompleto() == null || dto.nomeCompleto().trim().isEmpty()){
            errors.add("Nome completo é obrigatorio");
        } else if (dto.nomeCompleto().trim().split(" ").length < 2) {
            errors.add("Nome completo deve conter nome e sobrenome");
        }

        if(dto.dataNascimento() == null){
            errors.add("Data de nascimento é obrigatória");
        } else if (dto.dataNascimento().isAfter(LocalDate.now())) {
            errors.add("Data de nascimento não pode ser no futuro");
        } else if (LocalDate.now().getYear() - dto.dataNascimento().getYear() < 18) {
            errors.add("Contribuinte menor de 18 anos");
        }

        if(dto.email() == null || !dto.email().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")){
            errors.add("Email invalido");
        }

        if(dto.rendaAnual() == null || dto.rendaAnual().compareTo(BigDecimal.ZERO) < 0){
            errors.add("Renda anual não pode ser negativa");
        }

        if(dto.cpf() == null || !validarCPF(dto.cpf())){
            errors.add("Cpf inválido");
        }

        if(!errors.isEmpty()){
            throw new IllegalArgumentException("Erros de validação: " + String.join(", ", errors));
        }
    }


    public static boolean validarCPF(String cpf){
        if(cpf == null) return false;

        cpf = cpf.replaceAll("[^0-9]", "");

        if(cpf.length() != 11) return false;

        boolean todosDigitosIguais = true;
        for(int i = 1; i < cpf.length(); i++) {
            if(cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        if(todosDigitosIguais) return false;

        int soma = 0;
        for(int i = 0; i < 9; i++){
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int primeiroDigito = 11 - (soma % 11);

        if (primeiroDigito > 9){
            primeiroDigito = 0;
        }

        if(Character.getNumericValue(cpf.charAt(9)) != primeiroDigito) return false;


        soma = 0;
        for(int i = 0; i < 10; i++){
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int segundoDigito = 11 - (soma % 11);
        if(segundoDigito > 9){
            segundoDigito = 0;
        }

        return Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
    }
}
