package br.com.sga.persistencia;

import java.io.File;

/**
 * Modela um gerenciador de banco de
 * dados genérico.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public abstract class Gerenciador {
     /**
      * Guarda o caminho do banco de dados gerenciado.
      */
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

     /**
      * Restaura o backup informado para o banco de dados local.
      */
     public abstract void restaurarBackup(String arquivoBackup) throws IllegalAccessException;

     /**
      * Salva os dados da aplicação no arquivo de banco de dados
      * padrão.
      */
     public abstract void salvarDados();

     /**
      * Salva os dados da aplicação no arquivo de banco de dados
      * informado.
      */
     public abstract void salvarDados(String arquivoBanco);

     /**
      * Apaga o banco de dados padrão.
      */
     public void apagarDados() {
          new File(arquivoBanco).delete();
     }
}
