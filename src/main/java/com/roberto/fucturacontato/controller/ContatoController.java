package com.roberto.fucturacontato.controller;

import com.roberto.fucturacontato.dto.ContatoDto;
import com.roberto.fucturacontato.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @GetMapping
    public ResponseEntity<List<ContatoDto>> getAllContatos() {
        return ResponseEntity.ok(contatoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoDto> getContatoById(@PathVariable Long id) {
        return ResponseEntity.ok(contatoService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ContatoDto>> getContatosByNome(@RequestParam String nome) {
        List<ContatoDto> contatos = contatoService.findByNome(nome);
        if (contatos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contatos);
    }

    @PostMapping
    public ResponseEntity<ContatoDto> createContato(@Valid @RequestBody ContatoDto contatoDto) {
        return ResponseEntity.ok(contatoService.save(contatoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDto> updateContato(@PathVariable Long id, @Valid @RequestBody ContatoDto contatoDetails) {
        ContatoDto contatoDto = contatoService.findById(id);
        contatoDto.setNome(contatoDetails.getNome());
        contatoDto.setEmail(contatoDetails.getEmail());
        contatoDto.setTelefone(contatoDetails.getTelefone());
        return ResponseEntity.ok(contatoService.save(contatoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        contatoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}