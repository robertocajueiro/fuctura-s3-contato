package com.roberto.fucturacontato.service;

import com.roberto.fucturacontato.dto.ContatoDto;
import com.roberto.fucturacontato.exceptions.ResourceNotFoundException;
import com.roberto.fucturacontato.model.Contato;
import com.roberto.fucturacontato.repository.ContatoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ContatoDto> findAll() {
        return contatoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ContatoDto findById(Long id) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado com id: " + id));
        return convertToDto(contato);
    }

    public List<ContatoDto> findByNome(String nome) {
        return contatoRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public ContatoDto save(ContatoDto contatoDto) {
        // Verifique se o email já existe
        Optional<Contato> existingContato = contatoRepository.findByEmail(contatoDto.getEmail());
        if (existingContato.isPresent() && !existingContato.get().getId().equals(contatoDto.getId())) {
            throw new IllegalArgumentException("O email já está em uso.");
        }

        Contato contato = modelMapper.map(contatoDto, Contato.class);
        Contato savedContato = contatoRepository.save(contato);
        return modelMapper.map(savedContato, ContatoDto.class);
    }

    public void deleteById(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contato não encontrado com id: " + id);
        }
        contatoRepository.deleteById(id);
    }

    private ContatoDto convertToDto(Contato contato) {
        return new ContatoDto(contato.getId(), contato.getNome(), contato.getEmail(), contato.getTelefone());
    }

    private Contato convertToEntity(ContatoDto contatoDto) {
        Contato contato = new Contato();
        contato.setId(contatoDto.getId());
        contato.setNome(contatoDto.getNome());
        contato.setEmail(contatoDto.getEmail());
        contato.setTelefone(contatoDto.getTelefone());
        return contato;
    }
}