/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nhann
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

    //--- Accounts Controller.   
    private final String HOME_PAGE = "index.jsp";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String SEARCH_ACCOUNT_CONTROLLER = "SearchAccountServlet";
    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteAccountServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String LIST_ALL_ACCOUNTS_CONTROLLER = "ListAllAccountsServlet";
    private final String UPDATE_ACCOUNT_CONTROLLER = "UpdateAccountServlet";
    private final String LOAD_UPDATE_ACCOUNT_CONTROLLER = "LoadUpdateAccountServlet";
    private final String AUTO_LOGIN_CONTROLLER = "AutoLoginServlet";
    private final String ADD_NEW_ACCOUNT_CONTROLLER = "AddNewAccountServlet";
    private final String REGISTER_ACCOUNT_CONTROLLER = "RegisterAccountServlet";
    private final String CHANGE_STATUS_CONTROLLER = "ChangeStatusServlet";
    
    //--- Category Controller.
    private final String LIST_ALL_CATEGORIES = "ListAllCategoriesServlet";
    private final String DELETE_CATEGORY_CONTROLLER = "DeleteCategoryServlet";
    private final String SEARCH_CATEGORY_CONTROLLER = "SearchCategoryServlet";
    private final String ADD_NEW_CATEGORY_CONTROLLER = "AddNewCategoryServlet";
    private final String LOAD_UPDATE_CATEGORY_CONTROLLER = "LoadUpdateCategoryServlet";
    private final String UPDATE_CATEGORY_CONTROLLER = "UpdateCategoryServlet";
    
    //--- Product Controller.
    private final String LIST_ALL_PRODUCTS_CONTROLLER = "ListAllProductsServlet";  
    private final String FILTER_PRODUCT_BY_CATEGORY_CONTROLLER = "FilterProductByCategoryServlet";
    private final String SEARCH_PRODUCT_USING_NAME = "SearchProductUsingNameServlet";
    private final String DELETE_PRODUCT_CONTROLLER = "DeleteProductServlet";
    private final String LOAD_UPDATE_PRODUCT_CONTROLLER = "LoadUpdateProductServlet";
    private final String UPDATE_PRODUCT_CONTROLLER = "UpdateProductServlet";
    private final String ADD_NEW_PRODUCT_CONTROLLER = "AddNewProductServlet";
    private final String LOAD_BACKUP_PRODUCT_CONTROLLER = "LoadBackupProductServlet";
    private final String VIEW_PRODUCT_DETAIL_CONTROLLER = "ViewProductDetailServlet";
    private final String SORT_PRODUCTS_BY_PRICE_CONTROLLER = "SortByPriceServlet";
    
    //--- Cart Controller.
    private final String ADD_TO_CART_CONTROLLER = "AddToCartServlet"; 
    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
    private final String REMOVE_CART_ITEM_CONTROLLER = "RemoveCartItemServlet";
    private final String UPDATE_CART_ITEM_CONTROLLER = "UpdateCartItemServlet";
    private final String CHECK_OUT_CONTROLLER = "CheckOutServlet";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set a defaul value for URL.
        String url = HOME_PAGE;
        
        //--- Get the necessary parameters.
        String btnAction = request.getParameter("btnAction");
        
        try {
            //--- Mapping the function Servlet.
            if (btnAction == null) {
                //--- Using Cookies to check AutoLogin.
                url = AUTO_LOGIN_CONTROLLER;
            } else if (btnAction.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (btnAction.equals("SearchAccount")) {
                url = SEARCH_ACCOUNT_CONTROLLER;
            } else if (btnAction.equals("DeleteAccount")) {
                url = DELETE_ACCOUNT_CONTROLLER;
            } else if (btnAction.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (btnAction.equals("ListAllAccounts")) {
                url = LIST_ALL_ACCOUNTS_CONTROLLER;
            } else if (btnAction.equals("LoadUpdateAccount")) {
                url = LOAD_UPDATE_ACCOUNT_CONTROLLER;
            } else if (btnAction.equals("UpdateAccount")) {
                url = UPDATE_ACCOUNT_CONTROLLER;
            } else if (btnAction.equals("ListAllProducts")) { //---
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("AddNewAccount")) {
                url = ADD_NEW_ACCOUNT_CONTROLLER;
            } else if (btnAction.equals("ListAllCategories")) {
                url = LIST_ALL_CATEGORIES;
            } else if (btnAction.equals("DeleteCategory")) {
                url = DELETE_CATEGORY_CONTROLLER;
            } else if (btnAction.equals("SearchCategory")) {
                url = SEARCH_CATEGORY_CONTROLLER;
            } else if (btnAction.equals("AddNewCategory")) {
                url = ADD_NEW_CATEGORY_CONTROLLER;
            } else if (btnAction.equals("FilterProductByCategory")) { //---
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("SearchProductUsingName")) { //---
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("LoadUpdateCategory")) {
                url = LOAD_UPDATE_CATEGORY_CONTROLLER;
            } else if (btnAction.equals("UpdateCategory")) {
                url = UPDATE_CATEGORY_CONTROLLER;
            } else if (btnAction.equals("DeleteProduct")) {
                url = DELETE_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("RegisterAccount")) {
                url = REGISTER_ACCOUNT_CONTROLLER;
            } else if (btnAction.equals("Deactivate")) {
                url = CHANGE_STATUS_CONTROLLER;
            } else if (btnAction.equals("Activate")) {
                url = CHANGE_STATUS_CONTROLLER;
            } else if (btnAction.equals("LoadUpdateProduct")) {
                url = LOAD_UPDATE_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("UpdateProduct")) {
                url = UPDATE_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("AddNewProduct")) { //---
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("LoadBackupProduct")) {
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("ViewProductDetail")) {
                url = VIEW_PRODUCT_DETAIL_CONTROLLER;
            } else if (btnAction.equals("AddToCart")) {
                url = ADD_TO_CART_CONTROLLER;
            } else if (btnAction.equals("ViewCart")) {
                url = VIEW_CART_CONTROLLER;
            } else if (btnAction.equals("RemoveCartItem")) {
                url = REMOVE_CART_ITEM_CONTROLLER;
            } else if (btnAction.equals("UpdateCartItem")) {
                url = UPDATE_CART_ITEM_CONTROLLER;
            } else if (btnAction.equals("CheckOut")) {
                url = CHECK_OUT_CONTROLLER;
            } else if (btnAction.equals("FilterPrice")) {
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("SortByPrice")) {
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("FilterDiscount")) {
                url = LOAD_BACKUP_PRODUCT_CONTROLLER;
            } else if (btnAction.equals("AddNewProductThisTime")) {
                url = ADD_NEW_PRODUCT_CONTROLLER;
            }
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
