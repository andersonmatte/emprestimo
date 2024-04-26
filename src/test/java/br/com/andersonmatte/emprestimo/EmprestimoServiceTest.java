package br.com.andersonmatte.emprestimo;

import br.com.andersonmatte.emprestimo.entity.Emprestimo;
import br.com.andersonmatte.emprestimo.entity.Pessoa;
import br.com.andersonmatte.emprestimo.repository.EmprestimoRepository;
import br.com.andersonmatte.emprestimo.repository.PessoaRepository;
import br.com.andersonmatte.emprestimo.service.EmprestimoService;
import br.com.andersonmatte.emprestimo.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class EmprestimoServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRealizarEmprestimo() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setIdentificador("12345128");
        pessoa.setValorMaxEmprestimo(1000.00);
        pessoa.setValorMinParcelas(50.00);
        pessoa.setTipoIdentificador("EU");

        // Configurar o comportamento do repositório de pessoa
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        // realizarEmprestimo
        emprestimoService.realizarEmprestimo(1L, 500.00, 10);

        // Verificar dos métodos
        verify(pessoaRepository, times(1)).findById(1L);
        verify(emprestimoRepository, times(1)).save(any(Emprestimo.class));
        verify(pagamentoService, times(1)).pagarEmprestimo(anyLong());
    }

}
