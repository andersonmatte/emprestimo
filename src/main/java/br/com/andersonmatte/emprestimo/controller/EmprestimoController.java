package br.com.andersonmatte.emprestimo.controller;

import br.com.andersonmatte.emprestimo.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("/realizar")
    public ResponseEntity<String> realizarEmprestimo(@RequestParam Long pessoaId,
                                                     @RequestParam double valorEmprestimo,
                                                     @RequestParam int numeroParcelas) {
        try {
            emprestimoService.realizarEmprestimo(pessoaId, valorEmprestimo, numeroParcelas);
            return new ResponseEntity<>("Empréstimo realizado com sucesso", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao realizar empréstimo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

