package com.pedroh.teste_banco_declaracao.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.pedroh.teste_banco_declaracao.dto.request.ContribuinteDTO;

class ContribuinteValidatorTest {

    @Test
    void shouldValidateValidCPF() {
        assertThat(ContribuinteValidator.validarCPF("529.982.247-25")).isTrue();
    }

    @Test
    void shouldRejectInvalidCPF() {
        assertThat(ContribuinteValidator.validarCPF("111.111.111-11")).isFalse();
        assertThat(ContribuinteValidator.validarCPF("123.456.789-00")).isFalse();
        assertThat(ContribuinteValidator.validarCPF(null)).isFalse();
        assertThat(ContribuinteValidator.validarCPF("12345")).isFalse();
    }

    @Test
    void shouldValidateFullName() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João Silva",
                "529.982.247-25",
                LocalDate.of(1990, 1, 1),
                "joao@email.com",
                BigDecimal.valueOf(50000)
        );

        assertThatCode(() -> ContribuinteValidator.validar(dto))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldRejectSingleName() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João",
                "529.982.247-25",
                LocalDate.of(1990, 1, 1),
                "joao@email.com",
                BigDecimal.valueOf(50000)
        );

        assertThatThrownBy(() -> ContribuinteValidator.validar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nome completo deve conter nome e sobrenome");
    }

    @Test
    void shouldValidateAdultAge() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João Silva",
                "529.982.247-25",
                LocalDate.now().minusYears(20),
                "joao@email.com",
                BigDecimal.valueOf(50000)
        );

        assertThatCode(() -> ContribuinteValidator.validar(dto))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldRejectMinorAge() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João Silva",
                "529.982.247-25",
                LocalDate.now().minusYears(17),
                "joao@email.com",
                BigDecimal.valueOf(50000)
        );

        assertThatThrownBy(() -> ContribuinteValidator.validar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Contribuinte menor de 18 anos");
    }

    @Test
    void shouldValidateValidEmail() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João Silva",
                "529.982.247-25",
                LocalDate.of(1990, 1, 1),
                "joao@email.com",
                BigDecimal.valueOf(50000)
        );

        assertThatCode(() -> ContribuinteValidator.validar(dto))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldRejectInvalidEmail() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João Silva",
                "529.982.247-25",
                LocalDate.of(1990, 1, 1),
                "email-invalido",
                BigDecimal.valueOf(50000)
        );

        assertThatThrownBy(() -> ContribuinteValidator.validar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email invalido");
    }

    @Test
    void shouldValidatePositiveIncome() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João Silva",
                "529.982.247-25",
                LocalDate.of(1990, 1, 1),
                "joao@email.com",
                BigDecimal.valueOf(50000)
        );

        assertThatCode(() -> ContribuinteValidator.validar(dto))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldRejectNegativeIncome() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João Silva",
                "529.982.247-25",
                LocalDate.of(1990, 1, 1),
                "joao@email.com",
                BigDecimal.valueOf(-1000)
        );

        assertThatThrownBy(() -> ContribuinteValidator.validar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Renda anual não pode ser negativa");
    }

    @Test
    void shouldCollectMultipleValidationErrors() {
        ContribuinteDTO dto = new ContribuinteDTO(
                "João",
                "111.111.111-11",
                LocalDate.now().minusYears(17),
                "email-invalido",
                BigDecimal.valueOf(-1000)
        );

        assertThatThrownBy(() -> ContribuinteValidator.validar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nome completo deve conter nome e sobrenome")
                .hasMessageContaining("Contribuinte menor de 18 anos")
                .hasMessageContaining("Email invalido")
                .hasMessageContaining("Renda anual não pode ser negativa")
                .hasMessageContaining("Cpf inválido");
    }
}