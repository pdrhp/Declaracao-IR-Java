package com.pedroh.teste_banco_declaracao.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraImpostoServiceTest {

    private CalculadoraImpostoService calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraImpostoService();
    }

    @Test
    void shouldCalculateZeroTaxForExemptionRange() {
        BigDecimal baseCalculo = new BigDecimal("2000.00");

        BigDecimal imposto = calculadora.calcularImposto(baseCalculo, new ArrayList<>());

        assertThat(imposto).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldCalculateTaxForFirstRange() {
        BigDecimal baseCalculo = new BigDecimal("2500.00");

        BigDecimal imposto = calculadora.calcularImposto(baseCalculo, new ArrayList<>());

        assertThat(imposto).isEqualByComparingTo(new BigDecimal("18.06"));
    }

    @Test
    void shouldCalculateTaxForSecondRange() {
        BigDecimal baseCalculo = new BigDecimal("3500.00");

        BigDecimal imposto = calculadora.calcularImposto(baseCalculo, new ArrayList<>());

        assertThat(imposto).isEqualByComparingTo(new BigDecimal("143.56"));
    }

    @Test
    void shouldCalculateTaxForThirdRange() {
        BigDecimal baseCalculo = new BigDecimal("4200.00");

        BigDecimal imposto = calculadora.calcularImposto(baseCalculo, new ArrayList<>());

        assertThat(imposto).isEqualByComparingTo(new BigDecimal("282.23"));
    }

    @Test
    void shouldCalculateTaxForHighestRange() {
        BigDecimal baseCalculo = new BigDecimal("5000.00");

        BigDecimal imposto = calculadora.calcularImposto(baseCalculo, new ArrayList<>());

        assertThat(imposto).isEqualByComparingTo(new BigDecimal("479.00"));
    }

    @Test
    void shouldRoundTaxCorrectly() {
        BigDecimal baseCalculo = new BigDecimal("3333.33");

        BigDecimal imposto = calculadora.calcularImposto(baseCalculo, new ArrayList<>());

        assertThat(imposto).isEqualByComparingTo(new BigDecimal("118.56"));
        assertThat(imposto.scale()).isEqualTo(2);
    }
}