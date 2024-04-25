package br.com.andersonmatte.emprestimo.repository;

import br.com.andersonmatte.emprestimo.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}

