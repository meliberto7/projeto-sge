
package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.bean.Professores;
import model.dao.AreasDAO;
import model.dao.ProfessoresDAO;


@WebServlet(name = "ControllerLogin", urlPatterns = {"/ControllerLogin", "/login", "/cadastro", "/logar", "/cadastrar", "/inicio"})
@MultipartConfig
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
                
                Cookie[] cookies = request.getCookies();
                
                for(Cookie c: cookies){
                  
                    request.setAttribute(c.getName(), c.getValue());
                    
//                    switch(c.getName()) {
//                        
//                        case "id_professor":
//                            
//                            request.setAttribute("id_professor", c.getValue());
//                            
//                            break;
//                            
//                        case "nome":
//                            
//                            request.setAttribute("nome", c.getValue());
//                            
//                            break;
//                        
//                    }
                    
                }
                
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
                
                Part filePart = request.getPart("inputImagem");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                
                if (fileName != null && !fileName.isEmpty()) {
                    
                    String basePath = getServletContext().getRealPath("/") + "assets";
                    
                    File uploads = new File(basePath);
                    
                    if (!uploads.exists()) {
                        
                        uploads.mkdir();
                        
                    }
                    
                    File file = new File(uploads, fileName);
                    
                    try(InputStream input = filePart.getInputStream()){
                        
                        Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                    prof.setImagem("assets/" + fileName);
                    
                }
                
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
                
                Professores profe = new Professores();
                
                profe = profDAO.logar(cpf, senha);
                
                if (profe.getId_professor() == 0) {
                    
                    pag = "cadastro";
                    
                } else {
                    
                    Cookie cookie = new Cookie("id_professor", Integer.toString(profe.getId_professor()));
                    Cookie cookie2 = new Cookie("nome", profe.getNome());
                    response.addCookie(cookie);
                    response.addCookie(cookie2);
                    pag = "inicio";
                    
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
