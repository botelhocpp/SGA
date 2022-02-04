package br.com.sga.identidade;

import java.io.Serializable;

public class Endereco implements Serializable {
     private String logradouro;
     private int numero;
     private String bairro;
     private String cidade;
     private Estado estado;
     private String cep;

     public Endereco(String logradouro, int numero, String bairro, String cidade, Estado estado, String cep) {
          this.setLogradouro(logradouro);
          this.setNumero(numero);
          this.setBairro(bairro);
          this.setCidade(cidade);
          this.setEstado(estado);
          this.setCep(cep);
     }

     public Endereco() {
          
     }
     
     @Override
     public String toString() {
          return this.logradouro + ", N°" + this.numero + ", " + this.bairro + ", " + this.cidade + "-" + this.estado + "\nCEP: " + this.cep;
     }

     // ------------------------------------------------------------------------
     // Getters
     // ------------------------------------------------------------------------

     public String getLogradouro() {
          return this.logradouro;
     }

     public int getNumero() {
          return this.numero;
     }

     public String getBairro() {
          return this.bairro;
     }
     
     public String getCidade() {
          return this.cidade;
     }

     public Estado getEstado() {
          return this.estado;
     }

     public String getCep() {
          return this.cep;
     }

     // ------------------------------------------------------------------------
     // Setters
     // ------------------------------------------------------------------------

     public void setLogradouro(String logradouro) {
          if(logradouro == null || logradouro.isEmpty()) {
               throw new IllegalArgumentException("Nome do logradouro inválido!");
          }
          this.logradouro = logradouro;
     }

     public void setNumero(int numero) {
          if(numero <= 0) {
               throw new IllegalArgumentException("Número inválido!");
          }
          this.numero = numero;
     }

     public void setBairro(String bairro) {
          if(bairro == null || bairro.isEmpty()) {
               throw new IllegalArgumentException("Nome do bairro inválido!");
          }
          this.bairro = bairro;
     }

     public void setCidade(String cidade) {
          if(cidade == null || cidade.isEmpty()) {
               throw new IllegalArgumentException("Nome da cidade inválido!");
          }
          this.cidade = cidade;
     }

     public void setEstado(Estado estado) {
          if(estado == null) {
               throw new IllegalArgumentException("Informe um estado!");
          }
          this.estado = estado;
     }

     public void setCep(String cep) throws IllegalArgumentException {
          if(!ValidacaoDadosPessoais.validarCep(cep)) {
               throw new IllegalArgumentException("O número de CEP informado é inválido!");
          }
          this.cep = cep;
     }
}