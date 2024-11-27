package com.pedroh.teste_banco_declaracao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pedroh.teste_banco_declaracao.domain.enums.StatusDeclaracao;
import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;
import com.pedroh.teste_banco_declaracao.domain.model.Contribuinte;
import com.pedroh.teste_banco_declaracao.domain.model.Declaracao;

public interface DeclaracaoRepository extends JpaRepository<Declaracao, Long> {
    List<Declaracao> findByContribuinteCpf(String cpf);
    List<Declaracao> findByContribuinteCpfAndAnoExercicio(String cpf, Integer ano);
    List<Declaracao> findByStatus(StatusDeclaracao status);
    boolean existsByContribuinteAndAnoExercicio(Contribuinte contribuinte, Integer anoExercicio);

    @Query("SELECT DISTINCT d FROM Declaracao d " +
           "LEFT JOIN d.rendimentos r " +
           "WHERE d.contribuinte.cpf = :cpf " +
           "AND (:ano IS NULL OR d.anoExercicio = :ano) " +
           "AND (:status IS NULL OR d.status = :status) " +
           "AND (:tipoRendimento IS NULL OR r.tipo = :tipoRendimento)")
    List<Declaracao> buscarComFiltros(
            @Param("cpf") String cpf,
            @Param("ano") Integer ano,
            @Param("status") StatusDeclaracao status,
            @Param("tipoRendimento") TipoRendimento tipoRendimento
    );
}
