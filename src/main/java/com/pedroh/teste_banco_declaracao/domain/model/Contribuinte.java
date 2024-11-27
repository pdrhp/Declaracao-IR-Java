package com.pedroh.teste_banco_declaracao.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contribuinte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;

    private String cpf;

    private LocalDate dataNascimento;

    private String email;

    private BigDecimal rendaAnual;

    @OneToMany(mappedBy = "contribuinte", cascade = CascadeType.ALL)
    private List<Declaracao> declaracoes = new ArrayList<>();

}
