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
import model.ProductsJpaController;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import utils.JPAUtils;

@WebServlet(name = "DeleteProductServlet", urlPatterns = {"/DeleteProductServlet"})
public class DeleteProductServlet extends HttpServlet {
    
    private final String PRODUCTS_PAGE = "listAllProducts.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default value for URL.
        String url = PRODUCTS_PAGE;
        
        //--- Get the parameters.
        String productId = request.getParameter("txtProductId");
        String lastSearchValue = request.getParameter("lastSearchValue");
        
        try {
            //--- Calling JPA Controller.
            ProductsJpaController controller = 
                    new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            controller.destroy(productId);
            url = "DispatchServlet" + 
                    "?btnAction=SearchProductUsingName" + 
                    "&txtSearchValue=" + URLEncoder.encode(lastSearchValue, "UTF-8");
        } catch (IllegalOrphanException ex) {
            String message = ex.getMessage();
            log("DeleteProductServlet _ IllegalOrphanException " + message);
            request.setAttribute("NOTIFICATION", "There's at least an OrderDetails belongs to that product!");
            RequestDispatcher rd = request.getRequestDispatcher("DispatchServlet?btnAction=ListAllCategories");
        } catch (NonexistentEntityException ex) {
            String message = ex.getMessage();
            log("DeleteProductServlet _ NonexistentEntityException " + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("DeleteProductServlet _ " + message);
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
