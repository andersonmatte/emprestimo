package br.com.andersonmatte.emprestimo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String identificador;
    private String tipoIdentificador;
    private double valorMinParcelas;
    private double valorMaxEmprestimo;

    // Construtor padrão sem argumentos
    public Pessoa() {
    }

    // Construtor com argumentos id e nome, utilizado somente na classe de testes
    public Pessoa(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
