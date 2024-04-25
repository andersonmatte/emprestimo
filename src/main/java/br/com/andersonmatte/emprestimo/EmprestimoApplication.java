package br.com.andersonmatte.emprestimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("br.com.andersonmatte.emprestimo.entity")
public class EmprestimoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmprestimoApplication.class, args);
    }

}
