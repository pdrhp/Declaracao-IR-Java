package com.pedroh.teste_banco_declaracao.domain.model;

import com.pedroh.teste_banco_declaracao.domain.enums.StatusDeclaracao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Declaracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contribuinte_id", nullable = false)
    private Contribuinte contribuinte;

    private Integer anoExercicio;

    private BigDecimal baseCalculo;

    private BigDecimal impostoDevido;

    private StatusDeclaracao status;

    private LocalDateTime dataSubmissao;

    @OneToMany(mappedBy = "declaracao", cascade = CascadeType.ALL)
    private List<Rendimento> rendimentos = new ArrayList<>();

    @OneToMany(mappedBy = "declaracao", cascade = CascadeType.ALL)
    private List<Deducao> deducoes = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        dataSubmissao = LocalDateTime.now();
    }

    public void addRendimento(Rendimento rendimento) {
        rendimentos.add(rendimento);
        rendimento.setDeclaracao(this);
    }

    public void addDeducao(Deducao deducao) {
        deducoes.add(deducao);
        deducao.setDeclaracao(this);
    }
}