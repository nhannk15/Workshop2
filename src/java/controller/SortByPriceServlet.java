package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Products;
import utils.JPAUtils;

@WebServlet(name = "SortByPriceServlet", urlPatterns = {"/SortByPriceServlet"})
public class SortByPriceServlet extends HttpServlet {

    private final String LIST_PRODUCTS_PAGE = "listAllProducts.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //--- Set the default URL.
        String url = LIST_PRODUCTS_PAGE;
        
        //--- Get the parameters.
        String type = request.getParameter("type");
        
        try {           
            List<Products> result = (List<Products>) request.getAttribute("PRODUCT_LIST");
            if (type.equals("asc")) {
                Collections.sort(result, new Comparator<Products>(){
                    @Override
                    public int compare(Products p1, Products p2) {
                        return Double.compare(p1.getPrice(), p2.getPrice());
                    }
                });
            } else if (type.equals("desc")) {
                Collections.sort(result, new Comparator<Products>(){
                    @Override
                    public int compare(Products p1, Products p2) {
                        return Double.compare(p2.getPrice(), p1.getPrice());
                    }
                });
            }
            request.setAttribute("PRODUCT_LIST", result);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log("SortByPriceServlet _ Exception " + message);
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
