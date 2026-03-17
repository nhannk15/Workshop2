package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Categories;
import model.CategoriesJpaController;
import model.exceptions.PreexistingEntityException;
import utils.JPAUtils;

@WebServlet(name = "AddNewCategoryServlet", urlPatterns = {"/AddNewCategoryServlet"})
public class AddNewCategoryServlet extends HttpServlet {
    
    private final String CATEGORIES_PAGE = "listAllCategories.jsp";
    private final String ADD_CATEGORY_PAGE = "addNewCategory.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default value for URL.
        String url = ADD_CATEGORY_PAGE;
        
        //--- Get the parameters.
        String categoryName = request.getParameter("txtCategoryName");
        String memo = request.getParameter("txtMemo");
        Categories newCategories = new Categories(categoryName, memo);
        
        try {
            //--- Caling the JPA Controller.
            CategoriesJpaController controller =
                    new CategoriesJpaController(JPAUtils.getEntityManagerFactory());
            controller.create(newCategories);
        } catch (PreexistingEntityException ex) {
            String message = ex.getMessage();
            log("AddNewCategoryServlet _ PreexistingEntityException " + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("AddNewCategoryServlet _ Exception " + message);
        }
        response.sendRedirect("DispatchServlet"
                + "?btnAction=SearchCategory"
                + "&txtSearchValue="
                + URLEncoder.encode(newCategories.getCategoryName(), "UTF-8"));
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
