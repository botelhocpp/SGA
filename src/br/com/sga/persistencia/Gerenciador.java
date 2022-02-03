package br.com.sga.persistencia;

public abstract class Gerenciador {
     protected String arquivoBanco;

     public Gerenciador(String caminhoBanco) {
          this.setBanco(caminhoBanco);
     }

     private void setBanco(String arquivoBanco) throws IllegalArgumentException {
          if(arquivoBanco == null || arquivoBanco.isEmpty()) {
               throw new IllegalArgumentException("Insira um caminho válido para o arquivo.");
          }
          this.arquivoBanco = arquivoBanco;
     }

     public String getBanco() {
          return this.arquivoBanco;
     }

     public abstract void salvarDados();
}
