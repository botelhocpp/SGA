#!/bin/bash
rm -rf build/*
rm -rf dist/*
#javadoc -d doc/ ...
javac -d build/ src/br/com/sga/aplicacao/AppSGA.java src/br/com/sga/aplicacao/MenuConfiguracoes.java src/br/com/sga/aplicacao/MenuAluno.java src/br/com/sga/aplicacao/MenuEmpresa.java src/br/com/sga/aplicacao/MenuPagamento.java src/br/com/sga/aplicacao/MenuUsuario.java src/br/com/sga/datehelper/DateHelper.java src/br/com/sga/empresa/Empresa.java src/br/com/sga/empresa/Usuario.java src/br/com/sga/financeiro/Caixa.java src/br/com/sga/financeiro/Pagamento.java src/br/com/sga/identidade/Endereco.java src/br/com/sga/identidade/Estado.java src/br/com/sga/identidade/ValidacaoDadosPessoais.java src/br/com/sga/identidade/excecoes/PessoaInvalidaException.java src/br/com/sga/pessoal/Aluno.java src/br/com/sga/pessoal/Pessoa.java src/br/com/sga/pessoal/Sexo.java src/br/com/sga/persistencia/Gerenciador.java src/br/com/sga/persistencia/GerenciadorAlunos.java src/br/com/sga/persistencia/GerenciadorEmpresa.java src/br/com/sga/persistencia/GerenciadorHistoricoCaixa.java src/br/com/sga/persistencia/GerenciadorUsuarios.java
jar cfm dist/SGA.jar META-INF/MANIFEST.MF database/ -C build/ .
