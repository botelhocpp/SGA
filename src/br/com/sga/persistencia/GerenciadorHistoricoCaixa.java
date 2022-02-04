package br.com.sga.persistencia;

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
               this.historicoCaixa = (Map<DateHelper, Double>) historicoCaixaStream.readObject();
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

     @Override
     public void salvarDados() {
          try (
                    FileOutputStream arquivoHistoricoCaixa = new FileOutputStream(this.arquivoBanco);
                    ObjectOutputStream historicoCaixaStream = new ObjectOutputStream(arquivoHistoricoCaixa);
               ) {
               historicoCaixaStream.writeObject(this.historicoCaixa);
          } catch (IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

}
