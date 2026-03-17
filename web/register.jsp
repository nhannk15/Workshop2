<%-- 
    Document   : register
    Created on : Feb 28, 2026, 10:21:52 AM
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
            body {
                margin-top: 120px;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #68c9cc;
            }
        </style>
        <div class="section-one">
            <div class="login-box">
                <h2 class="title">Register</h2>
                <form action="DispatchServlet" method="POST">
                    <div class="form-group">
                        <label for="txtAccount">Account: <p style="color:red">${requestScope.DUPLICATE_ACCOUNT}</p></label>
                        <input type="text" class="form-control" value="${requestScope.POST_INFO.account}" 
                               id="txtAccount" name="txtAccount" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="txtPassword">Password: <p style="color:red">${requestScope.CONFIRM_NOT_MATCH}</p></label>
                        <input type="password" class="form-control" 
                               id="txtPassword" name="txtPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="txtPassword">Confirm Password:</label>
                        <input type="password" class="form-control" 
                               id="txtPassword" name="txtConfirm" required>
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
                        <label for="txtBirthday">Birthday: <p style="color:red">${requestScope.YEAR_OUT_OF_RANGE}</p></label></label>
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
                    <button type="submit" class="btn btn-default"
                            name="btnAction" value="RegisterAccount">Submit</button>
                    <a href="login.jsp" class="btn btn-default">Return to Login Page</a>
                    <div style="color:#ff3333; font-weight: bold; font-size: 12px;">
                       ${requestScope.NOTIFICATION} 
                    </div>
                    <input type="hidden" name="txtActive" value="true">
                    <input type="hidden" name="txtRoleInSystem" value="3" />
                </form> 
            </div>
        </div>
    </body>   
    </body>
</html>
