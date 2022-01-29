package br.com.sga.identidade;

public class Endereco {
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
     
     @Override
     public String toString() {
          return "Endereço: " + this.logradouro + ", N°" + this.numero + ", " + this.bairro + ", " + this.cidade + "-" + this.estado + "\nCEP: " + this.cep;
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

     private void setLogradouro(String logradouro) {
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

     private void setBairro(String bairro) {
          if(bairro == null || bairro.isEmpty()) {
               throw new IllegalArgumentException("Nome do bairro inválido!");
          }
          this.bairro = bairro;
     }

     private void setCidade(String cidade) {
          if(cidade == null || cidade.isEmpty()) {
               throw new IllegalArgumentException("Nome da cidade inválido!");
          }
          this.cidade = cidade;
     }

     private void setEstado(Estado estado) {
          if(estado == null) {
               throw new IllegalArgumentException("Informe um estado!");
          }
          this.estado = estado;
     }

     private void setCep(String cep) throws IllegalArgumentException {
          if(!ValidacaoDadosPessoais.validarCep(cep)) {
               throw new IllegalArgumentException("O número de CEP informado é inválido!");
          }
          this.cep = cep;
     }
}