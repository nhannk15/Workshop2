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
import model.Categories;
import model.CategoriesJpaController;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import utils.JPAUtils;

@WebServlet(name = "UpdateCategoryServlet", urlPatterns = {"/UpdateCategoryServlet"})
public class UpdateCategoryServlet extends HttpServlet {

    private final String CATEGORIES_PAGE = "listAllCategories.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default URL.
        String url = CATEGORIES_PAGE;
        
        //--- Get the parameters.
        int typeId = Integer.parseInt(request.getParameter("txtTypeId"));
        String categoryName = request.getParameter("txtCategoryName");
        String memo = request.getParameter("txtMemo");
        
        try {
            //--- Calling the JPA Controller.
            CategoriesJpaController controller = 
                    new CategoriesJpaController(JPAUtils.getEntityManagerFactory());
            Categories updateCategory = controller.findCategories(typeId);
            updateCategory.setCategoryName(categoryName);
            updateCategory.setMemo(memo);
            controller.edit(updateCategory);
            url = "DispatchServlet"
                    + "?btnAction=SearchCategory"
                    + "&txtSearchValue=" 
                    + URLEncoder.encode(updateCategory.getCategoryName(), "UTF-8");
        } catch (IllegalOrphanException ex) {
            String message = ex.getMessage();
            log("UpdateCategoryServlet _ IllegalOrphanException " + message);
        } catch (NonexistentEntityException ex) {
            String message = ex.getMessage();
            log("UpdateCategoryServlet _ NonexistentEntityException " + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("UpdateCategoryServlet _ Exception " + message);
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
