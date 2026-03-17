package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Accounts;
import model.AccountsJpaController;
import model.exceptions.PreexistingEntityException;
import utils.JPAUtils;

@WebServlet(name = "RegisterAccountServlet", urlPatterns = {"/RegisterAccountServlet"})
public class RegisterAccountServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String HOME_PAGE = "index.jsp";
    private final String REGISTER_PAGE = "register.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //--- Set a default URL.
        String url = REGISTER_PAGE;

        //--- Get the parameters.
        //--- Get the necessary paramters.
        String account = request.getParameter("txtAccount");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String firstName = request.getParameter("txtFirstName");
        String lastName = request.getParameter("txtLastName");
        String phone = request.getParameter("txtPhoneNumber");
        Date birthday = Date.valueOf(request.getParameter("txtBirthday"));
        boolean gender = Boolean.valueOf(request.getParameter("txtGender"));
        int roleInSystem = Integer.valueOf(request.getParameter("txtRoleInSystem"));
        boolean use = Boolean.valueOf(request.getParameter("txtActive"));

        Accounts newAccount = new Accounts(account, password, lastName, firstName,
                birthday, gender, phone, use, roleInSystem);
        request.setAttribute("POST_INFO", newAccount);
        try {
            //--- Calling JPA Controller.
            if (!confirm.equals(password)) {
                request.setAttribute("CONFIRM_NOT_MATCH", "Password and confirm not match!");
                RequestDispatcher rd = request.getRequestDispatcher(REGISTER_PAGE);
                rd.forward(request, response);
                return;
            }
            AccountsJpaController controller
                    = new AccountsJpaController(JPAUtils.getEntityManagerFactory());
            controller.create(newAccount);
        } catch (PreexistingEntityException ex) {
            String message = ex.getMessage();
            log("AddNewAccountServlet _ " + message);
            if (message.contains("already exists")) {
                request.setAttribute("DUPLICATE_ACCOUNT", "Account Duplicated!");
                request.setAttribute("POST_INFO", newAccount);
                RequestDispatcher rd = request.getRequestDispatcher(REGISTER_PAGE);
                rd.forward(request, response);
                return;
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("AddNewAccountServlet _ " + message);
        }
        HttpSession session = request.getSession();
        session.setAttribute("USER_INFORMATION", newAccount);
        //response.sendRedirect(HOME_PAGE);
        request.setAttribute("Success", "Register Successfully");
        RequestDispatcher rd = request.getRequestDispatcher(LOGIN_PAGE);
        rd.forward(request, response);
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
