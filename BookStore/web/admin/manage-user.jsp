<%-- 
    Document   : manage-user
    Created on : Oct 31, 2025, 6:25:26 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage User</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>
        <!-- MAIN BEGIN-->
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Users
            </div>

            <h1>All Books</h1>

            <div>             
                <a href="add" class="add-btn">+ Add user</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>FULLNAME</th>
                        <th>USERNAME</th>
                        <th>EMAIL</th>
                        <th>ROLE</th>
                        <th>REGISTERED</th>  
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="u" varStatus="status">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>
                                <div class="product-name">${u.fullname}</div>
                                <div class="subtext">ID: ${u.userID}</div>
                            </td>   
                            <td>${u.userName}</td>
                            <td>${u.email}</td>
                            <td>${u.role}</td>
                            <td>
                                <fmt:formatDate value="${u.createdAt}" pattern="dd/MM/yyyy" />
                            </td>
                            <td>
                                <a href="editbook?bookId=${b.bookID}" class="btn btn-edit">
                                    <i class="fa-solid fa-pen"></i> Edit
                                </a>
                                <a href="#" class="btn btn-delete">
                                    <i class="fa-solid fa-trash"></i> Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- MAIN END-->
    </body>
</html>
