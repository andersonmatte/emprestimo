package br.com.andersonmatte.emprestimo.service;

import br.com.andersonmatte.emprestimo.entity.Emprestimo;
import br.com.andersonmatte.emprestimo.entity.Pessoa;
import br.com.andersonmatte.emprestimo.repository.EmprestimoRepository;
import br.com.andersonmatte.emprestimo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmprestimoService {

    public static final String PESSOA_NAO_ENCONTRADA = "Pessoa não encontrada";
    public static final String VALOR_MAXIMO_PERMITIDO = "Valor do empréstimo ultrapassa o limite máximo permitido";
    public static final String VALOR_MINIMO_PERMITIDO_PARCELA = "Valor das parcelas é inferior ao mínimo permitido";
    public static final String O_LIMITE_MAXIMO_24 = "Número de parcelas excede o limite máximo (24)";
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public void realizarEmprestimo(Long pessoaId, double valorEmprestimo, int numeroParcelas) {
        // Buscar pessoa
        Pessoa pessoa = validaPessoaExistente(pessoaId);
        // Validar identificador da pessoa
        validarIdentificador(pessoa);
        // Checar se o valor do emprestimo está de acordo com a pessoa
        validaValorMaximoEmprestimo(valorEmprestimo, pessoa);
        // Checar se o valor da parcela está dentro do mínimo permitido
        validaValorParcela(valorEmprestimo, numeroParcelas, pessoa);
        // Validar se o numero de parcelas está dentro do permitido(24 no máximo)
        validaNumeroParcelas(numeroParcelas);
        // Liberado para criar emprestimo
        Emprestimo emprestimo = criarEmprestimo(valorEmprestimo, numeroParcelas, pessoa);
        // Chamar outra API para executar o pagamento
        chamarServicoPagamento(emprestimo.getId());
    }

    /**
     * Após passar por todas as validações o empréstimo deve ser criado
     */
    private Emprestimo criarEmprestimo(double valorEmprestimo, int numeroParcelas, Pessoa pessoa) {
        // Criar o empréstimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setPessoa(pessoa);
        emprestimo.setValor(valorEmprestimo);
        emprestimo.setNumeroParcelas(numeroParcelas);
        emprestimo.setStatusPagamento(false);
        emprestimo.setDataCriacao(new Date());
        // Salvar emprestimo
        emprestimoRepository.save(emprestimo);
        return emprestimo;
    }

    private Pessoa validaPessoaExistente(Long pessoaId) {
        // Verificar se a pessoa existe no banco de dados
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new IllegalArgumentException(PESSOA_NAO_ENCONTRADA));
        return pessoa;
    }

    /**
     * Verificar se o empréstimo não ultrapassa o limite máximo
     */
    private static void validaValorMaximoEmprestimo(double valorEmprestimo, Pessoa pessoa) {
        if (valorEmprestimo > pessoa.getValorMaxEmprestimo()) {
            throw new IllegalArgumentException(VALOR_MAXIMO_PERMITIDO);
        }
    }

    /**
     * Verificar se o valor das parcelas não é inferior ao mínimo permitido
     */
    private static void validaValorParcela(double valorEmprestimo, int numeroParcelas, Pessoa pessoa) {
        if (valorEmprestimo / numeroParcelas < pessoa.getValorMinParcelas()) {
            throw new IllegalArgumentException(VALOR_MINIMO_PERMITIDO_PARCELA);
        }
    }

    /**
     * Verificar se o número de parcelas não excede 24
     */
    private static void validaNumeroParcelas(int numeroParcelas) {
        if (numeroParcelas > 24) {
            throw new IllegalArgumentException(O_LIMITE_MAXIMO_24);
        }
    }

    // Método para validar o identificador da pessoa conforme as regras
    // Realizar validações do identificador da pessoa
    private void validarIdentificador(Pessoa pessoa) {
        // Implementar validações de identificador conforme especificado
    }

    // Método para chamar o serviço de pagamento
    private void chamarServicoPagamento(Long emprestimoId) {
        // Implementar a chamada ao serviço de pagamento
    }
}
