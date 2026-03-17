package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Accounts;
import model.AccountsJpaController;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import utils.JPAUtils;

@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

    private final String UPDATE_ACCOUNT_PAGE = "updateAccount.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //--- Set a default URL.
        String url = UPDATE_ACCOUNT_PAGE;

        //--- Get the parameters.
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        Date birthday = Date.valueOf(request.getParameter("birthday"));
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String phone = request.getParameter("phone");
        boolean use = (request.getParameter("use") != null);
        int roleInSystem = Integer.parseInt(request.getParameter("roleInSystem"));

        try {
            //--- Calling the JPA Controller.
            AccountsJpaController controller
                    = new AccountsJpaController(JPAUtils.getEntityManagerFactory());
            Accounts updateAccount = controller.findAccounts(account);

            updateAccount.setPass(password);
            updateAccount.setLastName(lastName);
            updateAccount.setFirstName(firstName);
            updateAccount.setBirthday(birthday);
            updateAccount.setGender(gender);
            updateAccount.setPhone(phone);
            updateAccount.setIsUse(use);
            updateAccount.setRoleInSystem(roleInSystem);

            controller.edit(updateAccount);
            
            //--- If he edited himself.
            HttpSession session = request.getSession();
            Accounts currentAccount = (Accounts) session.getAttribute("USER_INFORMATION");
            if(currentAccount.getAccount().equals(updateAccount.getAccount())){
                session.setAttribute("USER_INFORMATION", updateAccount); 
            }            
        } catch (IllegalOrphanException ex) {
            String message = ex.getMessage();
            log("UpdateAccountServlet _ IllegalOrphanException " + message);
        } catch (NonexistentEntityException ex) {
            String message = ex.getMessage();
            log("UpdateAccountServlet _ NonexistentEntityException " + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("UpdateAccountServlet _ Exception " + message);
        } finally {
            response.sendRedirect("DispatchServlet"
                    + "?btnAction=SearchAccount"
                    + "&txtSearchValue="
                    + URLEncoder.encode(lastName + " " + firstName, "UTF-8"));
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
