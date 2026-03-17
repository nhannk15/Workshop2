<%-- 
    Document   : addNewAccount
    Created on : Feb 26, 2026, 7:48:13 PM
    Author     : nhann
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new Account</title>

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
                <h2>Add a new account</h2>
                <form action="DispatchServlet" method="POST">
                    <div class="form-group">
                        <label for="txtAccount">Account: <p style="color:red">${requestScope.DUPLICATE_ACCOUNT}</p></label>
                        <input type="text" class="form-control" value="${requestScope.POST_INFO.account}" 
                               id="txtAccount" name="txtAccount" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="txtPassword">Password:</label>
                        <input type="text" class="form-control" 
                               id="txtPassword" name="txtPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="txtFirstName">First Name:</label>
                        <input type="text" class="form-control" value="${requestScope.POST_INFO.firstName}"  
                               id="txtFirstName" name="txtFirstName" required>
                    </div>
                    <div class="form-group">
                        <label for="txtLastName">Last Name:</label>
                        <input type="text" class="form-control" value="${requestScope.POST_INFO.lastName}"  
                               id="txtLastName" name="txtLastName" required>
                    </div>
                    <div class="form-group">
                        <label for="txtPhoneNumber">Phone Number:</label>
                        <input type="number" class="form-control" value="${requestScope.POST_INFO.phone}"
                               id="txtPhoneNumber" name="txtPhoneNumber" required>
                    </div>
                    <div class="form-group">
                        <label for="txtBirthday">Birthday: <p style="color:red">${requestScope.YEAR_OUT_OF_RANGE}</p></label>
                        <input type="date" class="form-control" value="${requestScope.POST_INFO.birthday}"
                               id="txtBirthday" name="txtBirthday" required>
                    </div>
                    <label for="txtGender">Gender: </label>
                    <div class="radio">
                        <label><input type="radio" name="txtGender" 
                                      value="true" checked>Male</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="txtGender"
                                      value="false">Female</label>
                    </div>
                    <div class="form-group">
                        <label for="sel1">Role in the system:</label>
                        <select class="form-control" id="sel1" name="txtRoleInSystem">
                            <option value="1">Administrator</option>
                            <option value="2">Normal Manager</option>
                            <option value="3" selected>Customer</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>
                            Active Account
                            <input type="checkbox" name="txtActive" 
                                   value="true" checked>
                        </label>
                    </div>
                    <button type="submit" class="btn btn-default"
                            name="btnAction" value="AddNewAccount">Submit</button>
                </form> 
            </div>            
        </div>
    </body>
</html>
