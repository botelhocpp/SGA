package br.com.sga.persistencia;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Map;
import java.util.TreeMap;

import br.com.sga.datehelper.DateHelper;

/**
 * Modela um gerenciador de banco de
 * dados que guardará o histórico do
 * caixa.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class GerenciadorHistoricoCaixa extends Gerenciador {

     private Map<DateHelper, Double> historicoCaixa;

     /**
      * Configura o caminho do banco de dados e restaura
      * os dados para a aplicação, caso tenha algum.
      */
     public GerenciadorHistoricoCaixa(String caminhoBanco) throws IOException {
          super(caminhoBanco);
          this.historicoCaixa = new TreeMap<>();

          this.restaurarDados(caminhoBanco);
     }

     /**
      * Obtém o mapeamento do histórico do caixa.
      */
     public Map<DateHelper, Double> getHistoricoCaixa() {
          return this.historicoCaixa;
     }

     /**
      * Salva os dados no banco padrão.
      */
     @Override
     public void salvarDados() {
          this.salvarDados(this.arquivoBanco);
     }

     /**
      * Salva os dados no banco informado.
      */
     @Override
     public void salvarDados(String arquivoBanco) {
          try( FileOutputStream arquivoHistoricoCaixa = new FileOutputStream(arquivoBanco);
               ObjectOutputStream historicoCaixaStream = new ObjectOutputStream(arquivoHistoricoCaixa);
               ) {
               for(Map.Entry<DateHelper, Double> entrada : this.historicoCaixa.entrySet()) {
                    historicoCaixaStream.writeObject(entrada.getKey());
                    historicoCaixaStream.writeObject(entrada.getValue());
               }
          } catch (IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

     /**
      * Restaura os dados do backup informado para
      * dentro do sistema, os salvando no banco de
      * dados local.
      */
     @Override
     public void restaurarBackup(String arquivoBackup) throws IllegalAccessException {
          if(!new File(arquivoBackup).exists()) {
               throw new IllegalAccessException("Arquivo inexistente!");
          }
          this.restaurarDados(arquivoBackup);
          this.salvarDados();
     }

     /**
      * Restaura os dados do arquivo informado para
      * dentro do sistema.
      */
     private void restaurarDados(String arquivoBackup) {
          try ( FileInputStream arquivoHistoricoCaixa = new FileInputStream(arquivoBackup);
                ObjectInputStream historicoCaixaStream = new ObjectInputStream(arquivoHistoricoCaixa);
          ) {
               
               DateHelper dataTemporaria;
               Double valorTemporario;

               while (true) {
                    try {
                         dataTemporaria = (DateHelper) historicoCaixaStream.readObject();
                         valorTemporario =  (Double) historicoCaixaStream.readObject();
                         this.historicoCaixa.put(dataTemporaria, valorTemporario);
                    } catch (EOFException e) {
                         break;
                    }
               }
          }
          // O arquivo não pôde ser encontrado
          catch (FileNotFoundException e) {
               System.out.println("Arquivo não encontrado.");
          } catch (ClassNotFoundException e) {
               System.out.println("Tentando ler um objeto de uma classe desconhecida.");
          } catch (StreamCorruptedException e) { // thrown by the constructor ObjectInputStream
               System.out.println("Formato do arquivo não é válido.");
          }
          // Arquivo sem cabeçalho/vazio
          catch (IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

}
