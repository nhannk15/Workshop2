/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
import utils.JPAUtils;

@WebServlet(name = "LoadBackupProductServlet", urlPatterns = {"/LoadBackupProductServlet"})
public class LoadBackupProductServlet extends HttpServlet {
   
    private final String PRODUCTS_PAGE = "listAllProducts.jsp";
    private final String FILTER_PRODUCT_BY_CATEGORY_CONTROLLER = "FilterProductByCategoryServlet";
    private final String SEARCH_PRODUCT_USING_NAME_CONTROLLER = "SearchProductUsingNameServlet";
    private final String ADD_NEW_PRODUCT_PAGE = "addNewProduct.jsp";
    private final String LOAD_UPDATE_PRODUCT_PAGE = "updateProduct.jsp";
    private final String FILTER_PRICE_CONTROLLER = "FilterPriceServlet";
    private final String SORT_BY_PRICE_CONTROLLER = "SortByPriceServlet";
    private final String FILTER_DISCOUNT_CONTROLLER = "FilterDiscountProductsServlet";
    private final String ADD_NEW_PRODUCT_CONTROLLER = "AddNewProductServlet";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String url = PRODUCTS_PAGE;
        
        String btnAction = request.getParameter("btnAction");
        
        try {
            AccountsJpaController accountController = 
                    new AccountsJpaController(JPAUtils.getEntityManagerFactory());
            CategoriesJpaController categoryController = 
                    new CategoriesJpaController(JPAUtils.getEntityManagerFactory());
            ProductsJpaController productJpaController = 
                    new ProductsJpaController(JPAUtils.getEntityManagerFactory());
            
            List<Accounts> accountList = accountController.findAccountsEntities();
            List<Categories> categoryList = categoryController.findCategoriesEntities();
            List<Products> productList = productJpaController.findProductsEntities();
            
            request.setAttribute("ACCOUNT_LIST", accountList);
            request.setAttribute("CATEGORY_LIST", categoryList);
            request.setAttribute("PRODUCT_LIST", productList);
            
            if(btnAction.equals("FilterProductByCategory")){
                url = FILTER_PRODUCT_BY_CATEGORY_CONTROLLER;
            } else if (btnAction.equals("SearchProductUsingName")) {
                url = SEARCH_PRODUCT_USING_NAME_CONTROLLER;
            } else if (btnAction.equals("ListAllProducts")) {
                url = PRODUCTS_PAGE;
            } else if (btnAction.equals("AddNewProduct")) {
                url = ADD_NEW_PRODUCT_PAGE;
            } else if (btnAction.equals("LoadUpdateProduct")) {
                url = LOAD_UPDATE_PRODUCT_PAGE;
            } else if (btnAction.equals("FilterPrice")) {
                url = FILTER_PRICE_CONTROLLER;
            } else if (btnAction.equals("SortByPrice")) {
                url = SORT_BY_PRICE_CONTROLLER;
            } else if (btnAction.equals("FilterDiscount")) {
                url = FILTER_DISCOUNT_CONTROLLER;
            } 
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("LoadBackupProductServlet _ Exception " + message);
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
