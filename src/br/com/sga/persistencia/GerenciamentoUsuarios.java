package br.com.sga.persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import br.com.sga.empresa.Usuario;

public class GerenciamentoUsuarios {
     public RandomAccessFile arquivoBanco;

     public GerenciamentoUsuarios(String caminhoBanco) throws FileNotFoundException {
          this.arquivoBanco = new RandomAccessFile(caminhoBanco, "rw");
     }

     public void criarUsuario(Usuario usuario) throws IOException {
          this.arquivoBanco.seek(this.arquivoBanco.length());
          this.arquivoBanco.writeUTF(usuario.getLogin());
          this.arquivoBanco.writeUTF(usuario.getSenha());
          this.arquivoBanco.writeBoolean(usuario.isAdministrador());
     }

     public void removerUsuario() {
        
     }
     
     public void listarUsuario() {
        
     }
     
     public void atualizarUsuario() {
        
     }
}
