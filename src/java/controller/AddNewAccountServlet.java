package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Accounts;
import model.AccountsJpaController;
import model.exceptions.PreexistingEntityException;
import utils.JPAUtils;

@WebServlet(name = "AddNewAccountServlet", urlPatterns = {"/AddNewAccountServlet"})
public class AddNewAccountServlet extends HttpServlet {

    private final String ACCOUNTS_PAGE = "listAllAccounts.jsp";
    private final String ADD_NEW_ACCOUNT_PAGE = "addNewAccount.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //--- Set a default URL.
        String url = ACCOUNTS_PAGE;

        //--- Get the parameters;
        String account = request.getParameter("txtAccount");
        String password = request.getParameter("txtPassword");
        String firstName = request.getParameter("txtFirstName");
        String lastName = request.getParameter("txtLastName");
        String phone = request.getParameter("txtPhoneNumber");
        Date birthday = Date.valueOf(request.getParameter("txtBirthday"));
        boolean gender = Boolean.valueOf(request.getParameter("txtGender"));
        int roleInSystem = Integer.valueOf(request.getParameter("txtRoleInSystem"));
        boolean use = Boolean.valueOf(request.getParameter("txtActive"));

        Accounts newAccount = new Accounts(account, password, lastName, firstName,
                birthday, gender, phone, use, roleInSystem);

        try {
            //--- Calling JPA Controller.
            AccountsJpaController controller
                    = new AccountsJpaController(JPAUtils.getEntityManagerFactory());
            controller.create(newAccount);
        } catch (PreexistingEntityException ex) {
            String message = ex.getMessage();
            log("AddNewAccountServlet _ " + message);
            if(message.contains("already exists")){
                request.setAttribute("DUPLICATE_ACCOUNT", "Account Duplicated");
                request.setAttribute("POST_INFO", newAccount);
                RequestDispatcher rd = request.getRequestDispatcher(ADD_NEW_ACCOUNT_PAGE);
                rd.forward(request, response);
                return;
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("AddNewAccountServlet _ " + message);
        }
        response.sendRedirect("DispatchServlet"
                + "?btnAction=SearchAccount"
                + "&txtSearchValue=" 
                + URLEncoder.encode(lastName + " " + firstName, "UTF-8"));
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
