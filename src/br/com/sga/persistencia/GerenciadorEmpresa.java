package br.com.sga.persistencia;

import java.io.File;
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

/**
 * Modela um gerenciador de banco de
 * dados dos dados da empresa.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class GerenciadorEmpresa extends Gerenciador {
     private Empresa academia;

     /**
      * Configura o caminho do banco de dados e restaura
      * os dados para a aplicação, caso tenha algum.
      */
     public GerenciadorEmpresa(String caminhoBanco) {
          super(caminhoBanco);

          this.academia = new Empresa();

          this.restaurarDados(caminhoBanco);
     }

     /**
      * Configura os dados da empresa com os dados informados.
      */
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

     /**
      * Verifica se o banco está vazio ou não.
      */
     public boolean bancoVazio() {
          if(this.academia.getNome() == null && this.academia.getCnpj() == null && this.academia.getEmail() == null && this.academia.getEndereco() == null) {
               return true;
          }
          return false;
     }

     public Empresa obterEmpresa() {
          return this.academia;
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
          try( FileOutputStream arquivoEmpresa = new FileOutputStream(arquivoBanco);
               ObjectOutputStream empresaStream = new ObjectOutputStream(arquivoEmpresa);
          ) {
               empresaStream.writeObject(this.academia);
          }
          catch (IOException e) {
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
          try( FileInputStream arquivoEmpresa = new FileInputStream(arquivoBackup);
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
}