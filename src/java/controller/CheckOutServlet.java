package controller;

import controller.exceptions.PreexistingEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.OrderDetails;
import model.OrderDetailsJpaController;
import model.OrderDetailsPK;
import model.Orders;
import model.OrdersJpaController;
import model.ProductsJpaController;
import utils.JPAUtils;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String VIEW_PRODUCTS_PAGE = "DispatchServlet?btnAction=ListAllProducts";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //--- Set a default URL.
        String url = VIEW_PRODUCTS_PAGE;

        //--- Get the parameters.
        String custName = request.getParameter("txtCustomerName");
        String custAddress = request.getParameter("txtCustomerAddress");
        String custPhone = request.getParameter("txtCustomerPhone");

        try {
            //--- Calling the JPA Controllers.
            OrdersJpaController orderController
                    = new OrdersJpaController(JPAUtils.getEntityManagerFactory());
            OrderDetailsJpaController orderDetailsJpaController
                    = new OrderDetailsJpaController(JPAUtils.getEntityManagerFactory());
            ProductsJpaController productsJpaController 
                    = new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            
            Orders order;
            HttpSession session = request.getSession();
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    Date date = new Date();
                    order = new Orders(custName, custPhone, custAddress,
                            date, cart.getTotal(), "PENDING");
                    orderController.create(order); //--- Persist and getID.
                    
                    for (CartItem cartItem : cart.getItems()) {
                        OrderDetails orderDetails = new OrderDetails();
                        
                        //--- Set attribute for @OneToMany
                        OrderDetailsPK orderDetailsPK = new OrderDetailsPK();
                        orderDetailsPK.setOrderId(order.getOrderId());
                        orderDetailsPK.setProductId(cartItem.getProduct().getProductId());

                        
                        //--- Set attribute for @OneToMany
                        orderDetails.setOrderDetailsPK(orderDetailsPK);
                        orderDetails.setPrice(cartItem.getProduct().getPrice() * 1.0);
                        orderDetails.setQuanty(cartItem.getQuantity());
                        orderDetails.setDiscount(cartItem.getProduct().getDiscount() * 1.0);
                        orderDetails.setOrders(order);
                        orderDetails.setProducts(cartItem.getProduct());
                        orderDetailsJpaController.create(orderDetails);
                    }
                }
                session.removeAttribute("CART");
            }
        } catch (PreexistingEntityException ex) {
            String message = ex.getMessage();
            log("CheckOutServlet _ PreexistingEntityException " + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("CheckOutServlet _ Exception " + message);
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
