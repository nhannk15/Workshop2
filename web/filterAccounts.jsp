<%-- 
    Document   : filterAccounts
    Created on : Mar 15, 2026, 7:02:05 PM
    Author     : nhann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                <p style="color:red;font-size: 25px;font-weight: bold">${requestScope.NOTIFICATION}</p>

                <c:if test="${not empty applicationScope.VIEWED_PRODUCTS}">
                    <c:set var="viewedProducts"
                           value="${applicationScope.VIEWED_PRODUCTS.viewedProducts}" />
                    <c:if test="${not empty viewedProducts}">
                        <table class="custom-table">
                            <thead>
                                <tr>
                                    <th>Account</th>
                                    <th>Type</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="entry" items="${viewedProducts}">
                                    <!-- Accounts Lists -->
                                    <c:set var="account" value="${entry.key}"></c:set>

                                        <!-- Products Lists -->
                                    <c:set var="productList" value="${entry.value.viewedProducts}"></c:set>

                                        <!-- Variables -->
                                    <c:set var="totalMoney" value="0" />
                                    <c:set var="count" value="0" />


                                    <c:forEach var="product" items="${productList}">
                                        <c:set var="totalMoney" 
                                               value="${totalMoney + product.price}" />
                                        <c:set var="count" 
                                               value="${count + 1}" />
                                    </c:forEach>
                                    <tr>
                                        <td>${account}</td>
                                        
                                        <td>
                                            <c:if test="${totalMoney / count > 15000000}">
                                                High income user
                                            </c:if> 
                                            <c:if test="${totalMoney / count > 5000000 && totalMoney / count <= 15000000}">
                                                Medium income user
                                            </c:if> 
                                            <c:if test="${totalMoney / count < 5000000}">
                                                Low income user
                                            </c:if> 
                                        </td>                                 
                                    </tr>
                                </c:forEach> 
                            </tbody>
                        </table>
                    </c:if>
                </c:if>
            </div>

        </div>  
    </body>
</html>
