package br.com.sga.pessoal;

/**
 * Encapsula os sexos de uma pessoa.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public enum Sexo {
     MASCULINO("Masculino"),
     FEMININO("Feminino"),
     OUTRO("Outro");

     private String identificador;

     private Sexo(String identificador) {
          this.identificador = identificador;
     }

     public String toString() {
          return this.identificador;
     }

}
