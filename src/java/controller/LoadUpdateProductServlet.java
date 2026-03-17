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
import javax.servlet.http.HttpSession;
import model.Products;
import model.ProductsJpaController;
import utils.JPAUtils;

@WebServlet(name = "LoadUpdateProductServlet", urlPatterns = {"/LoadUpdateProductServlet"})
public class LoadUpdateProductServlet extends HttpServlet {
    
    private final String VIEW_PRODUCT_DETAIL_PAGE = "productDetail.jsp";
    private final String LOAD_BACK_UP_CONTROLLER = "LoadBackupProductServlet";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default URL.
        String url = LOAD_BACK_UP_CONTROLLER;
        
        //--- Get the parameters.
        String productId = request.getParameter("txtProductId");
        String btnAction = request.getParameter("btnAction");
        try {
            ProductsJpaController controller = 
                    new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            Products product = controller.findProducts(productId);
            request.setAttribute("PRODUCT_INFO", product);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("LoadUpdateProductServlet _ Exception " + message);
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
