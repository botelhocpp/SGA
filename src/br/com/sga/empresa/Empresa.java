package br.com.sga.empresa;

import java.io.Serializable;

import br.com.sga.identidade.*;
import br.com.sga.identidade.excecoes.PessoaInvalidaException;

public class Empresa implements Serializable {
     private String nome;
     private String cnpj;
     private String email;
     private Endereco endereco;

     public Empresa() {
          
     }

     public Empresa(String nome, String cnpj, String email, Endereco endereco) {
          this(nome, cnpj, email);
          this.setEndereco(endereco);
     }

     public Empresa(String nome, String cnpj, String email) {
          this.setNome(nome);
          this.setCnpj(cnpj);
          this.setEmail(email);
     }

     @Override
     public String toString() {
          return String.format("Informações da Empresa:%n" + 
          "Nome: %s%n" + 
          "CNPJ: %s%n" + 
          "E-mail: %s%n" + 
          "Endereço: %s%n", 
          this.nome, this.cnpj, this.email,
          this.endereco);
     }

     // ------------------------------------------------------------------------
     // Setters
     // ------------------------------------------------------------------------

     public void setNome(String nome) throws IllegalArgumentException {
          if(nome == null || nome.isEmpty()) {
               throw new IllegalArgumentException("Nome da empresa inválido!");
          }
          this.nome = nome;
     }

     public void setCnpj(String cnpj) throws PessoaInvalidaException {
          if(!ValidacaoDadosPessoais.validarRegistroNacional(cnpj)) {
               throw new PessoaInvalidaException("O número do CNPJ informado é inválido!");
          }
          this.cnpj = cnpj;
     }

     public void setEmail(String email) throws IllegalArgumentException {
          if(email == null || email.isEmpty() || !ValidacaoDadosPessoais.validarEmail(email)) {
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
}