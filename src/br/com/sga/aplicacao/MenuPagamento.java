package br.com.sga.aplicacao;

import static br.com.sga.aplicacao.AppSGA.bancoAlunos;
import static br.com.sga.aplicacao.AppSGA.cabecalhoSGA;
import static br.com.sga.aplicacao.AppSGA.caixa;
import static br.com.sga.aplicacao.AppSGA.esperarEnter;
import static br.com.sga.aplicacao.AppSGA.leitor;
import static br.com.sga.aplicacao.AppSGA.limparBuffer;
import static br.com.sga.aplicacao.AppSGA.limparConsole;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import br.com.sga.datehelper.DateHelper;
import br.com.sga.financeiro.Pagamento;
import br.com.sga.pessoal.Aluno;

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
public class MenuPagamento {
     
     /**
      * Mostra o menu para a gerencia do caixa
      * e dos pagamentos dos alunos.
      */
     public static void menuPagamentos() {
          int opcaoMenu = 0;
          do {
               cabecalhoSGA();
               System.out.print("\033[1;94m1) \033[0;94mAbrir caixa do dia\033[0m\n" + 
                              "\033[1;94m2) \033[0;94mFechar caixa do dia\033[0m\n" +
                              "\033[1;94m3) \033[0;94mFazer pagamento da mensalidade do Aluno\033[0m\n" +
                              "\033[1;94m4) \033[0;94mListar alunos com mensalidade em dia\033[0m\n" + 
                              "\033[1;94m5) \033[0;94mListar alunos com mensalidade atrasada\033[0m\n" + 
                              "\033[1;94m6) \033[0;94mListar o histórico do caixa\033[0m\n" + 
                              "\033[1;94m7) \033[0;94mRetornar\n\033[1;97m>\033[0m ");

               opcaoMenu = leitor.nextInt();
               limparBuffer();           
               switch (opcaoMenu) {
                    case 1:
                         abrirCaixaDoDia();
                         break;
                    case 2:
                         fecharCaixaDoDia();
                         break;
                    case 3:
                         fazerPagamentoAluno();
                         break;
                    case 4:
                         listarAlunosMensalidadeEmDia();
                         break;
                    case 5:
                         listarAlunosMensalidadeAtrasada();
                         break;
                    case 6:
                         listarCaixa();
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
      * Abre o caixa para que novas operações financeiras
      * possam ser realizadas.
      */
     public static void abrirCaixaDoDia() {
          cabecalhoSGA();
          if (caixa.abrirCaixa()) {
               System.out.println("Caixa aberto com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Caixa já está aberto!\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     /**
      * Fecha o caixa, de forma que novas operações financeiras
      * só sejam possíveis ao abrir um novo caixa. Isso salva
      * no histórico de caixas com a data corrente!
      */
     public static void fecharCaixaDoDia() {
          cabecalhoSGA();
          if (caixa.fecharCaixa()) {
               System.out.println("Caixa fechado com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Caixa já está fechado!\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     /**
      * Realiza o pagamento da mensalidade do aluno, dado
      * um valor. O aluno ficará em dias!
      */
     public static void fazerPagamentoAluno() {
          if (!caixa.obterEstadoCaixa()) {
               cabecalhoSGA();
               System.out.println("Caixa está fechado!\nPressione \033[1;32mENTER\033[0m para voltar.");
               esperarEnter();
               limparConsole(); 
               return;
          }

          int matricula = 0;
          Double mensalidade;
          DateHelper dataAtual = new DateHelper(new Date());

          Aluno aluno = null;

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o número da matrícula do Aluno:\n> ");
                    matricula = leitor.nextInt();
                    aluno = bancoAlunos.obterAluno(matricula);
                    if (aluno == null) {
                         throw new Exception("Aluno não existente");
                    }
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Digite o valor da mensalidade do Aluno:\n> ");
                    mensalidade = leitor.nextDouble();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }
          
          cabecalhoSGA();

          Pagamento pagamento = new Pagamento(dataAtual, mensalidade);

          if (caixa.fazerPagamento(matricula, pagamento)) {
               aluno.adicionarPagamento(pagamento);
               System.out.println("Pagamento feito com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Falha ao fazer pagamento! Motivo: Caixa fechado, espere o próximo dia\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          
          esperarEnter();
          limparConsole(); 
     }
     
     /**
      * Lista os alunos que estão com a mensalidade em
      * dias.
      */
     public static void listarAlunosMensalidadeEmDia() {
          cabecalhoSGA();
          List<Aluno> alunosMensalidadeEmDia = caixa.listarAlunosMensalidadeEmDia();
          System.out.println("Alunos com mensalidade em dia:\n");
          for (Aluno aluno : alunosMensalidadeEmDia) {
               System.out.println("- " + aluno.getNome());
          }
          System.out.println("\nPressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     /**
      * Lista os alunos que não estão com a mensalidade em
      * dias.
      */
     public static void listarAlunosMensalidadeAtrasada() {
          cabecalhoSGA();
          List<Aluno> alunosMensalidadeAtrasada = caixa.listarAlunosMensalidadeAtrasada();
          System.out.println("Alunos com mensalidade atrasada:\n");
          for (Aluno aluno : alunosMensalidadeAtrasada) {
               System.out.println("- " + aluno.getNome());
          }
          System.out.println("\nPressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     /**
      * Procura o saldo do caixa baseado em uma data.
      */
     public static void buscarSaldoDiarioPorData() {
          DateHelper data;
          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira uma data no seguinte formato (dd/MM/aaaa):\n> ");
                    data = new DateHelper(leitor.nextLine());
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }
          
          cabecalhoSGA();
          DecimalFormat decimalFormat = new DecimalFormat("##.##");
          Double saldoDiario = caixa.buscarCaixaPorData(data);

          if(saldoDiario == null) {
               System.out.println("Saldo inexistente para a data informada.\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          else {
               System.out.println("Saldo diário: R$" + decimalFormat.format(saldoDiario) + ".\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          esperarEnter();
          limparConsole();
     }

     /**
      * Lista todos os caixas salvos no banco de dados.
      */
     public static void listarCaixa() {
          cabecalhoSGA();
          System.out.println(caixa.listarCaixa() + "\nPressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole();
     }
}
