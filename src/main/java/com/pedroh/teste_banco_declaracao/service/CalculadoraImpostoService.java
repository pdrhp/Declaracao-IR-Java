package com.pedroh.teste_banco_declaracao.service;

import com.pedroh.teste_banco_declaracao.domain.model.Deducao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CalculadoraImpostoService {

    public BigDecimal calcularImposto(BigDecimal baseCalculo, List<Deducao> deducoes) {

        if (baseCalculo.compareTo(new BigDecimal("2259.20")) <= 0) {
            return BigDecimal.ZERO;
        }
        else if (baseCalculo.compareTo(new BigDecimal("2826.65")) <= 0) {
            return calcularFaixaComDeducao(baseCalculo,
                    new BigDecimal("0.075"),
                    new BigDecimal("169.44"));
        }
        else if (baseCalculo.compareTo(new BigDecimal("3751.05")) <= 0) {
            return calcularFaixaComDeducao(baseCalculo,
                    new BigDecimal("0.15"),
                    new BigDecimal("381.44"));
        }
        else if (baseCalculo.compareTo(new BigDecimal("4664.68")) <= 0) {
            return calcularFaixaComDeducao(baseCalculo,
                    new BigDecimal("0.225"),
                    new BigDecimal("662.77"));
        }
        else {
            return calcularFaixaComDeducao(baseCalculo,
                    new BigDecimal("0.275"),
                    new BigDecimal("896.00"));
        }
    }

    private BigDecimal calcularFaixaComDeducao(
            BigDecimal baseCalculo,
            BigDecimal aliquota,
            BigDecimal deducao) {

        return baseCalculo.multiply(aliquota)
                .subtract(deducao)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
