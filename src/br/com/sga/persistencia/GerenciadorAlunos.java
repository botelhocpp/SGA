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
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import br.com.sga.pessoal.Aluno;
import br.com.sga.pessoal.Sexo;
import br.com.sga.identidade.Endereco;

public class GerenciadorAlunos extends Gerenciador {

     private static int matriculaIncremento = 1;
     private Map<Integer, Aluno> alunos;

     public GerenciadorAlunos(String caminhoBanco) throws IOException {
          super(caminhoBanco);
          this.alunos = new TreeMap<>();

          try (
               FileInputStream arquivoAlunos = new FileInputStream(caminhoBanco);
               ObjectInputStream alunosStream = new ObjectInputStream(arquivoAlunos);
          ) {
               Aluno alunoTemporario;
               while (true) {
                    try {
                         alunoTemporario = (Aluno) alunosStream.readObject();
                         alunos.put(alunoTemporario.getMatricula(), alunoTemporario);
                    } catch (EOFException e) {
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

     public void criarAluno(String nome, String telefone, Sexo sexo, String cpf, String dataNascimento, String email, Endereco endereco) {
          Aluno novoAluno = new Aluno(nome, telefone, sexo, cpf, dataNascimento, email, endereco, matriculaIncremento++);
          this.alunos.put(novoAluno.getMatricula(), novoAluno);
     }

     public Aluno obterAluno(Integer matricula) {
          if (matricula < matriculaIncremento) {
               return alunos.get(matricula);
          }
          return null;
     }

     public boolean removerAluno(Integer matricula) {
          if (matricula < matriculaIncremento) {
               alunos.remove(matricula);
               return true;
          }
          return false;
     }

     public List<Aluno> listarAlunos() {
          List<Aluno> alunosListar = new ArrayList<>();

          for (Map.Entry<Integer, Aluno> e : alunos.entrySet()) {
               alunosListar.add(obterAluno(e.getKey()));
           }

          return alunosListar;
     }

     public void atualizarAluno(Aluno aluno) {
          this.alunos.put(aluno.getMatricula(), aluno);
     }

     @Override
     public void salvarDados() {
          try (
               FileOutputStream arquivoAlunos = new FileOutputStream(this.arquivoBanco);
               ObjectOutputStream alunosStream = new ObjectOutputStream(arquivoAlunos);
          ) {
               for (Aluno aluno : this.alunos.values()) {
                    alunosStream.writeObject(aluno);
               }
          }
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }
     
}
