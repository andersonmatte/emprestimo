package br.com.andersonmatte.emprestimo.service;

import br.com.andersonmatte.emprestimo.constant.EmprestimoConstant;
import br.com.andersonmatte.emprestimo.entity.Emprestimo;
import br.com.andersonmatte.emprestimo.entity.Pessoa;
import br.com.andersonmatte.emprestimo.repository.EmprestimoRepository;
import br.com.andersonmatte.emprestimo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class EmprestimoService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    PagamentoService pagamentoService;

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
        efetuarPagamento(emprestimo.getId());
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


    /**
     * Verificar se a pessoa existe no banco de dados
     */
    private Pessoa validaPessoaExistente(Long pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new IllegalArgumentException(EmprestimoConstant.PESSOA_NAO_ENCONTRADA));
        return pessoa;
    }

    /**
     * Verificar se o empréstimo não ultrapassa o limite máximo
     */
    private static void validaValorMaximoEmprestimo(double valorEmprestimo, Pessoa pessoa) {
        if (valorEmprestimo > pessoa.getValorMaxEmprestimo()) {
            throw new IllegalArgumentException(EmprestimoConstant.VALOR_MAXIMO_PERMITIDO);
        }
    }

    /**
     * Verificar se o valor das parcelas não é inferior ao mínimo permitido
     */
    private static void validaValorParcela(double valorEmprestimo, int numeroParcelas, Pessoa pessoa) {
        if (valorEmprestimo / numeroParcelas < pessoa.getValorMinParcelas()) {
            throw new IllegalArgumentException(EmprestimoConstant.VALOR_MINIMO_PERMITIDO_PARCELA);
        }
    }

    /**
     * Verificar se o número de parcelas não excede 24
     */
    private static void validaNumeroParcelas(int numeroParcelas) {
        if (numeroParcelas > 24) {
            throw new IllegalArgumentException(EmprestimoConstant.O_LIMITE_MAXIMO_24);
        }
    }

    /**
     * Método para validar o identificador da pessoa conforme as regras
     */
    private void validarIdentificador(Pessoa pessoa) {
        String identificador = pessoa.getIdentificador();
        switch (pessoa.getTipoIdentificador()) {
            case "PF":
                validarCPF(identificador);
                break;
            case "PJ":
                validarCNPJ(identificador);
                break;
            case "EU":
                validarEU(identificador);
                break;
            case "AP":
                validarAP(identificador);
                break;
            default:
                throw new IllegalArgumentException(EmprestimoConstant.TIPO_DE_IDENTIFICADOR_INVALIDO);
        }
    }

    /**
     * Validações quando CPF
     */
    private void validarCPF(String cpf) {
        if (cpf.length() != 11) {
            throw new IllegalArgumentException(EmprestimoConstant.CPF_INVALIDO);
        }
    }

    /**
     * Validações quando CNPJ
     */
    private void validarCNPJ(String cnpj) {
        // Implemente as validações para CNPJ aqui
        if (cnpj.length() != 14) {
            throw new IllegalArgumentException(EmprestimoConstant.CNPJ_INVALIDO);
        }
    }

    /**
     * Validações quando Estudante Universitário
     */
    private void validarEU(String identificador) {
        if (identificador.length() != 8) {
            throw new IllegalArgumentException(EmprestimoConstant.DEVE_CONTER_8_CARACTERES);
        }

        int primeiroDigito = Character.getNumericValue(identificador.charAt(0));
        int ultimoDigito = Character.getNumericValue(identificador.charAt(identificador.length() - 1));

        if (primeiroDigito + ultimoDigito != 9) {
            throw new IllegalArgumentException(EmprestimoConstant.DEVE_SER_IGUAL_A_9);
        }
    }

    /**
     * Validações quando Aposentado
     */
    private void validarAP(String identificador) {
        if (identificador.length() != 10) {
            throw new IllegalArgumentException(EmprestimoConstant.EXATAMENTE_10_CARACTERES);
        }

        char ultimoDigito = identificador.charAt(identificador.length() - 1);
        String restanteDigitos = identificador.substring(0, identificador.length() - 1);

        if (restanteDigitos.indexOf(ultimoDigito) != -1) {
            throw new IllegalArgumentException(EmprestimoConstant.OUTROS_9_DÍGITOS_DO_IDENTIFICADOR_DE_APOSENTADO);
        }
    }

    /**
     * Realizar o pagamento do empréstimo
     */
    private void efetuarPagamento(Long emprestimoId) {
        // Chamar o serviço de pagamento
        pagamentoService.pagarEmprestimo(emprestimoId);
    }
}
