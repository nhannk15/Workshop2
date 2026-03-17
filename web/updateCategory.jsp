<%-- 
    Document   : updateCategory
    Created on : Feb 27, 2026, 1:31:33 PM
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
            <c:set var="dto" value="${requestScope.CATEGORY_INFO}" />
            <c:if test="${empty dto}">
                <c:redirect url="index.jsp"></c:redirect>
            </c:if>
            <div class="container">
                <form action="DispatchServlet" method="POST">

                    <div class="form-group">
                        <label>Type ID</label>
                        <input type="text" class="form-control"
                               name="txtTypeId"
                               value="${dto.typeId}"
                               readonly>
                    </div>
                    <div class="form-group">
                        <label>Category name</label>
                        <input type="text" class="form-control"
                               name="txtCategoryName" required
                               value="${dto.categoryName}">
                    </div>
                    <div class="form-group">
                        <label>Memo</label>
                        <input type="text" class="form-control"
                               name="txtMemo"
                               value="${dto.memo}">
                    </div>
                    <hr>
                    <button type="submit"
                            name="btnAction"
                            value="UpdateCategory"
                            class="btn btn-primary btn-block">
                        Update Category
                    </button>
                </form>
            </div>
        </div> 
    </body>
</html>
