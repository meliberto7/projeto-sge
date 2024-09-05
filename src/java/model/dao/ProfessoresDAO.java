
package model.dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import model.bean.Professores;


public class ProfessoresDAO {
    
    public boolean cadastrar(Professores professor) {
        
        boolean very = false;
        
        try{
            
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO professores(nome,matricula,admissao,senha,cpf,id_area) VALUES(?,?,?,?,?,?)");
            
            stmt.setString(1, professor.getNome());
            
            Random random = new Random();
            
            int num = random.nextInt(1000);
            String cod = String.valueOf(num);
            
            if (pesquisar(cod)) {
                
                boolean very2 = false;
                
                while (!very2) {
                    
                    num = random.nextInt(1000);
                    cod = String.valueOf(num);
                    System.out.println("Teste");
                    
                    if (!pesquisar(cod)) {
                        
                        very2 = true;
                        
                    }
                    
                }
                
            }
            
            stmt.setString(2, cod);
            
            stmt.setDate(3, professor.getAdmissao());
            stmt.setString(4, professor.getSenha());
            stmt.setString(5, professor.getCpf());
            stmt.setInt(6, professor.getId_area());
            
            if (stmt.executeUpdate() > 0) {
                
                very = true;
                
            }
            
            stmt.close();
            conexao.close();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return very;
    }
    
    public boolean pesquisar(String matricula) {
        
        boolean very = false;
        
        try{
            
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement("SELECT matricula FROM professores WHERE matricula = ?");
            
            stmt.setString(1, matricula);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                
                very = true;
                
            }
            
            rs.close();
            stmt.close();
            conexao.close();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return very;
    }
    
    public boolean logar(String cpf, String senha) {
        
        boolean very = false;
        
        try{
            
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM professores WHERE cpf = ? AND senha = ?");
            
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                
                very = true;
                
            }
            
            rs.close();
            stmt.close();
            conexao.close();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return very;
    }
    
}
