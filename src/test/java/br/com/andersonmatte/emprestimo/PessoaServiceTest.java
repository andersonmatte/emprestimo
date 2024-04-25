package br.com.andersonmatte.emprestimo;

import br.com.andersonmatte.emprestimo.dto.PessoaDTO;
import br.com.andersonmatte.emprestimo.entity.Pessoa;
import br.com.andersonmatte.emprestimo.exception.ResourceNotFoundException;
import br.com.andersonmatte.emprestimo.repository.PessoaRepository;
import br.com.andersonmatte.emprestimo.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPessoasQuandoListaVazia() {
        List<Pessoa> pessoas = new ArrayList<>();
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> result = pessoaService.getAllPessoas();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllPessoasQuandoListaNaoVazia() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa(1L, "João"));
        pessoas.add(new Pessoa(2L, "Maria"));
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> result = pessoaService.getAllPessoas();

        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getNome());
        assertEquals("Maria", result.get(1).getNome());
    }

    @Test
    public void testGetPessoaQuandoExiste() {
        Pessoa pessoa = new Pessoa(1L, "João");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.getPessoa(1L);

        assertNotNull(result);
        assertEquals(1L, Optional.ofNullable(result.getId()));
        assertEquals("João", result.getNome());
    }


    @Test
    public void testGetPessoaQuandoNaoExiste() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        Pessoa result = pessoaService.getPessoa(1L);

        assertNull(result);
    }

    @Test
    public void testExcluirPessoaQuandoExiste() {
        Pessoa pessoa = new Pessoa(1L, "João");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        pessoaService.excluirPessoa(1L);

        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    @Test
    public void testExcluirPessoa_QuandoNaoExiste() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.excluirPessoa(1L));
    }

    @Test
    public void testCriarPessoa() {
        Pessoa pessoa = new Pessoa(null, "João");
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("João");
        pessoaDTO.setIdentificador("11");
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaDTO result = pessoaService.criarPessoa(pessoaDTO);

        assertNotNull(result);
        Assertions.assertEquals("João", result.getNome());
    }

}

