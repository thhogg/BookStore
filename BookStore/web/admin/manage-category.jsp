<%-- 
    Document   : manage-category
    Created on : Oct 31, 2025, 7:08:42 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Category</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>
        <!-- MAIN BEGIN-->
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Categories
            </div>

            <c:if test="${not empty successMessage}">
                <div class="message success">${successMessage}</div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="message error">${errorMessage}</div>
            </c:if>

            <h1>All Categories</h1>

            <div>             
                <a href="editcategory" class="add-btn">+ Add category</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>CATEGORY NAME</th>
                        <th>DESCRIPTION</th>  
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${categories}" var="c" varStatus="status">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>
                                <div class="product-name">${c.categoryName}</div>
                                <div class="subtext">ID: ${c.categoryID}</div>
                            </td>   
                            <td>${c.description}</td>
                            <td>
                                <a href="editcategory?type=edit&id=${c.categoryID}" class="btn btn-edit">
                                    <i class="fa-solid fa-pen"></i> Edit
                                </a>
                                <a href="delete?type=category&id=${c.categoryID}" class="btn btn-delete"
                                   data-title="${fn:escapeXml(c.categoryName)}" 
                                   onclick="return xacNhan(this)">
                                    <i class="fa-solid fa-trash"></i> Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- MAIN END-->

        <script>
            function xacNhan(el) {
                var title = el.getAttribute('data-title');
                return confirm("Do you really want to delete this category: " + title + "?");
            }
        </script>
    </body>
</html>
