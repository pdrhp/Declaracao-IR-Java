package com.pedroh.teste_banco_declaracao.service;

import com.pedroh.teste_banco_declaracao.domain.model.Contribuinte;
import com.pedroh.teste_banco_declaracao.dto.request.ContribuinteDTO;
import com.pedroh.teste_banco_declaracao.exception.CpfJaCadastradoException;
import com.pedroh.teste_banco_declaracao.repository.ContribuinteRepository;
import com.pedroh.teste_banco_declaracao.util.ContribuinteValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ContribuinteService {

    private final ContribuinteRepository contribuinteRepository;

    public ContribuinteService(ContribuinteRepository contribuinteRepository){
        this.contribuinteRepository = contribuinteRepository;
    }

    @Transactional
    public Contribuinte cadastrarContribuinte(ContribuinteDTO dto){
        ContribuinteValidator.validar(dto);

        if(contribuinteRepository.existsByCpf(dto.cpf())){
            throw new CpfJaCadastradoException(dto.cpf());
        }

        Contribuinte contribuinte = new Contribuinte();
        contribuinte.setNomeCompleto(dto.nomeCompleto());
        contribuinte.setCpf(dto.cpf());
        contribuinte.setEmail(dto.email());
        contribuinte.setDataNascimento(dto.dataNascimento());
        contribuinte.setRendaAnual(dto.rendaAnual());

        return contribuinteRepository.save(contribuinte);
    }
}
