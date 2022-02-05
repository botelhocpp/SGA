package br.com.sga.pessoal;

import java.io.Serializable;

import br.com.sga.datehelper.DateHelper;
import br.com.sga.identidade.Endereco;
import br.com.sga.identidade.ValidacaoDadosPessoais;

import java.lang.Cloneable;

public abstract class Pessoa implements Comparable<Pessoa>, Serializable, Cloneable {
     protected String nome;
     protected String telefone;
     protected Sexo sexo;
     protected String cpf;
     protected DateHelper dataNascimento;
     protected String email;
     protected Endereco endereco;

     @Override
     public Pessoa clone() throws CloneNotSupportedException {
         return (Pessoa) super.clone();
     }

     public Pessoa() {}

     @Override
     public int hashCode() {
          final int prime = 31;
          int result = 1;
          result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
          result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
          result = prime * result + ((email == null) ? 0 : email.hashCode());
          result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
          result = prime * result + ((nome == null) ? 0 : nome.hashCode());
          result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
          result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
          return result;
     }

     @Override
     public boolean equals(Object obj) {
          if (this == obj)
               return true;
          if (obj == null)
               return false;
          if (getClass() != obj.getClass())
               return false;
          Pessoa other = (Pessoa) obj;
          if (cpf == null) {
               if (other.cpf != null)
                    return false;
          } else if (!cpf.equals(other.cpf))
               return false;
          if (dataNascimento == null) {
               if (other.dataNascimento != null)
                    return false;
          } else if (!dataNascimento.equals(other.dataNascimento))
               return false;
          if (email == null) {
               if (other.email != null)
                    return false;
          } else if (!email.equals(other.email))
               return false;
          if (endereco == null) {
               if (other.endereco != null)
                    return false;
          } else if (!endereco.equals(other.endereco))
               return false;
          if (nome == null) {
               if (other.nome != null)
                    return false;
          } else if (!nome.equals(other.nome))
               return false;
          if (sexo != other.sexo)
               return false;
          if (telefone == null) {
               if (other.telefone != null)
                    return false;
          } else if (!telefone.equals(other.telefone))
               return false;
          return true;
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