package com.pedroh.teste_banco_declaracao.controller;

import com.pedroh.teste_banco_declaracao.domain.enums.StatusDeclaracao;
import com.pedroh.teste_banco_declaracao.domain.enums.TipoRendimento;
import com.pedroh.teste_banco_declaracao.dto.request.DeclaracaoDTO;
import com.pedroh.teste_banco_declaracao.dto.response.ConsultaDeclaracaoDTO;
import com.pedroh.teste_banco_declaracao.dto.response.DeclaracaoResponseDTO;
import com.pedroh.teste_banco_declaracao.service.DeclaracaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/declaracoes")
public class DeclaracaoController {

    private final DeclaracaoService declaracaoService;

    public DeclaracaoController(DeclaracaoService declaracaoService) {
        this.declaracaoService = declaracaoService;
    }

    @PostMapping
    public ResponseEntity<DeclaracaoResponseDTO> submeter(@RequestBody DeclaracaoDTO dto) {
        DeclaracaoResponseDTO declaracao = declaracaoService.submitDeclaracao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(declaracao);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDeclaracaoDTO>> consultar(
            @RequestParam String cpf,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) TipoRendimento tipoRendimento,
            @RequestParam(required = false) StatusDeclaracao status
    ) {

        return ResponseEntity.ok(declaracaoService.consultar(cpf, ano, tipoRendimento, status));
    }

}
