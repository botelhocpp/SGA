package br.com.sga.empresa;

import br.com.sga.pessoal.*;
import br.com.sga.identidade.*;
import br.com.sga.identidade.excecoes.PessoaInvalidaException;

public class Empresa {
     private String nome;
     private String cnpj;
     private String email;
     private Endereco endereco;
     private Funcionario gerente;

     public Empresa(String nome, String cnpj, String email, Endereco endereco) {
          this.setNome(nome);
          this.setCnpj(cnpj);
          this.setEmail(email);
          this.setEndereco(endereco);
     }

     @Override
     public String toString() {
          return String.format("Informações da Empresa:%n" + 
          "Nome: %s%n" + 
          "CNPJ: %s%n" + 
          "E-mail: %s%n" + 
          "Endereço: %s%n" + 
          "Gerente: %s%n", 
          this.nome, this.cnpj, this.email,
          this.endereco, this.gerente);
     }

     // ------------------------------------------------------------------------
     // Setters
     // ------------------------------------------------------------------------

     private void setNome(String nome) throws IllegalArgumentException {
          if(nome == null || nome.isEmpty()) {
               throw new IllegalArgumentException("Nome da empresa inválido!");
          }
          this.nome = nome;
     }

     private void setCnpj(String cnpj) throws PessoaInvalidaException {
          if(!ValidacaoDadosPessoais.validarRegistroNacional(cnpj)) {
               throw new PessoaInvalidaException("O número do CNPJ informado é inválido!");
          }
          this.cnpj = cnpj;
     }

     public void setEmail(String email) throws IllegalArgumentException {
          if(email == null || email.isEmpty()) {
               throw new IllegalArgumentException("E-mail da empresa inválido!");
          }
          this.email = email;
     }

     public void setEndereco(Endereco endereco) throws IllegalArgumentException {
          if(endereco == null) {
               throw new IllegalArgumentException("Informe um endereço!");
          }
          this.endereco = endereco;
     }  

     public void setGerente(Funcionario gerente) throws IllegalArgumentException {
          if(gerente == null) {
               throw new IllegalArgumentException("Informe um gerente!");
          }
          this.gerente = gerente;
     }  

     // ------------------------------------------------------------------------
     // Getters
     // ------------------------------------------------------------------------

     public String getNome() {
          return this.nome;
     }

     public String getCnpj() {
          return this.cnpj;
     }

     public String getEmail() {
          return this.email;
     }

     public Endereco getEndereco() {
          return this.endereco;
     }

     public Funcionario getGerente() {
          return this.gerente;
     }
}