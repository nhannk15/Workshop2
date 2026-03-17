package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Products;
import model.ProductsJpaController;
import utils.JPAUtils;

@WebServlet(name = "UpdateCartItemServlet", urlPatterns = {"/UpdateCartItemServlet"})
public class UpdateCartItemServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "viewCart.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default URL.
        String url = VIEW_CART_PAGE;
        
        //--- Get the parameters.
        String productId = request.getParameter("txtProductId");
        int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
        if(quantity < 1){
            response.sendRedirect(VIEW_CART_PAGE);
            return;
        }
        
        try {
            //--- Calling the JPA Controller.
            ProductsJpaController controller = 
                    new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            Products product = controller.findProducts(productId);
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                cart.removeProduct(productId);
                for(int i = 0; i < quantity; i++) {
                    cart.addProduct(product);
                }            
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("UpdateCartItemServlet _ Exception " + message);
        } finally {
            response.sendRedirect(VIEW_CART_PAGE);
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
