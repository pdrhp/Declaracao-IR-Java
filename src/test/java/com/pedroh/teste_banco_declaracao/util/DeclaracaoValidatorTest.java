package com.pedroh.teste_banco_declaracao.util;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.pedroh.teste_banco_declaracao.domain.enums.TipoDeducao;
import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;
import com.pedroh.teste_banco_declaracao.dto.request.DeclaracaoDTO;
import com.pedroh.teste_banco_declaracao.dto.request.DeducaoDTO;
import com.pedroh.teste_banco_declaracao.dto.request.RendimentoDTO;

class DeclaracaoValidatorTest {

    @Test
    void shouldValidateRequiredFields() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear(),
            List.of(new RendimentoDTO(TipoRendimento.SALARIO, "Salário Empresa X", BigDecimal.valueOf(50000))),
            List.of(new DeducaoDTO(TipoDeducao.SAUDE, "Plano de Saúde", BigDecimal.valueOf(5000)))
        );

        assertThatCode(() -> DeclaracaoValidator.validar(dto))
            .doesNotThrowAnyException();
    }

    @Test
    void shouldValidateExerciseYear() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear(),
            List.of(new RendimentoDTO(TipoRendimento.SALARIO, "Salário", BigDecimal.valueOf(50000))),
            null
        );

        assertThatCode(() -> DeclaracaoValidator.validar(dto))
            .doesNotThrowAnyException();
    }

    @Test
    void shouldRejectFutureYear() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear() + 1,
            List.of(new RendimentoDTO(TipoRendimento.SALARIO, "Salário", BigDecimal.valueOf(50000))),
            null
        );

        assertThatThrownBy(() -> DeclaracaoValidator.validar(dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Ano de exercicio não pode ser maior que o ano atual");
    }

    @Test
    void shouldRejectOldYear() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear() - 6,
            List.of(new RendimentoDTO(TipoRendimento.SALARIO, "Salário", BigDecimal.valueOf(50000))),
            null
        );

        assertThatThrownBy(() -> DeclaracaoValidator.validar(dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Declaração não pode ser feita para anos anteriores a");
    }

    @Test
    void shouldValidateRendimentos() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear(),
            List.of(
                new RendimentoDTO(TipoRendimento.SALARIO, "Salário", BigDecimal.valueOf(50000)),
                new RendimentoDTO(TipoRendimento.ALUGUEL, "Aluguel", BigDecimal.valueOf(12000))
            ),
            null
        );

        assertThatCode(() -> DeclaracaoValidator.validar(dto))
            .doesNotThrowAnyException();
    }

    @Test
    void shouldValidateDeducoes() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear(),
            List.of(new RendimentoDTO(TipoRendimento.SALARIO, "Salário", BigDecimal.valueOf(50000))),
            List.of(
                new DeducaoDTO(TipoDeducao.SAUDE, "Plano de Saúde", BigDecimal.valueOf(5000)),
                new DeducaoDTO(TipoDeducao.EDUCACAO, "Escola", BigDecimal.valueOf(10000))
            )
        );

        assertThatCode(() -> DeclaracaoValidator.validar(dto))
            .doesNotThrowAnyException();
    }

    @Test
    void shouldValidateDeducoesLimit() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear(),
            List.of(new RendimentoDTO(TipoRendimento.SALARIO, "Salário", BigDecimal.valueOf(50000))),
            List.of(new DeducaoDTO(TipoDeducao.SAUDE, "Plano de Saúde", BigDecimal.valueOf(30000))) // 60% do rendimento
        );

        assertThatThrownBy(() -> DeclaracaoValidator.validar(dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Total de deduções não pode ultrapassar 50% dos rendimentos");
    }

    @Test
    void shouldRejectEmptyRendimentos() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear(),
            List.of(),
            null
        );

        assertThatThrownBy(() -> DeclaracaoValidator.validar(dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Declaração deve conter ao menos um rendimento");
    }

    @Test
    void shouldRejectNegativeRendimento() {
        DeclaracaoDTO dto = new DeclaracaoDTO(
            "529.982.247-25",
            LocalDateTime.now().getYear(),
            List.of(new RendimentoDTO(TipoRendimento.SALARIO, "Salário", BigDecimal.valueOf(-1000))),
            null
        );

        assertThatThrownBy(() -> DeclaracaoValidator.validar(dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Rendimento deve ter valor positivo");
    }
}