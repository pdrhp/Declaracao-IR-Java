package com.pedroh.teste_banco_declaracao.dto.request;

import java.util.List;

public record DeclaracaoDTO(
    String cpfContribuinte,
    Integer anoExercicio,
    List<RendimentoDTO> rendimentos,
    List<DeducaoDTO> deducoes
) {}
