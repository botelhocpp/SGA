package br.com.sga.identidade;

public class PessoaInvalidaException extends RuntimeException {
     public PessoaInvalidaException() {
          super("Pessoa inválida!");
     }

     public PessoaInvalidaException(String mensagem) {
          super(mensagem);
     }
}
