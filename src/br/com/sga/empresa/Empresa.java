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

     public Empresa(String nome, String cnpj) {
          this.setnome(nome);
          this.setcnpj(cnpj);
     }

     // ------------------------------------------------------------------------
     // Setters
     // ------------------------------------------------------------------------

     private void setnome(String nome) throws IllegalArgumentException {
          if(nome == null || nome.isEmpty()) {
               throw new IllegalArgumentException("Nome da empresa inválido!");
          }
          this.nome = nome;
     }

     private void setcnpj(String cnpj) {
          if(!ValidacaoDadosPessoais.validarRegistroNacional(cnpj)) {
               throw new PessoaInvalidaException("O número do CNPJ informado é inválido!");
          }
          this.cnpj = cnpj;
     }

     // ------------------------------------------------------------------------
     // Getters
     // ------------------------------------------------------------------------

     public String getnome() {
          return nome;
     }

     public String getcnpj() {
          return cnpj;
     }

     public String getemail() {
          return email;
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