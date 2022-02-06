package br.com.sga.aplicacao;

import static br.com.sga.aplicacao.AppSGA.*;

import java.io.File;

import br.com.sga.financeiro.Caixa;

/**
 * <p>
 * Encapsula as operações relacionadas ao menu de configurações do sistema.
 * </p>
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class MenuConfiguracoes {

     /**
      * A opção pra navegação no menu.
      */
     private static int opcaoMenu;

     /**
      * Mostra as configurações do sistema, como logout, backup e banco de dados
      * ou mostrar informações do sistema.
      */
     public static void menuConfiguracoes() {
          do {
               cabecalhoSGA();

               System.out.print("\033[1;94m1)\033[0m \033[0;94mRealizar Logout\033[0m\n" +
                         "\033[1;94m2)\033[0m \033[0;94mSalvar Dados no Banco\033[0m\n" +
                         "\033[1;94m3)\033[0m \033[0;94mApagar os Dados do Sistema\033[0m\n" +
                         "\033[1;94m4)\033[0m \033[0;94mRealizar Backup\033[0m\n" +
                         "\033[1;94m5)\033[0m \033[0;94mRestaurar Backup\033[0m\n" +
                         "\033[1;94m6)\033[0m \033[0;94mSobre o SGA\033[0m\n" +
                         "\033[1;94m7)\033[0m \033[0;94mRetornar\033[0m\n\033[1;97m>\033[0m ");
                         
               opcaoMenu = leitor.nextInt();
               limparBuffer();           
               switch (opcaoMenu) {
                    case 1:
                         logout();
                         break;
                    case 2:
                         salvarTodosDados();
                         break;
                    case 3:
                         apagarDados();
                         break;
                    case 4:
                         realizarBackup();
                         break;
                    case 5:
                         restaurarBackup();
                         break;
                    case 6:
                         sobre();
                         break;
                    case 7:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 7);
     }
     
     /**
      * Salva todos os dados nos bancos de dados.
      */
     public static void salvarTodosDados() {
          cabecalhoSGA();
          salvarDados();
          System.out.println("Dados salvos com sucesso!\nPressione \033[1;32mENTER\033[0m para retornar ao menu.");
          esperarEnter();
     }

     /**
      * Pede para o usuário fazer o login novamente.
      */
     public static void logout() {
          cabecalhoSGA();
          System.out.println("Você realizou o logout!\nPressione \033[1;32mENTER\033[0m para fazer o login novamente.");
          esperarEnter();
          loginSistema();
          opcaoMenu = 7;
     }

     /**
      * Apaga todos os dados do banco e finaliza o programa.
      */
     public static void apagarDados() {
          while(true) {
               cabecalhoSGA();
               System.out.print("\033[1;93mATENÇÃO!!!\033[0m Essa ação apagará \033[1;93mTODOS\033[0m os dados do sistema!" +
               "\nPortanto, \033[0;94mNÃO será possível recuperar os dados após apagados!\033[0m" +
               "\nCaso queira \033[1;94mseguir em frente e apagar os dados\033[0m, pressione \033[1;31mS\033[0m." +
               "\nCaso queira \033[1;94mvoltar ao menu de configurações\033[0m, pressione \033[1;32mENTER\033[0m.\n\033[1;97m>\033[0m ");

               String escolha = leitor.nextLine().toUpperCase();
               if (escolha.isEmpty()) {
                    limparConsole();
                    return;
               }
               else if(escolha.equals("S")) {
                    cabecalhoSGA();
                    System.out.println("O sistema agora irá finalizar sua execução.\nAté a próxima!");
                    bancoAlunos.apagarDados();
                    bancoEmpresa.apagarDados();
                    bancoHistoricoCaixa.apagarDados();
                    bancoUsuarios.apagarDados();
                    System.exit(0);
               }
               else {
                    System.out.println("Opção inválida! Pressione \033[1;32mENTER\033[0m para tentar de novo.");
               }
          }
     }

     /**
      * Mostra as informações sobre o sistema, como a versão atual, equipe de
      * desenvolvedores e o site!
      */
     public static void sobre() {
          cabecalhoSGA();
          System.out.println("Em caso de dúvidas na utilização do sistema consulte o nosso \033[1;94msite oficial!\033[0m\n" +
          "A \033[0;94mequipe SGA\033[0m agradece a preferência!\n");   
          System.out.println("\033[1;33mSite Oficial: \033[1;32mhttps://github.com/pedrobotelho15/SGA\033[0m");
          System.out.println("\033[1;33mEquipe Técnica: \033[1;32mPedro Botelho & Daniel Vitor\033[0m");
          System.out.println("\033[1;33mVersão Atual: \033[1;32m1.1\033[0m");
          System.out.println("\nPressione \033[1;32mENTER\033[0m para voltar às configurações.");
          esperarEnter();
     }

     /**
      * Restaura o backup do caminho informado no
      * banco de dados local.
      */
     public static void restaurarBackup() {

          while (true) {
               try { 
                    cabecalhoSGA();
                    System.out.print("Informe o caminho completo da pasta do backup:\n> ");
                    String caminhoBackup = leitor.nextLine();
                    if(caminhoBackup.charAt(caminhoBackup.length() - 1) != '/') {
                         caminhoBackup = caminhoBackup.concat("/");
                    }

                    while(!new File(caminhoBackup).exists()) {
                         cabecalhoSGA();
                         System.out.print("O diretório informado não existe!\nDigite um caminho válido ou pressione \033[1;32mENTER\033[0m para voltar.\n> ");
                         String escolha = leitor.nextLine();
                         if (escolha.isEmpty()) {
                              return;
                         }
                         else if(!new File(escolha).exists()){
                              continue;
                         }
                         else {
                              break;
                         }
                    }

                    try {
                         bancoAlunos.restaurarBackup(caminhoBackup + "Alunos.bin");
                    }
                    catch (Exception e) {
                         cabecalhoSGA();
                         System.out.println("Não foi possível abrir o arquivo Alunos.bin!\nPressione \033[1;32mENTER\033[0m para continuar.");
                         esperarEnter();
                    }

                    try {
                         bancoEmpresa.restaurarBackup(caminhoBackup + "Empresa.bin");
                    }
                    catch (Exception e) {
                         cabecalhoSGA();
                         System.out.println("Não foi possível abrir o arquivo Empresa.bin!\nPressione \033[1;32mENTER\033[0m para continuar.");
                         esperarEnter();
                    }

                    try {
                         bancoHistoricoCaixa.restaurarBackup(caminhoBackup + "HistoricoCaixa.bin");
                    }
                    catch (Exception e) {
                         cabecalhoSGA();
                         System.out.println("Não foi possível abrir o arquivo HistoricoCaixa.bin!\nPressione \033[1;32mENTER\033[0m para continuar.");
                         esperarEnter();
                    }

                    try {
                         bancoUsuarios.restaurarBackup(caminhoBackup + "Usuarios.bin");
                    }
                    catch (Exception e) {
                         cabecalhoSGA();
                         System.out.println("Não foi possível abrir o arquivo Usuarios.bin!\nPressione \033[1;32mENTER\033[0m para continuar.");
                         esperarEnter();
                    }
                    salvarDados();


                    caixa = new Caixa(bancoAlunos.listarAlunos(), bancoHistoricoCaixa.getHistoricoCaixa());

                    cabecalhoSGA();
                    System.out.println("O backup foi devidamente restaurado!\nPressione \033[1;32mENTER\033[0m para retornar ao menu.");
                    esperarEnter();
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

     }

     /**
      * Exporta o banco de dados para o caminho informado.
      */
     public static void realizarBackup() {

          while (true) {
               try { 
                    cabecalhoSGA();
                    System.out.print("Informe o caminho completo para a pasta onde ficará o backup:\n> ");
                    String caminhoBackup = leitor.nextLine();
                    if(caminhoBackup.charAt(caminhoBackup.length() - 1) != '/') {
                         caminhoBackup = caminhoBackup.concat("/");
                    }

                    File diretorioBackup = new File(caminhoBackup);
                    if(!diretorioBackup.exists()) {
                         diretorioBackup.mkdirs();
                    }

                    bancoAlunos.salvarDados(caminhoBackup + "Alunos.bin");
                    bancoEmpresa.salvarDados(caminhoBackup + "Empresa.bin");
                    bancoHistoricoCaixa.salvarDados(caminhoBackup + "HistoricoCaixa.bin");
                    bancoUsuarios.salvarDados(caminhoBackup + "Usuarios.bin");

                    cabecalhoSGA();
                    System.out.println("O backup foi devidamente realizado!\nPressione \033[1;32mENTER\033[0m para retornar ao menu.");
                    esperarEnter();
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
     }
}
