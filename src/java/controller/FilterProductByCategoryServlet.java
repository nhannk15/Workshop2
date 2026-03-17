package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Categories;
import model.CategoriesJpaController;
import model.Products;
import model.ProductsJpaController;
import utils.JPAUtils;

@WebServlet(name = "FilterProductByCategoryServlet", urlPatterns = {"/FilterProductByCategoryServlet"})
public class FilterProductByCategoryServlet extends HttpServlet {

    private final String FILTER_PRODUCTS_BY_CATEGORY_PAGE = "listAllProducts.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default URL.
        String url = FILTER_PRODUCTS_BY_CATEGORY_PAGE;
        
        //--- Get the paramters.
        int typeId = Integer.parseInt(request.getParameter("txtTypeId"));
        
        try {
            //--- Calling the JPA Controller.
            ProductsJpaController controller = 
                    new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            List<Products> allProducts = controller.findProductsEntities();
            
            CategoriesJpaController categoryController = 
                    new CategoriesJpaController(JPAUtils.getEntityManagerFactory());
            List<Categories> categoryList = categoryController.findCategoriesEntities();
            
            List<Products> result = new ArrayList<>();
            for (Products product: allProducts) {
                if(product.getTypeId().getTypeId() == typeId) { //--- Jesus.
                    result.add(product);
                }
            }
            request.setAttribute("PRODUCT_LIST", result);
            request.setAttribute("CATEGORY_LIST", categoryList);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("FilterProductByCategory _ Exception " + message);
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
