package br.com.sga.empresa;

import br.com.sga.identidade.*;

public class Empresa {
     private String nomeEmpresa;
     private String cnpjEmpresa;
     private String emailEmpresa;

     private String nomeDono;
     private String cpfDono;
     
     private String nomeResponsavel;
     private String cpfResponsavel;
     private Endereco enderecoEmpresa;
     private double valorMensalidade;

     public Empresa(String nomeEmpresa, String cnpjEmpresa) {
          this.setNomeEmpresa(nomeEmpresa);
          this.setCNPJEmpresa(cnpjEmpresa);
     }

     

     // ------------------------------------------------------------------------
     // Setters
     // ------------------------------------------------------------------------

     private void setNomeEmpresa(String nomeEmpresa) {
          if(nomeEmpresa == null || nomeEmpresa.isEmpty()) {
               throw new IllegalArgumentException("Nome da empresa inválido!");
          }
          this.nomeEmpresa = nomeEmpresa;
     }

     private void setCNPJEmpresa(String cnpjEmpresa) {
          if(!Pessoas.validarRegistroNacional(cnpjEmpresa)) {
               throw new PessoaInvalidaException("O número do CNPJ informado é inválido!");
          }
          this.cnpjEmpresa = cnpjEmpresa;
     }

     // ------------------------------------------------------------------------
     // Getters
     // ------------------------------------------------------------------------

     public String getNomeEmpresa() {
          return nomeEmpresa;
     }

     public String getCnpjEmpresa() {
          return cnpjEmpresa;
     }

     public String getEmailEmpresa() {
          return emailEmpresa;
     }

     public String getNomeDono() {
          return nomeDono;
     }

     public String getCpfDono() {
          return cpfDono;
     }

     public String getNomeResponsavel() {
          return nomeResponsavel;
     }

     public String getCpfResponsavel() {
          return cpfResponsavel;
     }

     public String getCidade() {
          return cidade;
     }

     public Estado getEstado() {
          return estado;
     }

     public double getValorMensalidade() {
          return valorMensalidade;
     }
}