package br.com.sga.identidade;

public abstract class Pessoas {

     /**
      * Valida um número de registro nacional, tais como RG, CPF e CNPJ.
      *
      * @param registro
      * @return
      */
     public static boolean validarRegistroNacional(String registro) {
          return true;
     }

     /**
      * Valida um endereço de e-mail.
      *
      * @param email
      * @return
      */
     public static boolean validarEmail(String email) {
          return true;
     }
}
