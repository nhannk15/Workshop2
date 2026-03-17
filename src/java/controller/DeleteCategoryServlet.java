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
import model.CategoriesJpaController;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import utils.JPAUtils;

@WebServlet(name = "DeleteCategoryServlet", urlPatterns = {"/DeleteCategoryServlet"})
public class DeleteCategoryServlet extends HttpServlet {

    private final String CATEGORIES_PAGE = "listAllCategories.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default URL.
        String url = CATEGORIES_PAGE;
        
        //--- Get the parameters.
        String txtTypeId = request.getParameter("typeId");
        int typeId = Integer.parseInt(txtTypeId);
        String lastSearchValue = request.getParameter("lastSearchValue");
        
        try {
            //--- Calling the JPA Controller.
            CategoriesJpaController controller = 
                    new CategoriesJpaController(JPAUtils.getEntityManagerFactory());           
            controller.destroy(typeId);
            if(!lastSearchValue.trim().isEmpty()){
                url = "DispatchServlet"
                    + "?btnAction=SearchCategory"
                    + "&txtSearchValue="
                    + URLEncoder.encode(lastSearchValue, "UTF-8");
            } else {
                url = "DispatchServlet"
                    + "?btnAction=ListAllCategories";
            }
        } catch (IllegalOrphanException ex) {
            String message = ex.getMessage();
            log("DeleteCategoryServlet _ IllegalOrphanException " + message);
            request.setAttribute("NOTIFICATION", "There's at least a product belongs to that category!");
            RequestDispatcher rd = request.getRequestDispatcher("DispatchServlet?btnAction=ListAllCategories");
            rd.forward(request, response);
            return;
        } catch (NonexistentEntityException ex) {
            String message = ex.getMessage();
            log("DeleteCategoryServlet _ NonexistentEntityException " + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("DeleteCategoryServlet _ " + message);
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
