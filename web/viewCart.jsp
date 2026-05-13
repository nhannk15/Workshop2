<%-- 
    Document   : viewCart
    Created on : Mar 8, 2026, 11:03:24 PM
    Author     : nhann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="section-two">
            <%@include file="WEB-INF/menu.jspf" %>
            <div class="container">
                <h2 class="form-card-title" style="margin-top:24px">Your Shopping Cart</h2>
                <c:choose>
                    <c:when test="${empty sessionScope.CART.items}">
                        <div style="text-align:center; padding:60px 0; color:#64748b;">
                            <p style="font-size:18px; font-weight:500;">Your cart is empty</p>
                            <a href="DispatchServlet?btnAction=ListAllProducts" class="btn btn-primary" style="margin-top:12px;">Browse Products</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="custom-table">
                            <tr>
                                <th>Image</th>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                                <th>Action</th>
                            </tr>
                            <c:forEach var="item" items="${sessionScope.CART.items}">
                                <tr>
                                    <td>
                                        <img src="${pageContext.request.contextPath}${item.product.productImage}"
                                             width="80" height="80">
                                    </td>
                                    <td>
                                        ${item.product.productName}
                                    </td>
                                    <td>
                                        ${item.product.price * (100 - item.product.discount) / 100}
                                    </td>
                                    <td>
                                        <form action="DispatchServlet" method="POST">
                                            <input type="hidden" 
                                                   name="txtProductId"
                                                   value="${item.product.productId}">
                                            <input type="number"
                                                   name="txtQuantity"
                                                   value="${item.quantity}"
                                                   min="1"
                                                   style="width:60px">
                                            <input type="submit" name="btnAction" 
                                                   value="UpdateCartItem" class="btn btn-success"/>
                                        </form>
                                    </td>
                                    <td>
                                        <fmt:formatNumber 
                                            value="${item.product.price * (100 - item.product.discount) / 100 * item.quantity}" 
                                            type="number"/>
                                    </td>
                                    <td>
                                        <form action="DispatchServlet" method="post">
                                            <input type="hidden"
                                                   name="txtProductId"
                                                   value="${item.product.productId}">
                                            <input type="submit" name="btnAction" 
                                                   value="RemoveCartItem" class="btn btn-danger"/>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <div style="background:#eff6ff; border:1px solid #bfdbfe; border-radius:10px; padding:18px 24px; margin:20px 0; display:inline-block; min-width:280px;">
                            <h3 style="margin:0; color:#1e3a8a; font-size:20px; font-weight:700;">
                                Total:&nbsp;
                                <fmt:formatNumber
                                    value="${sessionScope.CART.total}"
                                    type="number"/> <span style="font-size:14px; font-weight:500;">VNÐ</span>
                            </h3>
                        </div>
                        <br>
                        <a href="DispatchServlet?btnAction=ListAllProducts" class="btn btn-primary">
                            Continue Shopping
                        </a>

                        <br/>
                        <br/>
                        <h4 style="font-weight:700; color:#1e3a8a; border-bottom:2px solid #dbeafe; padding-bottom:8px; margin-bottom:16px;">Delivery Information</h4>
                        <div class="form-card" style="margin-top:0">
                        <form action="DispatchServlet">
                            <div class="form-group">
                                <label for="txtCustomerName">Customer's name:</label>
                                <input type="text" class="form-control" 
                                       value="${sessionScope.USER_INFORMATION.lastName} ${sessionScope.USER_INFORMATION.firstName}" 
                                       name="txtCustomerName" required >
                            </div>
                            <div class="form-group">
                                <label for="txtCustomerAddress">Customer's phone</label>
                                <input type="number" class="form-control" 
                                       value="${sessionScope.USER_INFORMATION.phone}"
                                       name="txtCustomerPhone" required >
                            </div>
                            <div class="form-group">
                                <label for="txtCustomerAddress">Customer's address:</label>
                                <input type="text" class="form-control" 
                                       name="txtCustomerAddress" required>
                            </div>                      
                            <input type="hidden" name="" value="" />
                            <button type="submit" class="btn btn-success btn-block"
                                    name="btnAction" value="CheckOut">Proceed to Checkout</button>
                        </form>
                        </div>


                    </c:otherwise>
                </c:choose>
            </div>

        </div>
    </body>
</html>
