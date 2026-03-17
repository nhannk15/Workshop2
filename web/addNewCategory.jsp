<%-- 
    Document   : addNewCategory
    Created on : Feb 27, 2026, 9:09:32 AM
    Author     : nhann
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new Category</title>

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
                <h2>New Category</h2>
                <form action="DispatchServlet">
                    <div class="form-group">
                        <label for="txtCategoryName">Category Name</label>
                        <input type="text" class="form-control" 
                               id="txtCategoryName" name="txtCategoryName" required=""
                               placeholder="The name of the new Category">
                    </div>
                    <div class="form-group">
                        <label for="txtMemo">Memo</label>
                        <input type="text" class="form-control" 
                               id="txtMemo" name="txtMemo"
                               placeholder="The product used for travling...">
                    </div>
                    <button type="submit" class="btn btn-default"
                            name="btnAction" value="AddNewCategory">Save</button>
                </form>
            </div>
        </div>
    </body>
</html>
