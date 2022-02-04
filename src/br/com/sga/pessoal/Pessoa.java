package br.com.sga.pessoal;

import br.com.sga.datehelper.DateHelper;
import br.com.sga.identidade.Endereco;
import br.com.sga.identidade.ValidacaoDadosPessoais;

import java.io.Serializable;

public abstract class Pessoa implements Comparable<Pessoa>, Serializable {
     protected String nome;
     protected String telefone;
     protected Sexo sexo;
     protected String cpf;
     protected DateHelper dataNascimento;
     protected String email;
     protected Endereco endereco;

     public Pessoa() {

     }

     @Override
     public int hashCode() {
          return this.nome.hashCode() + this.email.hashCode();
     }

     public Pessoa(String nome, String telefone, Sexo sexo, String cpf, DateHelper dataNascimento, String email, Endereco endereco) {
          this.setNome(nome);
          this.setTelefone(telefone);
          this.setSexo(sexo);
          this.setCpf(cpf);
          this.setDataNascimento(dataNascimento);
          this.setEmail(email);
          this.setEndereco(endereco);
     }

     @Override
     public String toString() {
          return String.format("Nome: %s%n" + 
          "Telefone: %s%n" + 
          "Sexo: %s%n" + 
          "CPF: %s%n" + 
          "Data de Nascimento: %s%n" +
          "Endereço de E-mail: %s%n" +  
          "Endereço: %s%n", this.nome,
          this.telefone, this.sexo, this.cpf,
          this.dataNascimento, this.email, this.endereco);
     }

     // ------------------------------------------------------------------------
     // Getters
     // ------------------------------------------------------------------------

     public String getNome() {
          return this.nome;
     }

     public String getTelefone() {
          return this.telefone;
     }

     public Sexo getSexo() {
          return this.sexo;
     }

     public String getCpf() {
          return this.cpf;
     }

     public String getDataNascimento() {
          return this.dataNascimento.toString();
     }

     public String getEmail() {
          return this.email;
     }

     public Endereco getEndereco() {
          return this.endereco;
     }

     // ------------------------------------------------------------------------
     // Setters
     // ------------------------------------------------------------------------

     public void setNome(String nome) throws IllegalArgumentException {
          if(nome == null || nome.isEmpty()) {
               throw new IllegalArgumentException("Nome informado é inválido!");
          }
          this.nome = nome;
     }

     public void setTelefone(String telefone) throws IllegalArgumentException {
          if(!ValidacaoDadosPessoais.validarTelefone(telefone)) {
               throw new IllegalArgumentException("Número de telefone inválido!");
          }
          this.telefone = telefone;
     }

     public void setSexo(Sexo sexo) throws IllegalArgumentException {
          if(sexo == null) {
               throw new IllegalArgumentException("Informe um sexo!");
          }
          this.sexo = sexo;
     }

     public void setCpf(String cpf) throws IllegalArgumentException {
          if(!ValidacaoDadosPessoais.validarRegistroNacional(cpf)) {
               throw new IllegalArgumentException("Número de CPF inválido");
          }
          this.cpf = cpf;
     }

     public void setDataNascimento(DateHelper dataNascimento) throws IllegalArgumentException {
          this.dataNascimento = dataNascimento;
     }

     public void setEmail(String email) throws IllegalArgumentException {
          if(!ValidacaoDadosPessoais.validarEmail(email)) {
               throw new IllegalArgumentException("Endereço de e-mail inválido");
          }
          this.email = email;
     }

     public void setEndereco(Endereco endereco) throws IllegalArgumentException {
          if(endereco == null) {
               throw new IllegalArgumentException("Informe um endereço!");
          }
          this.endereco = endereco;
     }    
}