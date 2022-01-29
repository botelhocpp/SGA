package br.com.sga.pessoal;

public enum Sexo {
     MASCULINO("Masculino"),
     FEMININO("Feminino");

     private String identificador;

     private Sexo(String identificador) {
          this.identificador = identificador;
     }

     public String toString() {
          return this.identificador;
     }

}
