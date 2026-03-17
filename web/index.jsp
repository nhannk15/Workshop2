<%-- 
    Document   : index
    Created on : Feb 25, 2026, 7:29:06 PM
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
        <style>
            .divider {
                height: 4px;
                background: linear-gradient(to right, #ff4d4d, #333);
                margin: 30px 0;
                border-radius: 5px;
            }
        </style>
        <div class="section-two">
            <%@include file="WEB-INF/menu.jspf" %>
            <div class="container">
                <div style="color:red;font-weight: bold;font-size: 16px">${requestScope.NOTIFICATION}</div>
                <div class="divider"></div>
                <c:if test="${sessionScope.USER_INFORMATION.roleInSystem == 1}">
                    <h2>You are the <b style="color:red">Administration</b></h2>
                    <h3>You only works with accounts!</h3>
                    <img src="images/sanPham/heimdall.jpg" alt=""/>
                </c:if>
                <c:if test="${sessionScope.USER_INFORMATION.roleInSystem == 2}">
                    <h2>You are the <b style="color:red">Manager</b></h2>
                    <h3>You works with categories and products but not accounts!</h3>
                    <img src="images/sanPham/thomasShelby.gif" alt=""/>
                </c:if>
                <c:if test="${sessionScope.USER_INFORMATION.roleInSystem != 1 
                              && sessionScope.USER_INFORMATION.roleInSystem != 2}">
                    <h2>You are the <b style="color:red">Customer</b></h2>
                    <h3>You can see products and their categories</h3>
                    <img src="images/sanPham/leonardoDiCaprio.jpg" alt=""/>
                </c:if>        
            </div>      
        </div>       
    </body>
</html>
