package br.com.sga.persistencia;

import java.io.EOFException;
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

public class GerenciadorHistoricoCaixa extends Gerenciador {

     private Map<DateHelper, Double> historicoCaixa;

     public GerenciadorHistoricoCaixa(String caminhoBanco) throws IOException {
          super(caminhoBanco);
          this.historicoCaixa = new TreeMap<>();

          try (
                    FileInputStream arquivoHistoricoCaixa = new FileInputStream(caminhoBanco);
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

     public Map<DateHelper, Double> getHistoricoCaixa() {
          return this.historicoCaixa;
     }

     @Override
     public void salvarDados() {
          try (
                    FileOutputStream arquivoHistoricoCaixa = new FileOutputStream(this.arquivoBanco);
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

}
