package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String HOME_PAGE = "index.jsp";
    private final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //--- Set a default URL.
        String url = LOGIN_PAGE;

        //--- Get the parameters.
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String remember = request.getParameter("txtRemember");

        try {
            //--- Calling JPA Controller.
            AccountsJpaController dao
                    = new AccountsJpaController(JPAUtils.getEntityManagerFactory());
            Accounts account = dao.findAccounts(username);
            if (account != null) {
                //--- Check if account is loged in at another platform.
                ServletContext context = request.getServletContext();
                Set<String> activeAccount = (Set<String>) context.getAttribute("ACTIVE_ACCOUNTS");
                if (activeAccount.contains(username)) {
                    request.setAttribute("NOTIFICATION", "This account is now used at another platform.");
                    return;
                }

                if (account.getPass().equals(password)) {
                    activeAccount.add(username);
                    HttpSession session = request.getSession();
                    session.setAttribute("USER_INFORMATION", account);
                    url = HOME_PAGE;
                    if (remember != null) {
                        Cookie cookie
                                = new Cookie("AUTO_LOGIN", username + ":" + password);
                        cookie.setMaxAge(60 * 60 * 24 * 30);
                        response.addCookie(cookie);
                    }
                } else {
                    request.setAttribute("NOTIFICATION", "Wrong username or password");
                }
            } else {
                request.setAttribute("NOTIFICATION", "Wrong username or password");
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("LoginServlet _ Exception " + message);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
