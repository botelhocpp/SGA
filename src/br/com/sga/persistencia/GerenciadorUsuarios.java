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


import br.com.sga.empresa.Usuario;
/**
 * Modela um gerenciador de banco de
 * dados dos usuários.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class GerenciadorUsuarios extends Gerenciador {

     /**
      * Guarda o próximo ID elencado a um
      * usuário na criação.
      */
     private static int idIncremento = 1;

     /**
      * Guarda uma lista de usuários mapeada a seu
      * ID.
      */
     private Map<Integer, Usuario> usuarios;

     /**
      * Configura o caminho do banco de dados e restaura
      * os dados para a aplicação, caso tenha algum.
      */
     public GerenciadorUsuarios(String caminhoBanco) throws IOException {
          super(caminhoBanco);
          this.usuarios = new TreeMap<>();
          this.restaurarDados(caminhoBanco);
     }

     public void criarUsuario(String login, String senha, boolean administrador) {
          Usuario novoUsuario = new Usuario(idIncremento++, login, senha, administrador);
          this.usuarios.put(novoUsuario.getId(), novoUsuario);
     }

     public Usuario obterUsuario(String login, String senha) {
          for(Usuario usuario : this.usuarios.values()) {
               if(usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                    return usuario;
               }
          }
          return null;
     }

     public boolean removerUsuario(int id) {
          return (this.usuarios.remove(id) != null) ? true : false;
     }
     
     public void listarUsuario() {
          this.usuarios.values().forEach(System.out::println);
     }
     
     public boolean atualizarLogin(int id, String login) {
          Usuario antigoUsuario = this.usuarios.get(id);
          if(antigoUsuario == null) {
               return false;
          }
          this.usuarios.put(id, new Usuario(id, login, antigoUsuario.getSenha(), antigoUsuario.isAdministrador()));
          return true;
     }
     
     public boolean atualizarSenha(int id, String senha) {
          Usuario antigoUsuario = this.usuarios.get(id);
          if(antigoUsuario == null) {
               return false;
          }
          this.usuarios.put(id, new Usuario(id, antigoUsuario.getLogin(), senha, antigoUsuario.isAdministrador()));
          return true;
     }
     
     public boolean atualizarPermissao(int id, boolean administrador) {
          Usuario antigoUsuario = this.usuarios.get(id);
          if(antigoUsuario == null) {
               return false;
          }
          this.usuarios.put(id, new Usuario(id, antigoUsuario.getLogin(), antigoUsuario.getSenha(), administrador));
          return true;
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
          try( FileOutputStream arquivoUsuarios = new FileOutputStream(arquivoBanco);
               ObjectOutputStream usuariosStream = new ObjectOutputStream(arquivoUsuarios);
          )
          {
               usuariosStream.writeObject(idIncremento);
               for(Usuario usuario : this.usuarios.values()) {
                    usuariosStream.writeObject(usuario);
               }
          }
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

     /**
      * Retorna se tem ou não usuários cadastrados
      * no sistema.
      */
     public boolean isVazio() {
          return this.usuarios.size() == 0;
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
          try( FileInputStream arquivoUsuarios = new FileInputStream(arquivoBackup);
               ObjectInputStream usuariosStream = new ObjectInputStream(arquivoUsuarios);
          )
          {
               idIncremento = (Integer) usuariosStream.readObject();
               Usuario usuarioTemporario;
               while(true) {
                    try {
                         usuarioTemporario = (Usuario) usuariosStream.readObject();
                         usuarios.put(usuarioTemporario.getId(), usuarioTemporario);
                    }
                    catch(EOFException e) {
                         break;
                    }
               }
          }
          // O arquivo não pôde ser encontrado
          catch(FileNotFoundException e) {
               System.out.println("Arquivo não encontrado.");
          }
          catch(ClassNotFoundException e) {
              System.out.println("Tentando ler um objeto de uma classe desconhecida.");
          }
          catch(StreamCorruptedException e) { // thrown by the constructor ObjectInputStream
              System.out.println("Formato do arquivo não é válido.");
          }
          // Arquivo sem cabeçalho/vazio
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }
}
