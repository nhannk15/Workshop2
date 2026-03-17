package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Accounts;
import model.AccountsJpaController;
import model.Categories;
import model.CategoriesJpaController;
import model.Products;
import model.ProductsJpaController;
import model.exceptions.PreexistingEntityException;
import utils.JPAUtils;

@WebServlet(name = "AddNewProductServlet", urlPatterns = {"/AddNewProductServlet"})
public class AddNewProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String productId = request.getParameter("txtProductId");
        String productName = request.getParameter("txtProductName");
        
        String productImage = request.getParameter("txtProductImage");
        productImage = productImage.replace("\\", "/");
        
        String brief = request.getParameter("txtBrief");
        
        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("txtPostedDate"));
        Date postedDate = (Date) Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        
        int txtTypeId = Integer.valueOf(request.getParameter("txtTypeId"));
        CategoriesJpaController categoryController = 
                new CategoriesJpaController(JPAUtils.getEntityManagerFactory());
        Categories typeId = categoryController.findCategories(txtTypeId);
        
        String txtAccount = request.getParameter("txtAccount");
        AccountsJpaController accountController = 
                new AccountsJpaController(JPAUtils.getEntityManagerFactory());
        Accounts account = accountController.findAccounts(txtAccount);
        
        String unit = request.getParameter("txtUnit");
        int price = Integer.valueOf(request.getParameter("txtPrice"));
        int discount = Integer.valueOf(request.getParameter("txtDiscount"));
        
        try {
            //--- Calling the JPA Controller.
            ProductsJpaController controller = 
                    new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            
            Products newProduct = 
                    new Products(productId, productName, productImage, brief, 
                            postedDate, unit, price, discount, account, typeId);
            
            controller.create(newProduct);
        } catch (PreexistingEntityException ex) {
            String message = ex.getMessage();
            log("AddNewProductServlet _ PreexistingEntityException " + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("AddNewProductServlet _ Exception " + message);
        } 
        response.sendRedirect("DispatchServlet"
                + "?btnAction=SearchProductUsingName"
                + "&txtSearchValue=" 
                + URLEncoder.encode(productName, "UTF-8"));
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
