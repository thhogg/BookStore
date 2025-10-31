<%-- 
    Document   : manage-book
    Created on : Oct 30, 2025, 9:58:52 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Book</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>
        <!-- MAIN BEGIN-->
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Books
            </div>

            <h1>All Books</h1>

            <div>             
                <a href="add" class="add-btn">+ Add book</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>TITLE</th>
                        <th>CATEGORY</th>
                        <th>AUTHOR</th>
                        <th>PRICE</th>
                        <th>STOCK</th>  
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${books}" var="b" varStatus="status">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>
                                <div class="product-name">${b.title}</div>
                                <div class="subtext">ID: ${b.bookID}</div>
                            </td>   
                            <td>${categoryDao.getCategoryById(b.categoryID).categoryName}</td>
                            <td>${b.author}</td>
                            <td>
                                <fmt:setLocale value="en_US" />
                                <fmt:formatNumber value="${b.price}" type="number" groupingUsed="true"/>
                            </td>
                            <td>${b.stock}</td>
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
