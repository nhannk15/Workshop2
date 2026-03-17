package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Accounts;
import model.AccountsJpaController;
import utils.JPAUtils;

@WebServlet(name = "AutoLoginServlet", urlPatterns = {"/AutoLoginServlet"})
public class AutoLoginServlet extends HttpServlet {

    private final String HOME_PAGE = "index.jsp";
    private final String LOGIN_PAGE = "login.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = HOME_PAGE;
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                String account = null;
                String password = null;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("AUTO_LOGIN")) {
                        String[] parts = cookie.getValue().split(":");
                        account = parts[0];
                        password = parts[1];
                        break;
                    }
                }
                //--- Caling JPA COntroller
                AccountsJpaController dao = 
                        new AccountsJpaController(JPAUtils.getEntityManagerFactory());
                Accounts dto = dao.findAccounts(account);
                if (dto != null && dto.getPass().equals(password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER_INFORMATION", dto);                    
                } else {
                    url = LOGIN_PAGE;
                }
            } else {
                System.out.println("Cookie didn't existed");
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("AtoLoginServlet _ " + message);
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
