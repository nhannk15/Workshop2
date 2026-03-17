package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Accounts;
import model.AccountsJpaController;
import utils.JPAUtils;

@WebServlet(name = "ChangeStatusServlet", urlPatterns = {"/ChangeStatusServlet"})
public class ChangeStatusServlet extends HttpServlet {

    private final String ACCOUNTS_PAGE = "listAllAccounts.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //--- Set a default URK.
        String url = ACCOUNTS_PAGE;

        //--- Get the parameters.
        String account = request.getParameter("txtAccount");
        String lastSearchValue = request.getParameter("txtLastSearchValue");

        //--- For adding and removing accounts from Website.
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        Set<String> activeAccount = (Set<String>) context.getAttribute("ACTIVE_ACCOUNTS");

        try {
            //--- Calling the JPA Controller.
            AccountsJpaController controller
                    = new AccountsJpaController(JPAUtils.getEntityManagerFactory());
            Accounts updateAccount = controller.findAccounts(account);
            if (updateAccount.getIsUse() == true) {
                updateAccount.setIsUse(false);

                String username = updateAccount.getAccount();
                activeAccount.remove(username);
            } else {
                updateAccount.setIsUse(true);
            }
            controller.edit(updateAccount);
            
            if (!lastSearchValue.trim().isEmpty()) {
                url = "DispatchServlet"
                        + "?btnAction=SearchAccount"
                        + "txtSearchValue="
                        + URLEncoder.encode(lastSearchValue, "UTF-8");
            } else {
                url = "DispatchServlet"
                        + "?btnAction=ListAllAccounts";
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("ChangeStatusServlet _ Exception " + message);
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
