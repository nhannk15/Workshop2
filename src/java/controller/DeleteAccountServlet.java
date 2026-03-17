package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AccountsJpaController;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import utils.JPAUtils;

@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccountServlet"})
public class DeleteAccountServlet extends HttpServlet {

    private final String ACCOUNTS_PAGE = "listAllAccounts.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default value for URL.
        String url = ACCOUNTS_PAGE;
        
        //--- Get the parameters.
        String account = request.getParameter("txtAccount");
        String lastSearchValue = request.getParameter("lastSearchValue");
        
        try {
            //--- Calling the JPA Controller.
            AccountsJpaController controller =
                    new AccountsJpaController(JPAUtils.getEntityManagerFactory());
            controller.destroy(account);
            url = "DispatchServlet" + 
                    "?btnAction=SearchAccount" + 
                    "&txtSearchValue=" + URLEncoder.encode(lastSearchValue, "UTF-8");
        } catch (NonexistentEntityException ex) {
            String message = ex.getMessage();
            log("DeleteAccountServlet _ " + message);
        } catch (IllegalOrphanException ex) {
            String message = ex.getMessage();
            log("DeleteAccountServlet _ " + message);
            request.setAttribute("NOTIFICATION", "There's at least a product belongs to that account!");
            RequestDispatcher rd = request.getRequestDispatcher("DispatchServlet?btnAction=ListAllCategories");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("DeleteAccountServlet _ " + message);
        }
        response.sendRedirect(url);
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
