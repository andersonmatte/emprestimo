package br.com.andersonmatte.emprestimo.mapper;

import br.com.andersonmatte.emprestimo.dto.PessoaDTO;
import br.com.andersonmatte.emprestimo.entity.Pessoa;

public class PessoaMapper {

    public static Pessoa converterDTOParaEntity(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setIdentificador(pessoaDTO.getIdentificador());
        return pessoa;
    }

    public static PessoaDTO converterEntityParaDTO(Pessoa pessoa) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setIdentificador(pessoa.getIdentificador());
        return pessoaDTO;
    }

}

