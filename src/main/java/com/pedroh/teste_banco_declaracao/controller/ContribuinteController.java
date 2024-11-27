package com.pedroh.teste_banco_declaracao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroh.teste_banco_declaracao.domain.model.Contribuinte;
import com.pedroh.teste_banco_declaracao.dto.request.ContribuinteDTO;
import com.pedroh.teste_banco_declaracao.dto.response.ContribuinteResponseDTO;
import com.pedroh.teste_banco_declaracao.service.ContribuinteService;

@RestController
@RequestMapping("/api/v1/contribuintes")
public class ContribuinteController {
    private final ContribuinteService contribuinteService;


    public ContribuinteController(ContribuinteService contribuinteService){
        this.contribuinteService = contribuinteService;
    }

    @PostMapping
    public ResponseEntity<ContribuinteResponseDTO> cadastrar(@RequestBody ContribuinteDTO dto){
        Contribuinte contribuinte = contribuinteService.cadastrarContribuinte(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ContribuinteResponseDTO.fromEntity(contribuinte));
    }
}
