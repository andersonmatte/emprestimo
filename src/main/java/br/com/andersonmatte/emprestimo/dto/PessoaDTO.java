package br.com.andersonmatte.emprestimo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PessoaDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo identificador é obrigatório")
    private String identificador;

}

