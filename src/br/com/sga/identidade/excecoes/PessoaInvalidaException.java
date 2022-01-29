package br.com.sga.identidade.excecoes;

public class PessoaInvalidaException extends RuntimeException {
     public PessoaInvalidaException() {
          super("Pessoa inválida!");
     }

     public PessoaInvalidaException(String mensagem) {
          super(mensagem);
     }
}
