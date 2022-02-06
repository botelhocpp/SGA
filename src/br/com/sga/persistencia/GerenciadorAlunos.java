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
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import br.com.sga.pessoal.Aluno;
import br.com.sga.pessoal.Sexo;
import br.com.sga.identidade.Endereco;

import br.com.sga.datehelper.DateHelper;

import br.com.sga.financeiro.Pagamento;

/**
 * Modela um gerenciador de banco de
 * dados dos alunos.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class GerenciadorAlunos extends Gerenciador {

     /**
      * Guarda a próxima matrícula elencada a um
      * aluno na criação.
      */
     private static int matriculaIncremento = 1;

     /**
      * Guarda uma lista de alunos mapeada a seu
      * número de matrícula.
      */
     private Map<Integer, Aluno> alunos;

     /**
      * Configura o caminho do banco de dados e restaura
      * os dados para a aplicação, caso tenha algum.
      */
     public GerenciadorAlunos(String caminhoBanco) throws IOException {
          super(caminhoBanco);
          this.alunos = new TreeMap<>();
          restaurarDados(caminhoBanco);
     }

     public void criarAluno(Aluno aluno) {
          aluno.setMatricula(matriculaIncremento++);
          this.alunos.put(aluno.getMatricula(), aluno);
     }

     public void criarAluno(String nome, String telefone, Sexo sexo, String cpf, DateHelper dataNascimento, String email, Endereco endereco) {
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

     /**
      * Retorna uma lista com todos os alunos.
      */
     public List<Aluno> listarAlunos() {
          List<Aluno> alunosListar = new ArrayList<>();

          for (Map.Entry<Integer, Aluno> e : alunos.entrySet()) {
               alunosListar.add(e.getValue());
           }

          return alunosListar;
     }

     /**
      * Retorna uma lista com todas as mensalidades pagas 
      * pelo aluno informadp.
      */
    public List<Pagamento> listarMensalidadesPorAluno(int matricula) {
        return obterAluno(matricula).getPagamentos();
    }

     public boolean atualizarAluno(Aluno aluno) {
          Aluno aluno_anterior = obterAluno(aluno.getMatricula());
          if (aluno.equals(aluno_anterior)) {
               return false;
          }
          this.alunos.put(aluno.getMatricula(), aluno);
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
          try (
               FileOutputStream arquivoAlunos = new FileOutputStream(arquivoBanco);
               ObjectOutputStream alunosStream = new ObjectOutputStream(arquivoAlunos);
          ) {
               alunosStream.writeObject(matriculaIncremento);
               for (Aluno aluno : this.alunos.values()) {
                    alunosStream.writeObject(aluno);
               }
          }
          catch(IOException e) {
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
          try (
               FileInputStream arquivoAlunos = new FileInputStream(arquivoBackup);
               ObjectInputStream alunosStream = new ObjectInputStream(arquivoAlunos);
          ) {
               Aluno alunoTemporario;
               matriculaIncremento = (int) alunosStream.readObject();
               while (true) {
                    try {
                         alunoTemporario = (Aluno) alunosStream.readObject();
                         this.alunos.put(alunoTemporario.getMatricula(), alunoTemporario);
                    }
                    catch (EOFException e) {
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
