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

import br.com.sga.empresa.Usuario;

public class GerenciadorUsuarios {

     private String arquivoBanco;
     private static int idIncremento = 1;
     private Map<Integer, Usuario> usuarios;

     public GerenciadorUsuarios(String caminhoBanco) throws IOException {
          this.setBanco(caminhoBanco);
          this.usuarios = new TreeMap<>();

          try( FileInputStream arquivoUsuarios = new FileInputStream(caminhoBanco);
               ObjectInputStream usuariosStream = new ObjectInputStream(arquivoUsuarios);
          )
          {
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

     public void removerUsuario(int id) {
          this.usuarios.remove(id);
     }
     
     public void listarUsuario() {
          this.usuarios.values().forEach(System.out::println);
     }
     
     public void atualizarLogin(int id, String login) {
          Usuario antigoUsuario = this.usuarios.get(id);
          this.usuarios.put(id, new Usuario(id, login, antigoUsuario.getSenha(), antigoUsuario.isAdministrador()));
     }

     public void salvarUsuarios() {
          try( FileOutputStream arquivoUsuarios = new FileOutputStream(this.arquivoBanco);
               ObjectOutputStream usuariosStream = new ObjectOutputStream(arquivoUsuarios);
          )
          {
               for(Usuario usuario : this.usuarios.values()) {
                    usuariosStream.writeObject(usuario);
               }
          }
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

     private void setBanco(String arquivoBanco) throws IllegalArgumentException {
          if(arquivoBanco == null || arquivoBanco.isEmpty()) {
               throw new IllegalArgumentException("Insira um caminho para o arquivo.");
          }
          this.arquivoBanco = arquivoBanco;
     }

     public String getBanco() {
          return this.arquivoBanco;
     }

     public boolean isVazio() {
          return this.usuarios.size() == 0;
     }
}
