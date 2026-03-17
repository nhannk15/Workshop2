package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Accounts;
import model.Products;
import model.ProductsJpaController;
import model.SetOfProducts;
import model.ViewedProducts;
import utils.JPAUtils;

@WebServlet(name = "ViewProductDetailServlet", urlPatterns = {"/ViewProductDetailServlet"})
public class ViewProductDetailServlet extends HttpServlet {

    private final String VIEW_PRODUCT_DETAIL_PAGE = "productDetail.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a default URL.
        String url = VIEW_PRODUCT_DETAIL_PAGE;
        
        //--- Get the parameters.
        String productId = request.getParameter("txtProductId");
        
        try {
            ProductsJpaController controller = 
                    new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            Products product = controller.findProducts(productId);
            request.setAttribute("PRODUCT_INFO", product);
            HttpSession session = request.getSession();
            Set<Products> viewedList = 
                    (Set<Products>) session.getAttribute("VIEWED_LIST");
            if(viewedList == null) {
                viewedList = new HashSet<>();
            }
            viewedList.add(product);
            session.setAttribute("VIEWED_LIST", viewedList);
            
            Accounts accountsInfo = 
                    (Accounts) session.getAttribute("USER_INFORMATION");
            String account = accountsInfo.getAccount();
            
            ServletContext context = request.getServletContext();
            ViewedProducts viewedProducts = 
                    (ViewedProducts) context.getAttribute("VIEWED_PRODUCTS");
            viewedProducts.addNewProduct(account, product);
            context.setAttribute("VIEWED_PRODUCTS", viewedProducts);
            
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
