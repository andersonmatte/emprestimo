package br.com.andersonmatte.emprestimo.controller;

import br.com.andersonmatte.emprestimo.dto.PessoaDTO;
import br.com.andersonmatte.emprestimo.entity.Pessoa;
import br.com.andersonmatte.emprestimo.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/criar")
    public ResponseEntity<PessoaDTO> criarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        try {
            PessoaDTO novaPessoaDTO = pessoaService.criarPessoa(pessoaDTO);
            return new ResponseEntity<>(novaPessoaDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.getPessoa(id);
        if (pessoa != null) {
            return new ResponseEntity<>(pessoa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> getAllPessoas() {
        List<Pessoa> pessoas = pessoaService.getAllPessoas();
        if (!pessoas.isEmpty()) {
            return new ResponseEntity<>(pessoas, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return ResponseEntity.ok(("Pessoa com id :" + id + " excluída com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> editarPessoa(@PathVariable Long id, @Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaAtualizadaDTO = pessoaService.editarPessoa(id, pessoaDTO);
        return new ResponseEntity<>(pessoaAtualizadaDTO, HttpStatus.OK);
    }
}
