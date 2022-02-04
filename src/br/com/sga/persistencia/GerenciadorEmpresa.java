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

          this.academia = new Empresa();

          try( FileInputStream arquivoEmpresa = new FileInputStream(caminhoBanco);
               ObjectInputStream empresaStream = new ObjectInputStream(arquivoEmpresa);
          ) {
               this.academia = (Empresa) empresaStream.readObject();
          }
          // O arquivo não pôde ser encontrado
          catch (FileNotFoundException e) {
               System.out.println("Arquivo não encontrado.");
          }
          catch (ClassNotFoundException e) {
               System.out.println("Tentando ler um objeto de uma classe desconhecida.");
          }
          catch (StreamCorruptedException e) { // thrown by the constructor ObjectInputStream
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

     public void configurarNome(String nome) {
          this.academia.setNome(nome);
     }

     public void configurarCnpj(String cnpj) {
          this.academia.setCnpj(cnpj);
     }

     public void configurarEmail(String email) {
          this.academia.setEmail(email);
     }

     public void configurarEndereco(Endereco endereco) {
          this.academia.setEndereco(endereco);
     }

     public void configurarEndereco(String logradouro, int numero, String bairro, String cidade, Estado estado, String cep) {
          this.academia.setEndereco(new Endereco(logradouro, numero, bairro, cidade, estado, cep));
     }

     public boolean bancoVazio() {
          if(this.academia.getNome() == null && this.academia.getCnpj() == null && this.academia.getEmail() == null && this.academia.getEndereco() == null) {
               return true;
          }
          return false;
     }

     public Empresa obterEmpresa() {
          return this.academia;
     }

     public void salvarDados() {
          try( FileOutputStream arquivoEmpresa = new FileOutputStream(this.arquivoBanco);
               ObjectOutputStream empresaStream = new ObjectOutputStream(arquivoEmpresa);
          ) {
               empresaStream.writeObject(this.academia);
          }
          catch (IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }
}
