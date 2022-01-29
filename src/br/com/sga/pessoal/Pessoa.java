package br.com.sga.pessoal;

import java.util.Date;

import br.com.sga.identidade.Endereco;
import br.com.sga.identidade.ValidacaoDadosPessoais;

public abstract class Pessoa implements Comparable<Pessoa> {
     private String nome;
     private String telefone;
     private Sexo sexo;
     private String cpf;
     private Date dataNascimento;
     private String email;
     private Endereco endereco;

     public Pessoa(String nome, String telefone, Sexo sexo, String cpf) {
          this.setNome(nome);
          this.setTelefone(telefone);
          this.setSexo(sexo);
          this.setCpf(cpf);
          this.setDataNascimento(dataNascimento);
          this.setEmail(email);
          this.setEndereco(endereco);
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

     public Date getDataNascimento() {
          return this.dataNascimento;
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

     public void setDataNascimento(Date dataNascimento) throws IllegalArgumentException {
          if(dataNascimento == null) {
               throw new IllegalArgumentException("Informe uma data de nascimento!");
          }
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