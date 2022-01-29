package br.com.sga.identidade;

import java.util.regex.*;

public abstract class ValidacaoDadosPessoais {
     // RFC 5322
     private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
     private static final String REGEX_TELEFONE = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})";
     private static final String REGEX_CEP = "\\d{5}\\-\\d{3}";

     /**
      * Valida um número de registro nacional, tais como CPF e CNPJ.
      * 
      * @param registro
      * @return
      */
     public static boolean validarRegistroNacional(String registro) {
          if (registro.length() == 11) {
               if (registro.equals("00000000000") ||
                    registro.equals("11111111111") ||
                    registro.equals("22222222222") ||
                    registro.equals("33333333333") ||
                    registro.equals("44444444444") ||
                    registro.equals("55555555555") ||
                    registro.equals("66666666666") ||
                    registro.equals("77777777777") ||
                    registro.equals("88888888888") ||
                    registro.equals("99999999999")) {
                         return (false);
                    }

               int valor = 0, base = 10;
               for (int index = 0; index < 9; index++, base--) {
                    valor += base * Integer.parseInt(registro.charAt(index) + "");
               }

               boolean check1 = ((11 - (valor % 11)) == Integer.parseInt(registro.charAt(9) + ""));

               valor = 0;
               base = 11;
               for (int index = 0; index < 10; index++, base--) {
                    valor += base * Integer.parseInt(registro.charAt(index) + "");
               }

               boolean check2 = ((11 - (valor % 11)) == Integer.parseInt(registro.charAt(10) + ""));

               return (check1 && check2 ? true : false);
          }
          else if (registro.length() == 12) {

          }
          else {
               throw new IllegalArgumentException("Quantidade inválida de digitos!");
          }
          return true;
     }

     /**
      * Valida um endereço de e-mail.
      *
      * @param email
      * @return
      */
     public static boolean validarEmail(String email) {
          return email.matches(REGEX_EMAIL);
     }

     /**
      * Valida um número de telefone.
      *
      * @param telefone
      * @return
      */
     public static boolean validarTelefone(String telefone) {
          return telefone.matches(REGEX_TELEFONE);
     }

     // XXXXX-XXX
     public static boolean validarCep(String cep) {
          return cep.matches(REGEX_CEP);
     }
}

/*
 * 
 * (\(?\d{2}\)?\s)?(\d{4,5}\-\d{4})
 * 
 * number.matches("((\\([0-9]{2}\\))?([0-9]\\-)?[0-9]{4}\\-[0-9]{4})|([0-9]{3})"
 * );
 * 
 * XXX
 * XXXX.XXXX
 * X.XXXX.XXXX
 * (XX)XXXX.XXXX
 * (XX)X.XXXX.XXXX
 * 
 */
