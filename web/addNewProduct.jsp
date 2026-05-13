<%-- 
    Document   : addNewProduct
    Created on : Mar 3, 2026, 12:15:22 PM
    Author     : nhann
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Product</title>

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
                <h2 class="form-card-title">Add a new product</h2>
                <div class="form-card">
                <form action="DispatchServlet" method="POST">

                    <!-- Product Id -->
                    <div class="form-group">
                        <label>Product id <p style="color:red">${requestScope.DUPLICATE_PRODUCT_ID}</p></label>
                        <input type="text" class="form-control"
                               name="txtProductId" maxlength="10"
                               value="${requestScope.POST_INFO.productId}" required>
                    </div>

                    <!-- Product name -->
                    <div class="form-group">
                        <label>Product Name</label>
                        <input type="text" class="form-control"
                               name="txtProductName"
                               value="${requestScope.POST_INFO.productName}" required>
                    </div>

                    <!-- Product image -->
                    <div class="form-group">
                        <label>Product Image URL</label>
                        <input type="text" class="form-control"
                               name="txtProductImage"
                               <c:if test="${empty requestScope.POST_INFO.productImage}">
                                   value="\images\sanPham\"
                               </c:if>
                               <c:if test="${not empty requestScope.POST_INFO.productImage}">
                                   value="${requestScope.POST_INFO.productImage}" 
                               </c:if>
                               
                               placeholder="Upload and copy URL on File Explorer only. For ex: \images\sanPham\tre-g-.gif">
                    </div>

                    <!-- Brief -->
                    <div class="form-group">
                        <label for="txtBrief">Brief</label>
                        <textarea class="form-control" 
                                  id="txtBrief" 
                                  name="txtBrief" 
                                  rows="4" 
                                  >${requestScope.POST_INFO.brief}</textarea>
                    </div>


                    <!-- Posted Date -->
                    <div class="form-group">
                        <label>Posted Date <p style="color:red">${requestScope.YEAR_OUT_OF_RANGE}</p> </label>
                        <input type="datetime-local" class="form-control"
                               name="txtPostedDate"
                               value="<fmt:formatDate value='${requestScope.POST_INFO.postedDate}'
                               pattern='yyyy-MM-dd'T'HH:mm'/>"
                               step="1" required
                                min="1753-01-01T00:00:00"
                                max="9999-12-31T23:59:59">
                    </div>

                    <!-- Category -->
                    <div class="form-group">
                        <label>Category Type</label>
                        <select name="txtTypeId" class="form-control">
                            <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                                <option value="${category.typeId}"
                                    <c:if test="${category.typeId == requestScope.POST_INFO.typeId}">selected</c:if>>
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
                                    <c:if test="${account.account == requestScope.POST_INFO.account}">selected</c:if>>
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
                               value="${requestScope.POST_INFO.unit}" required>
                    </div>

                    <!-- Price -->
                    <div class="form-group">
                        <label>Price</label>
                        <input type="number" class="form-control"
                               name="txtPrice"
                               value="${requestScope.POST_INFO.price}" required>
                    </div>

                    <!-- Discount -->
                    <div class="form-group">
                        <label>Discount</label>
                        <input type="number" class="form-control"
                               name="txtDiscount"
                               value="${requestScope.POST_INFO.discount}" required>
                    </div>

                    <hr>
                    <button type="submit"
                            name="btnAction"
                            value="AddNewProductThisTime"
                            class="btn btn-primary btn-block">
                        Add Product
                    </button>
                </form>
                </div>
            </div>
        </div>
    </body>
</html>
