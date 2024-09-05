
package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Professores;
import model.dao.AreasDAO;
import model.dao.ProfessoresDAO;


@WebServlet(name = "ControllerLogin", urlPatterns = {"/ControllerLogin", "/login", "/cadastro", "/logar", "/cadastrar", "/inicio"})
public class ControllerLogin extends HttpServlet {

    private ProfessoresDAO profDAO = new ProfessoresDAO();
    private AreasDAO areaDAO = new AreasDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String paginaAtual = request.getServletPath();
        
        switch(paginaAtual) {
            
            case "/login":
                
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
                
                break;
                
            case "/cadastro":
                
                request.setAttribute("areas", areaDAO.listar());
                
                request.getRequestDispatcher("WEB-INF/jsp/cadastro.jsp").forward(request, response);
                
                break;
                
            case "/inicio":
                
                request.getRequestDispatcher("WEB-INF/jsp/inicio.jsp").forward(request, response);
                
                break;
            
        }
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String paginaAtual = request.getServletPath();
        
        switch(paginaAtual) {
            
            case "/cadastrar":
                
                Professores prof = new Professores();
                
                prof.setNome(request.getParameter("inputNome"));
                prof.setSenha(request.getParameter("inputSenha"));
                prof.setCpf(request.getParameter("inputCpf"));
                prof.setId_area(Integer.parseInt(request.getParameter("selectArea")));
                prof.setAdmissao(Date.valueOf(request.getParameter("inputDate")));
                
                if (prof.getNome().isEmpty() || prof.getSenha().isEmpty() || prof.getCpf().isEmpty() || prof.getAdmissao() == null || prof.getId_area() == 0) {
                    
                    response.sendRedirect("./cadastro");
                    
                } else {
                    
                    profDAO.cadastrar(prof);
                
                    request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
                    
                }

                break;
                
            case "/logar":
                
                String cpf = request.getParameter("inputCpf");
                String senha = request.getParameter("inputSenha");
                String pag = null;
                
                if (profDAO.logar(cpf, senha)) {
                    
                    pag = "inicio";
                    
                } else {
                    
                    pag = "login";
                    
                } 
                
                response.sendRedirect("./" + pag);
                
                break;
                
            
        }
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
