package br.com.sga.aplicacao;

import static br.com.sga.aplicacao.AppSGA.bancoAlunos;
import static br.com.sga.aplicacao.AppSGA.cabecalhoSGA;
import static br.com.sga.aplicacao.AppSGA.esperarEnter;
import static br.com.sga.aplicacao.AppSGA.leitor;
import static br.com.sga.aplicacao.AppSGA.limparBuffer;
import static br.com.sga.aplicacao.AppSGA.limparConsole;

import java.io.IOException;
import java.util.List;

import br.com.sga.datehelper.DateHelper;
import br.com.sga.identidade.Endereco;
import br.com.sga.identidade.Estado;
import br.com.sga.identidade.ValidacaoDadosPessoais;
import br.com.sga.pessoal.Aluno;
import br.com.sga.pessoal.Sexo;

public class MenuAluno {

     public static void menuAluno() throws IOException {
          int opcaoMenu;
          do {
               cabecalhoSGA();
               System.out.print("\033[1;94m1) \033[0;94mAdicionar um Aluno\033[0m\n" +
                         "\033[1;94m2) \033[0;94mListar os Alunos\033[0m\n" +
                         "\033[1;94m3) \033[0;94mAtualizar um Aluno\033[0m\n" +
                         "\033[1;94m4) \033[0;94mRemover um Aluno\033[0m\n" +
                         "\033[1;94m5) \033[0;94mRetornar\n\033[1;97m>\033[0m ");

               opcaoMenu = leitor.nextInt();
               limparBuffer();
               switch (opcaoMenu) {
                    case 1:
                         adicionarAluno();
                         break;
                    case 2:
                         listarAlunos();
                         break;
                    case 3:
                         atualizarAluno();
                         break;
                    case 4:
                         removerAluno();
                         break;
                    case 5:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 5);
     }

     public static void adicionarAluno() throws IOException {
          Aluno aluno = new Aluno();

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o nome do aluno:\n> ");
                    aluno.setNome(leitor.nextLine());
                    if(aluno.getNome().isEmpty()) {
                         System.out.println("Nome inválido! O nome do aluno deve ter no mínimo 1 caractere!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }  

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o telefone do aluno:\n> ");
                    aluno.setTelefone(leitor.nextLine());
                    if(!ValidacaoDadosPessoais.validarTelefone(aluno.getTelefone())) {
                         System.out.println("Telefone inválido!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }  

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o sexo do aluno (F - Feminino, M - Masculino, O - Outro):\n> ");
                    String letra = leitor.nextLine();
                    if(!letra.equals("F") && !letra.equals("M") && !letra.equals("O")) {
                         System.out.println("Dígito inválido! Deve digitar 1, 2 ou 3!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    switch (letra) {
                         case "M":
                              aluno.setSexo(Sexo.MASCULINO);
                              break;
                         case "F":
                              aluno.setSexo(Sexo.FEMININO);
                              break;
                         case "O":
                              aluno.setSexo(Sexo.OUTRO);
                              break;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          } 


          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o CPF do aluno:\n> ");
                    aluno.setCpf(leitor.nextLine());
                    if(!ValidacaoDadosPessoais.validarRegistroNacional(aluno.getCpf())) {
                         System.out.println("CPF inválido!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira a data de nascimento no seguinte formato (dd/MM/aaaa):\n> ");
                    aluno.setDataNascimento(new DateHelper(leitor.nextLine()));
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println("Data em formato inválido! Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o e-mail do aluno:\n> ");
                    aluno.setEmail(leitor.nextLine());
                    if(!ValidacaoDadosPessoais.validarEmail(aluno.getEmail())) {
                         System.out.println("E-mail inválido!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }  

          aluno.setEndereco(new Endereco());

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe qual o logradouro:\n> ");
                    aluno.getEndereco().setLogradouro(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o número (sem letras adjacentes):\n> ");
                    aluno.getEndereco().setNumero(leitor.nextInt());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparBuffer();
                    limparConsole(); 
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o Bairro:\n> ");
                    aluno.getEndereco().setBairro(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe a Cidade:\n> ");
                    aluno.getEndereco().setCidade(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               } 
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o Estado (apenas a sigla):\n> ");
                    aluno.getEndereco().setEstado(Estado.valueOf(leitor.nextLine().toUpperCase()));
                    break;
               }
               catch (Exception e) {
                    System.out.println("Informe uma sigla de estado válida!\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               } 
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o CEP:\n> ");
                    aluno.getEndereco().setCep(leitor.next());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparBuffer();
                    limparConsole(); 
               }
          }

          cabecalhoSGA();
          bancoAlunos.criarAluno(aluno);
          System.out.println("O aluno foi criado com sucesso!\nPressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole(); 
     }

     public static void listarAlunos() throws IOException {
          cabecalhoSGA();
          List<Aluno> alunosListar = bancoAlunos.listarAlunos();
          System.out.println("\033[1;32mLista de Alunos\033[0m:\n");
          for (Aluno aluno : alunosListar) {
               System.out.println(aluno);
          }
          System.out.println("Pressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     public static void atualizarAluno() throws IOException {

     }

     public static void removerAluno() throws IOException {
          int matricula = 0;

          while(true) {
               try {
                    System.out.print("Insira o número da matrícula do Aluno:\n> ");
                    matricula = leitor.nextInt();
                    Aluno aluno = bancoAlunos.obterAluno(matricula);
                    if (aluno == null) {
                         throw new Exception("Aluno não existente");
                    }
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
          }     

          limparConsole();
          cabecalhoSGA();
          if (bancoAlunos.removerAluno(matricula)) {
               System.out.println("Aluno removido com sucesso! Pressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("O aluno não foi removido, rente novamente! Pressione \033[1;32mENTER\033[0m para voltar.");
          }

          esperarEnter();
          limparConsole(); 
     }  
}