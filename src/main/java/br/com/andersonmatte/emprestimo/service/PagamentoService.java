package br.com.andersonmatte.emprestimo.service;

import br.com.andersonmatte.emprestimo.entity.Emprestimo;
import br.com.andersonmatte.emprestimo.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public void pagarEmprestimo(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        // Alterar o status do pagamento para "pago"
        emprestimo.setStatusPagamento(true);
        emprestimoRepository.save(emprestimo);
    }
}

