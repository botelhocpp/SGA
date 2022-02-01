package br.com.sga.empresa;

public class Usuario {
     /**
      *  No máximo 30 caractéres, sem acento.
      */
     private String login; 

     /**
      *  No máximo 10 caractéres, sem acento. 
      */
     private String senha;
     private boolean administrador;

     public Usuario(String login, String senha) {
          this.setLogin(login);
          this.setSenha(senha);
     }

     public Usuario(String login, String senha, boolean administrador) {
          this(login, senha);
          this.administrador = administrador;
     }

     @Override
     public String toString() {
          return "Usuário de login " + login + ((this.administrador) ? "(Administrador)." : ".") ;
     }
     
     @Override
     public int hashCode() {
          return this.login.hashCode() + this.senha.hashCode();
     }

     @Override
     public boolean equals(Object obj) {
          if (this == obj)
               return true;
          if (obj == null)
               return false;
          if (getClass() != obj.getClass())
               return false;
          Usuario other = (Usuario) obj;
          if (administrador != other.administrador)
               return false;
          if (login == null) {
               if (other.login != null)
                    return false;
          } else if (!login.equals(other.login))
               return false;
          if (senha == null) {
               if (other.senha != null)
                    return false;
          } else if (!senha.equals(other.senha))
               return false;
          return true;
     }
     
     // ------------------------------------------------------------------------
     // Getters
     // ------------------------------------------------------------------------

     public String getLogin() {
          return this.login;
     }

     public String getSenha() {
          return this.senha;
     }

     public boolean isAdministrador() {
          return this.administrador;
     }

     // ------------------------------------------------------------------------
     // Setters
     // ------------------------------------------------------------------------

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
}