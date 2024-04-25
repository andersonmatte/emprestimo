package br.com.andersonmatte.emprestimo.repository;

import br.com.andersonmatte.emprestimo.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
