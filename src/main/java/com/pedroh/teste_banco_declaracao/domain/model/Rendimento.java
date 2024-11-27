package com.pedroh.teste_banco_declaracao.domain.model;

import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TipoRendimento tipo;
    private String descricao;
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "declaracao_id")
    private Declaracao declaracao;
}
