package br.com.sga.aplicacao;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import br.com.sga.empresa.Usuario;
import br.com.sga.persistencia.GerenciadorHistoricoCaixa;
import br.com.sga.persistencia.GerenciadorEmpresa;
import br.com.sga.persistencia.GerenciadorUsuarios;
import br.com.sga.pessoal.Aluno;
import br.com.sga.persistencia.GerenciadorAlunos;
import br.com.sga.financeiro.Caixa;

import static br.com.sga.aplicacao.MenuUsuario.*;
import static br.com.sga.aplicacao.MenuEmpresa.*;
import static br.com.sga.aplicacao.MenuPagamento.*;
import static br.com.sga.aplicacao.MenuAluno.*;
import static br.com.sga.aplicacao.MenuConfiguracoes.*;

/**
 * <p>
 * A classe principal do sistema! Os menus específicos forma modularizados
 * em outras classes para melhorar a organização.
 * </p>
 * 
 * <p>
 * Contém métodos e atributos relativos a tela inicial do sistema.
 * Essa classe encapsula as princpais operações relativas ao backend 
 * e frontend básico do sistema.
 * </p>
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class AppSGA {

     /**
      * Inicia a aplicação!
      */
     public static void main(String[] args) {

          // Inicializa todos os gerenciadores de bancos.
          inicializarBancos();

          // Configura as informações da empresa no primeiro uso.
          if (bancoEmpresa.bancoVazio()) {
               cabecalhoSGA();
               System.out.println("Olá! Obrigado por adquirir o SGA!\n"
                         + "Iremos agora realizar as configurações iniciais da sua empresa.\n"
                         + "Pressione \033[1;32mENTER\033[0m para configurarmos os dados da sua empresa.");
               esperarEnter();
               inicializarEmpresa();
          }

          // Configura um usuário administrador do sistema no primeiro uso.
          if (bancoUsuarios.isVazio()) {
               cabecalhoSGA();
               System.out.println("Agora vamos criar o usuário administrador do sistema." + 
               "\nPressione \033[1;32mENTER\033[0m para configurarmos o usuário.");
               esperarEnter();
               criarAdministrador();
          }

          // Realiza o login no sistema com um usuário criado.
          loginSistema();

          // Apresenta o(s) menu(s) para navegação na aplicação.
          menuPrincipal();

          // Sistema em etapa de finalização
          salvarDados();
          System.out.println("Volte sempre!");
          leitor.close();
     }

     // ------------------------------------------------------------------------
     // Atributos Gerais da Aplicação
     // ------------------------------------------------------------------------

     public static Scanner leitor = new Scanner(System.in);
     public static GerenciadorHistoricoCaixa bancoHistoricoCaixa;
     public static GerenciadorUsuarios bancoUsuarios;
     public static GerenciadorEmpresa bancoEmpresa;
     public static GerenciadorAlunos bancoAlunos;

     /**
      * O usuário que está usando o sistema no momento.
      */
     public static Usuario usuarioAtual;

     /**
      * O objeto que fará o controle do caixa.
      */
     public static Caixa caixa;

     // ------------------------------------------------------------------------
     // Métodos do Back-end da Aplicação
     // ------------------------------------------------------------------------

     /**
      * Inicializa os gerenciadores do banco de dados, assim como o gerenciador
      * do caixa, para manter os dados ao salvar. 
      */
     public static void inicializarBancos() {
          try {
               bancoHistoricoCaixa = new GerenciadorHistoricoCaixa("database/HistoricoCaixa.bin");
               bancoUsuarios = new GerenciadorUsuarios("database/Usuarios.bin");
               bancoEmpresa = new GerenciadorEmpresa("database/Empresa.bin");
               bancoAlunos = new GerenciadorAlunos("database/Alunos.bin");
               caixa = new Caixa(bancoAlunos.listarAlunos(), bancoHistoricoCaixa.getHistoricoCaixa());
          }
          catch (IOException e) {
               System.out.println(e.getMessage());
          }
     }

     /**
      * Realiza o salvamento dos dados nos bancos do diretório database/.
      */
     public static void salvarDados() {
          bancoHistoricoCaixa.salvarDados();
          bancoUsuarios.salvarDados();
          bancoEmpresa.salvarDados();
          bancoAlunos.salvarDados();
     }

     /**
      * Realiza o login no sistema por meio de um usuário
      * cadastrado previamente.
      */
     public static void loginSistema() {
          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o login do usuário:\n> ");
                    String login = leitor.next();

                    limparBuffer();
                    cabecalhoSGA();
                    System.out.print("Insira a senha do usuário:\n> ");
                    String senha = leitor.next();
                    usuarioAtual = bancoUsuarios.obterUsuario(login, senha);
                    
                    limparBuffer();
                    cabecalhoSGA();
                    if (usuarioAtual == null) {
                         System.out.println("Dados inválidos! Pressione \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
                    }
                    else {
                         System.out.println("Seja bem vindo! Pressione \033[1;32mENTER\033[0m para continuar.");
                         esperarEnter();
                         break;
                    }
               }
               catch (Exception e) {
                    System.out.println(e.getMessage());
               }
          }
     }

     // ------------------------------------------------------------------------
     // Métodos do Front-end da Aplicação
     // ------------------------------------------------------------------------

     /**
      * Limpa a tela do terminal.
      */
     public static void limparConsole() {
          System.out.print("\033[H\033[J");
          System.out.flush();
     }

     /**
      * Limpa o "\n" residual no buffer.
      */
     public static void limparBuffer() {
          if(leitor.hasNextLine()) {
               leitor.nextLine();
          }
     }

     /**
      * Mostra o cabeçalho do sistema, que sempre está a
      * mostra na interface do sistema!
      */
     public static void cabecalhoSGA() {
          limparConsole();
          System.out.println("\t\t\033[1;94m-------------------------------------------\033[0m");
          System.out.println("\t\t\033[1;93mSistema de Gerenciamento de Academias (SGA)\033[0m");
          System.out.println("\t\t\033[1;94m-------------------------------------------\033[0m\n");
     }

     /**
      * Reconhece o pressionar da tecla ENTER.
      */
     public static void esperarEnter() {
          try {
               System.in.read();
          }
          catch(IOException e) {
               System.out.println(e.getMessage());
          }
     }

     /**
      * O menu principal da interface! Leva o usuário a
      * outros menus, baseado em sua escolha.
      */
     public static void menuPrincipal() {
          int opcaoMenu;
          do {
               cabecalhoSGA();

               System.out.print("\033[1;94m1)\033[0m \033[0;94mControle de Entrada\033[0m\n" +
                         "\033[1;94m2)\033[0m \033[0;94mGerenciar Empresa\033[0m\n" +
                         "\033[1;94m3)\033[0m \033[0;94mGerenciar Usuários\033[0m\n" +
                         "\033[1;94m4)\033[0m \033[0;94mGerenciar Alunos\033[0m\n" +
                         "\033[1;94m5)\033[0m \033[0;94mGerenciar Pagamentos\033[0m\n" +
                         "\033[1;94m6)\033[0m \033[0;94mConfigurações do Sistema\033[0m\n" +
                         "\033[1;94m7)\033[0m \033[0;94mSair do Sistema\033[0m\n\033[1;97m>\033[0m ");
                         
               opcaoMenu = leitor.nextInt();
               limparBuffer();           
               switch (opcaoMenu) {
                    case 1:
                         controleEntrada();
                         break;
                    case 2:
                         menuEmpresa();
                         break;
                    case 3:
                         menuUsuario();
                         break;
                    case 4:
                         menuAluno();
                         break;
                    case 5:
                         menuPagamentos();
                         break;
                    case 6:
                         menuConfiguracoes();
                         break;
                    case 7:
                         cabecalhoSGA();
                         System.out.println("Finalizando o Sistema. Aguarde...");
                         caixa.fecharCaixa();
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 7);
     }

     /**
      * Realiza o controle de entrada dos alunos: É inserido
      * o ID dos alunos, e o sistema irá indicar se o seu
      * acesso está liberado ou não, baseado em se o aluno
      * está em dias.
      */
     public static void controleEntrada() {
          String buffer;
          int matricula = 0;

          Aluno alunoAcesso = null;

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o número da matrícula do aluno ou pressione \033[1;32mENTER\033[0m para voltar ao menu.\n> ");
                    buffer = leitor.nextLine();
                    if (buffer.isEmpty()) {
                         return;
                    }
                    matricula = Integer.parseInt(buffer);
                    alunoAcesso = bancoAlunos.obterAluno(matricula);
                    if (alunoAcesso == null) {
                         throw new Exception("Aluno não existente!\n");
                    }
                    else {
                         cabecalhoSGA();
                         List<Aluno> alunosMensalidadeEmDia = caixa.listarAlunosMensalidadeEmDia();
                         for (Aluno aluno : alunosMensalidadeEmDia) {
                              if (alunoAcesso.getMatricula() == aluno.getMatricula()) {
                                   throw new Exception("Aluno: " + alunoAcesso.getNome() + 
                                   "\nCPF: " + alunoAcesso.getCpf() +
                                   "\n\n\033[1;32mACESSO PERMITIDO!!!\033[0m\n");
                              }
                         }
                         throw new Exception("Aluno: " + alunoAcesso.getNome() + 
                         "\nCPF: " + alunoAcesso.getCpf() +
                         "\n\n\033[1;31mACESSO NEGADO!!!\033[0m\n");
                    }
               }
               catch (NumberFormatException e) {
                    cabecalhoSGA();
                    System.out.println("Insira um número de matrícula válido!\nPressione \033[1;32mENTER\033[0m para voltar ao controle de acesso.");
                    esperarEnter();
               }
               catch (Exception e) {
                    cabecalhoSGA();
                    System.out.println(e.getMessage() + "\nPressione \033[1;32mENTER\033[0m para voltar ao controle de acesso.");
                    esperarEnter();
               }
          }
     }
     
}