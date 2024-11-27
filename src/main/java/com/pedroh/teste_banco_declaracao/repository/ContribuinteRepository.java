package com.pedroh.teste_banco_declaracao.repository;

import com.pedroh.teste_banco_declaracao.domain.model.Contribuinte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContribuinteRepository extends JpaRepository<Contribuinte, Long> {
    Optional<Contribuinte> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
