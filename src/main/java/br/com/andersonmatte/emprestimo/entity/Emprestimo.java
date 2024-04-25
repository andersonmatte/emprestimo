package br.com.andersonmatte.emprestimo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    private double valor;
    private int numeroParcelas;
    private boolean statusPagamento;
    private Date dataCriacao;

}
