<%-- 
    Document   : updateAccount
    Created on : Feb 26, 2026, 8:31:03 AM
    Author     : nhann
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="today" class="java.util.Date"/>
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
            <c:set var="dto" value="${requestScope.ACCOUNT_INFO}" />
            <c:if test="${empty dto}">
                <c:redirect url="index.jsp"></c:redirect>
            </c:if>
            <div class="container">
                <form action="DispatchServlet" method="POST">

                    <!-- Account (readonly) -->
                    <div class="form-group">
                        <label>Account (Email)</label>
                        <input type="text" class="form-control"
                               name="account"
                               value="${dto.account}"
                               readonly>
                    </div>

                    <!-- Password -->
                    <div class="form-group">
                        <label>Password</label>
                        <input type="text" class="form-control"
                               name="password"
                               value="${dto.pass}">
                    </div>

                    <!-- Last Name -->
                    <div class="form-group">
                        <label>Last Name</label>
                        <input type="text" class="form-control"
                               name="lastName"
                               value="${dto.lastName}">
                    </div>

                    <!-- First Name -->
                    <div class="form-group">
                        <label>First Name</label>
                        <input type="text" class="form-control"
                               name="firstName"
                               value="${dto.firstName}">
                    </div>

                    <!-- Birthday -->
                    <div class="form-group">
                        <label>Birthday <p style="color:red">${requestScope.YEAR_OUT_OF_RANGE}</p></label>
                        <input type="date" class="form-control"
                               name="birthday"
                               min="1900-01-01"
                               max="<fmt:formatDate value='${today}' pattern='yyyy-MM-dd'/>"
                               value="<fmt:formatDate value='${dto.birthday}' pattern='yyyy-MM-dd'/>">
                    </div>

                    <!-- Gender -->
                    <div class="form-group">
                        <label>Gender</label><br>

                        <label class="radio-inline">
                            <input type="radio" name="gender" value="true"
                                   <c:if test="${dto.gender == true}">checked</c:if>>
                                   Male
                            </label>

                            <label class="radio-inline">
                                <input type="radio" name="gender" value="false"
                                <c:if test="${dto.gender == false}">checked</c:if>>
                                Female
                            </label>
                        </div>

                        <!-- Phone -->
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" class="form-control"
                                   name="phone"
                                   value="${dto.phone}">
                    </div>

                    <!-- isUse -->
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="use" value="true"
                                   <c:if test="${dto.isUse == true}">checked</c:if>>
                                   Active
                            </label>
                        </div>

                        <!-- Role -->
                        <div class="form-group">
                            <label>Role in System</label>
                            <select name="roleInSystem" class="form-control">

                                <option value="1"
                                <c:if test="${dto.roleInSystem == 1}">selected</c:if>>
                                    Admin
                                </option>

                                <option value="2"
                                <c:if test="${dto.roleInSystem == 2}">selected</c:if>>
                                    Manager
                                </option>

                                <option value="3"
                                <c:if test="${dto.roleInSystem == 3}">selected</c:if>>
                                Customer
                            </option>

                        </select>
                    </div>
                    <hr>
                    <button type="submit"
                            name="btnAction"
                            value="UpdateAccount"
                            class="btn btn-primary btn-block">
                        Update Account
                    </button>

                </form>
            </div>
        </div>        
    </body>
</html>
