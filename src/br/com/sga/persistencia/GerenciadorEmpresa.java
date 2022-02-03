package br.com.sga.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import br.com.sga.empresa.Empresa;
import br.com.sga.identidade.Endereco;
import br.com.sga.identidade.Estado;

public class GerenciadorEmpresa extends Gerenciador {
     private Empresa academia;

     public GerenciadorEmpresa(String caminhoBanco) {
          super(caminhoBanco);

          try (FileInputStream arquivoEmpresa = new FileInputStream(caminhoBanco);
                    ObjectInputStream empresaStream = new ObjectInputStream(arquivoEmpresa);) {
               this.academia = (Empresa) empresaStream.readObject();
          }
          // O arquivo não pôde ser encontrado
          catch (FileNotFoundException e) {

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

     public void configurarEmpresa(String nome, String cnpj, String email) {
          this.academia = new Empresa(nome, cnpj, email);
     }

     public void configurarEndereco(String logradouro, int numero, String bairro, String cidade, Estado estado,
               String cep) {
          this.academia.setEndereco(new Endereco(logradouro, numero, bairro, cidade, estado, cep));
     }

     public Empresa obterEmpresa() {
          return this.academia;
     }

     public void salvarDados() {
          try (
                    FileOutputStream arquivoEmpresa = new FileOutputStream(this.arquivoBanco);
                    ObjectOutputStream empresaStream = new ObjectOutputStream(arquivoEmpresa);) {
               empresaStream.writeObject(this.academia);
          } catch (IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }
}
