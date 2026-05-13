<%-- 
    Document   : productDetail
    Created on : Mar 8, 2026, 9:09:40 PM
    Author     : nhann
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <c:set var="p" value="${requestScope.PRODUCT_INFO}" />

            <div class="container" style="margin-top:32px">
                <div class="form-card">
                <div class="row">

                    <!-- Product Image -->
                    <div class="col-md-5 text-center">
                        <img src="${pageContext.request.contextPath}${p.productImage}" 
                             class="img-responsive img-thumbnail"
                             style="max-height:400px">
                    </div>

                    <!-- Product Info -->
                    <div class="col-md-7">

                        <h2 style="font-weight:bold">${p.productName}</h2>

                        <p style="font-size:16px">
                            ${p.brief}
                        </p>

                        <hr>

                        <p>
                            <b>Unit:</b> ${p.unit}
                        </p>

                        <p>
                            <b>Posted Date:</b>
                            <fmt:formatDate value="${p.postedDate}" pattern="dd-MM-yyyy"/>
                        </p>

                        <p>
                            <b>Category:</b> ${p.typeId.categoryName}
                        </p>

                        <hr>

                        <!-- Price -->
                        <c:if test="${p.discount > 0}">
                            <h4>
                                <del>${p.price} vnd</del>
                            </h4>

                            <h3 style="color:red">
                                <fmt:formatNumber
                                    value="${p.price - p.price * p.discount / 100}"
                                    type="number"
                                    groupingUsed="true"/> vnd
                            </h3>

                            <p style="color:red">
                                Discount: ${p.discount}%
                            </p>
                        </c:if>

                        <c:if test="${p.discount == 0}">
                            <h3 style="color:red">
                                <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>
                                vnd
                            </h3>
                        </c:if>

                        <br>

                        <!-- Add to cart -->
                        <c:url var="addToCartUrl" value="DispatchServlet">
                            <c:param name="btnAction" value="AddToCart"/>
                            <c:param name="txtProductId" value="${p.productId}"/>
                        </c:url>
                        
                        <c:if test="${sessionScope.USER_INFORMATION.roleInSystem > 0}">
                            <a href="${addToCartUrl}" class="btn btn-success btn-lg">
                                Add to Cart
                            </a>
                        </c:if>
                        <a href="DispatchServlet?btnAction=ListAllProducts"
                           class="btn btn-default btn-lg">
                            Back
                        </a>

                    </div>

                </div>
                </div>
            </div>
        </div>

    </body>
</html>
