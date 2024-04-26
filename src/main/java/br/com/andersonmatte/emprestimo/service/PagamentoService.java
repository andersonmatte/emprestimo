package br.com.andersonmatte.emprestimo.service;

import br.com.andersonmatte.emprestimo.entity.Emprestimo;
import br.com.andersonmatte.emprestimo.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    public static final String NAO_ENCONTRADO = "Empréstimo não encontrado";
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    /**
     * Realizar o pagamento do Empréstimo com base no id
     */
    public void pagarEmprestimo(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException(NAO_ENCONTRADO));

        // Alterar o status do pagamento para "pago"
        emprestimo.setStatusPagamento(true);
        emprestimoRepository.save(emprestimo);
    }
}

