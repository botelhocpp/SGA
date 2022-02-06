# Projeto Sistema de Controle de Academias (SGA) 🏋️💻
## Desenvolvimento de um sistema de controle e gerenciamento para academias, desenvolvido em Java 16, para desktop.

<p align="center">
 <a href="#sobre">Sobre</a> •
 <a href="#descrição">Descrição</a> •
 <a href="#executando">Executando</a> •
 <a href="#pastas">Pastas</a> • 
 <a href="#recursos">Recursos</a> • 
 <a href="#tecnologias">Tecnologias</a> • 
 <a href="#autores">Autores</a>
</p>

#### Mais informações em breve...

### Sobre

O projeto partiu de uma proposta mais generalizada, da disciplina de Programação Orientada a Objetos, e fluiu em uma análise mais específica: construir um sistema simples, porém robusto, que atenda as necessidades de uma pequena academia: manejar seus alunos e seu caixa. 

Ainda, trata-se de um projeto com fins acadêmicos! Não recomendamos usa-lo em um meio profissional, pois mesmo que tenha sido testado e desenvolvido para ser robusto não oferece toda a segurança necessária para um sistema profissional!

### Descrição

O projeto foca em desenvolver um sistema simples e prático para pequenas academias. Terá como foco inicial o controle do cadastro e caixa dos alunos. O sistema ainda se preocupa em ter um banco de dados consistente, interno ao sistema, com mecanismos de backup e formatação dos dados internos para reúso posterior.

### Executando

O projeto é todo encapsulado em um JAR, devendo o cliente apenas portar um único arquivo! O banco de dados é interno ao JAR, e para o usuário executar o programa basta digitar no terminal:

```
java -jar SGA.jar
```

### Pastas

- META-INF: Contém metadados do arquivo JAR. 
- build: Os bytecodes gerados a partir do código fonte.
- database: Os arquivos binários do banco de dados.
- dist: O JAR executável.
- doc: Onde fica a documentação do projeto, bem como o planejamento e relatório.
- src: O código fonte do projeto.

### Recursos

- [X] Configuração Inicial na Primeira Inicialização
- [X] Sistema de Login/Logout.
- [X] Encapsulamento do Sistema e Banco de Dados em um JAR
- [X] Gerenciamento de dados da Empresa.
- [X] Cadastro e Gerenciamento de Usuários.
- [X] Cadastro e Gerenciamento de Alunos.
- [X] Gerenciador de Caixa Eficiente.
- [X] Sistema de Mensalidade sem Folga.
- [X] Gerenciamento de Banco de Dados local.
- [X] Sistema de Backup e Formatação dos Dados.

### Tecnologias

As seguintes ferramentas foram usadas no projeto.

- [Java](https://www.java.com/)
- [PlantUML](https://plantuml.com/)

### Autores

- Pedro M. Botelho - pedrobotelho15@alu.ufc.br
- Daniel V. Pereira - danieldev.ti@gmail.com
