package br.com.sga.empresa;

public class Usuario {
     private String login; 
     private String senha;

     public Usuario(String login, String senha) {
          this.setLogin(login);
          this.setSenha(senha);
     }

     public String getLogin() {
          return login;
     }

     public String getSenha() {
          return senha;
     }

     private void setLogin(String login) throws IllegalArgumentException {
          if(login == null || login.isEmpty() || login.length() < 5) {
               throw new IllegalArgumentException("Login inválido! O login do usuário deve ter no mínimo 5 caracteres!");
          }
          this.login = login;
     }

     private void setSenha(String senha) throws IllegalArgumentException {
          if(senha == null || senha.isEmpty() || senha.length() < 5) {
               throw new IllegalArgumentException("Senha inválida! A senha do usuário deve ter no mínimo 5 caracteres!");
          }
          this.senha = senha;
     }

     @Override
     public String toString() {
          return "Usuário de login " + login + ".";
     }
}