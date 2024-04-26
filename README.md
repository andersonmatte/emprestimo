# Sistema de Empréstimos

URL Base:

	http://localhost:8080/

URL H2:

	http://localhost:8080/h2/

Console H2:

![Criar uma pessoa(POST)](src/main/resources/prints/ConsoleH2.png)

Usuário: admin

Senha: admin

## Serviço de cadastro de Pessoas

### Criar uma pessoa(POST)

	Rota: 

		http://localhost:8080/pessoas/criar		
		
	Exemplo	
		
	{
		"nome": "Pedro",
		"identificador": "01477539045"
	}

![Criar uma pessoa(POST)](src/main/resources/prints/CriandoPessoaPostman.png)

Conferindo no Banco:

![Criar uma pessoa(POST)](src/main/resources/prints/ConferindoNoBancoCriacaoPessoa.png)

### Listar todas as Pessoas(GET)

	Rota: 

		http://localhost:8080/pessoas/

![Listar todas as Pessoas(GET)](src/main/resources/prints/BuscaTodos.png)

### Listar uma Pessoa(GET)

	Rota: 

		http://localhost:8080/pessoas/{id}

![Listar uma Pessoa(GET)](src/main/resources/prints/BuscarPorId.png)

### Excluir uma pessoa(DELETE)

	Rota: 

	http://localhost:8080/pessoas/{id}

![Criar uma pessoa(POST)](src/main/resources/prints/DeletandoRegistro.png)

### Atualizar uma pessoa (PUT)

	Rota: 

	http://localhost:8080/pessoas/{id}	
	
	
	Exemplo	
		
	{
		"nome": "Pedro",
		"identificador": "11"
	}	

![Criar uma pessoa(POST)](src/main/resources/prints/UpdatePessoa.png)

## Serviço de Contratação de Empréstimos

### Contratar um Empréstimo (POST)

	Rota: 

	http://localhost:8080/emprestimos/realizar?pessoaId={id}&valorEmprestimo={valorEmprestimo}&numeroParcelas={numeroParcelas}	
	
	
	Exemplo	
		
	http://localhost:8080/emprestimos/realizar?pessoaId=1&valorEmprestimo=5000&numeroParcelas=12

![Criar uma pessoa(POST)](src/main/resources/prints/EmprestimoRealizado.png)

Validando se pagamento ocorreu:

![Criar uma pessoa(POST)](src/main/resources/prints/ValidandoStatusPagamento.png)