<%-- 
    Document   : updateProduct
    Created on : Mar 3, 2026, 9:12:34 AM
    Author     : nhann
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product Page</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFORMATION.roleInSystem != 2}">
            <c:redirect url="index.jsp">
                <c:param name="NOTIFICATION" value="Only manager can use this function"></c:param>
            </c:redirect>
        </c:if>
        <%@include file="WEB-INF/sessionCheck.jspf" %>
        <div class="section-two">
            <%@include file="WEB-INF/menu.jspf" %>
            <div class="container">
                
            </div>
            <c:set var="dto" value="${requestScope.PRODUCT_INFO}" />
            <c:if test="${empty dto}">
                <c:redirect url="index.jsp"></c:redirect>
            </c:if>
            <div class="container">
                <h2 class="form-card-title">Update Product</h2>
                <div class="form-card">
                <form action="DispatchServlet" method="POST" accept-charset="UTF-8">
                    <!-- Product Id -->
                    <div class="form-group">
                        <label>Product id</label>
                        <input type="text" class="form-control"
                               name="txtProductId"
                               value="${dto.productId}"
                               readonly>
                    </div>

                    <!-- Product name -->
                    <div class="form-group">
                        <label>Product Name</label>
                        <input type="text" class="form-control"
                               name="txtProductName"
                               value="${dto.productName}" required>
                    </div>

                    <!-- Product image -->
                    <div class="form-group">
                        <label>Product Image URL</label>
                        <input type="text" class="form-control"
                               name="txtProductImage"
                               value="${dto.productImage}"
                               placeholder="Upload and copy URL on File Explorer only. For ex: \images\sanPham\tre-g-.gif">
                    </div>

                    <!-- Brief -->
                    <div class="form-group">
                        <label for="txtBrief">Brief</label>
                        <textarea class="form-control" 
                                  id="txtBrief" 
                                  name="txtBrief" 
                                  rows="4" 
                                  required>${dto.brief}</textarea>
                    </div>


                    <!-- Posted Date -->
                    <div class="form-group">
                        <label>Posted Date</label>
                        <input type="datetime-local" class="form-control"
                               name="txtPostedDate"
                               value="<fmt:formatDate value='${dto.postedDate}'
                                               pattern='yyyy-MM-dd\'T\'HH:mm:ss'/>"
                               step="1">
                    </div>

                    <!-- Category -->
                    <div class="form-group">
                        <label>Category Type</label>
                        <select name="txtTypeId" class="form-control">
                            <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                                <option value="${category.typeId}"
                                        <c:if test="${dto.typeId.typeId == category.typeId}">selected</c:if>>
                                    ${category.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Account -->
                    <div class="form-group">
                        <label>Account</label>
                        <select name="txtAccount" class="form-control">
                            <c:forEach var="account" items="${requestScope.ACCOUNT_LIST}">
                                <option value="${account.account}"
                                        <c:if test="${dto.account.account == account.account}">selected</c:if>>
                                    ${account.account}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Unit -->
                    <div class="form-group">
                        <label>Unit</label>
                        <input type="text" class="form-control"
                               name="txtUnit"
                               value="${dto.unit}">
                    </div>

                    <!-- Price -->
                    <div class="form-group">
                        <label>Price</label>
                        <input type="number" class="form-control"
                               name="txtPrice"
                               value="${dto.price}">
                    </div>

                    <!-- Discount -->
                    <div class="form-group">
                        <label>Discount</label>
                        <input type="number" class="form-control"
                               name="txtDiscount"
                               value="${dto.discount}" required>
                    </div>

                    <hr>
                    <button type="submit"
                            name="btnAction"
                            value="UpdateProduct"
                            class="btn btn-primary btn-block">
                        Update Product
                    </button>
                </form>
                </div>
            </div>
            
        </div>
    </div>
</body>
</html>
