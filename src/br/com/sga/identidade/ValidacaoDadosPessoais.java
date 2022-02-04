package br.com.sga.identidade;

public abstract class ValidacaoDadosPessoais {
     // RFC 5322
     private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
     private static final String REGEX_TELEFONE = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})";
     private static final String REGEX_CEP = "\\d{5}\\-\\d{3}";

     public static void main(String args[]) {
          System.out.println(validarEmail("d"));
     }

     /**
      * Valida um número de registro nacional, tais como CPF e CNPJ
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

               valor = (valor % 11);
               if (valor == 1)
                    valor = 0;

               valor = 11 - valor;

               boolean check1 = (valor == Integer.parseInt(registro.charAt(9) + ""));

               valor = 0;
               base = 11;
               for (int index = 0; index < 10; index++, base--) {
                    valor += base * Integer.parseInt(registro.charAt(index) + "");
               }

               valor = (valor % 11);
               if (valor == 1)
                    valor = 0;

               valor = 11 - valor;

               boolean check2 = (valor == Integer.parseInt(registro.charAt(10) + ""));

               return (check1 && check2 ? true : false);
          } else if (registro.length() == 14) {
               if (registro.equals("00000000000000") || registro.equals("11111111111111") ||
                         registro.equals("22222222222222") || registro.equals("33333333333333") ||
                         registro.equals("44444444444444") || registro.equals("55555555555555") ||
                         registro.equals("66666666666666") || registro.equals("77777777777777") ||
                         registro.equals("88888888888888") || registro.equals("99999999999999"))
                    return (false);

               int valor = 0, base = 2;
               for (int index = 11; index >= 0; index--) {
                    valor += base * Integer.parseInt(registro.charAt(index) + "");
                    base++;
                    if (base == 10) {
                         base = 2;
                    }
               }

               valor = valor % 11;
               if (valor == 1)
                    valor = 0;

               valor = 11 - valor;

               boolean check1 = (valor == Integer.parseInt(registro.charAt(12) + ""));

               valor = 0;
               base = 2;
               for (int index = 12; index >= 0; index--) {
                    valor += base * Integer.parseInt(registro.charAt(index) + "");
                    base++;
                    if (base == 10) {
                         base = 2;
                    }
               }

               valor = valor % 11;
               if (valor == 1)
                    valor = 0;

               valor = 11 - valor;

               boolean check2 = (valor == Integer.parseInt(registro.charAt(13) + ""));

               return (check1 && check2 ? true : false);
          }
          else {
               throw new IllegalArgumentException("Quantidade inválida de digitos!");
          }
     }

     /**
      * Valida um endereço de e-mail
      *
      * @param email
      * @return
      */
     public static boolean validarEmail(String email) throws IllegalArgumentException {
          if (email == null || email.isEmpty()) {
               throw new IllegalArgumentException("Informe um endereço de e-mail!");
          }
          return email.matches(REGEX_EMAIL);
     }

     /**
      * Valida um número de telefone
      *
      * @param telefone
      * @return
      */
     public static boolean validarTelefone(String telefone) {
          return telefone.matches(REGEX_TELEFONE);
     }

     /**
      * Valida um cep
      *
      * @param cep
      * @return
      */
     public static boolean validarCep(String cep) {
          return cep.matches(REGEX_CEP);
     }

     public void validarIdentificador(String identificador) {
          if (identificador == null || identificador.isEmpty()) {
               throw new IllegalArgumentException("Informe um identificador válido!");
          }
     }
}