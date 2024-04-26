package br.com.andersonmatte.emprestimo.service;

import br.com.andersonmatte.emprestimo.dto.PessoaDTO;
import br.com.andersonmatte.emprestimo.entity.Pessoa;
import br.com.andersonmatte.emprestimo.exception.ResourceNotFoundException;
import br.com.andersonmatte.emprestimo.mapper.PessoaMapper;
import br.com.andersonmatte.emprestimo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        // Converter DTO para entidade Pessoa
        Pessoa pessoa = PessoaMapper.converterDTOParaEntity(pessoaDTO);
        // Seta os valores padrão conforme o identificador
        switch (pessoa.getIdentificador().length()) {
            case 11:
                definirLimitesPF(pessoa);
                break;
            case 14:
                definirLimitesPJ(pessoa);
                break;
            case 8:
                definirLimitesEstudante(pessoa);
                break;
            case 10:
                definirLimitesAposentado(pessoa);
                break;
            default:
                throw new IllegalArgumentException("Identificador inválido");
        }
        // Salvar pessoa
        pessoa = pessoaRepository.save(pessoa);
        // Devolver DTO
        return PessoaMapper.converterEntityParaDTO(pessoa);
    }

    public Pessoa getPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElse(null);
    }

    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    public void excluirPessoa(Long id) {
        // Verificar se existe no BD
        Pessoa pessoa = verificarExistenciaPessoa(id);
        // Excluir no BD
        pessoaRepository.delete(pessoa);
    }

    public PessoaDTO editarPessoa(Long id, PessoaDTO pessoaDTO) {
        // Verificar se existe no BD
        Pessoa pessoa = verificarExistenciaPessoa(id);
        // Atualizar pessoa com base no DTO
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setIdentificador(pessoaDTO.getIdentificador());
        // Salvar no BD
        pessoa = pessoaRepository.save(pessoa);
        // Devolver DTO
        return PessoaMapper.converterEntityParaDTO(pessoa);
    }

    /**
     * Definir limitadores padrão para Pessoa Física
     */
    protected Pessoa definirLimitesPF(Pessoa pessoa) {
        pessoa.setTipoIdentificador("PF");
        pessoa.setValorMinParcelas(300.00);
        pessoa.setValorMaxEmprestimo(10000.00);
        return pessoa;
    }

    /**
     * Definir limitadores padrão para Pessoa Jurídica
     */
    protected Pessoa definirLimitesPJ(Pessoa pessoa) {
        pessoa.setTipoIdentificador("PJ");
        pessoa.setValorMinParcelas(1000.00);
        pessoa.setValorMaxEmprestimo(100000.00);
        return pessoa;
    }

    /**
     * Definir limitadores padrão para Estudante Universitário
     */
    protected Pessoa definirLimitesEstudante(Pessoa pessoa) {
        pessoa.setTipoIdentificador("EU");
        pessoa.setValorMinParcelas(100.00);
        pessoa.setValorMaxEmprestimo(10000.00);
        return pessoa;
    }

    /**
     * Definir limitadores padrão para Aposentado
     */
    protected Pessoa definirLimitesAposentado(Pessoa pessoa) {
        pessoa.setTipoIdentificador("AP");
        pessoa.setValorMinParcelas(400.00);
        pessoa.setValorMaxEmprestimo(25000.00);
        return pessoa;
    }

    /**
     * Verificar se a pessoa existe no banco de dados
     */
    private Pessoa verificarExistenciaPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
    }

}

