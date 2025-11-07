<%-- 
    Document   : category-modify
    Created on : Nov 2, 2025, 7:38:16 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Book</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
        <link rel="stylesheet" href="css/add-book.css"/>
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>

        <div class="main">

            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Categories <span>/</span> Modify Category
            </div>

            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>

            <div class="form-container">
                <c:if test="${not empty editedCategory}">
                    <h2>Edit Category</h2>
                </c:if>

                <c:if test="${empty editedCategory}">
                    <h2>Add New Category</h2>
                </c:if>

                <form id="addCategoryForm" action="editcategory" method="post">

                    <c:if test="${not empty editedCategory}">
                        <input type="hidden" name="id" value="${editedCategory.categoryID}" />
                        <input type="hidden" name="type" value="edit" />
                    </c:if>

                    <c:if test="${empty editedCategory}">
                        <input type="hidden" name="type" value="add" />
                    </c:if>

                    <div class="form-group">
                        <label for="categoryName">Category Name</label>
                        <input type="text" id="categoryName" name="categoryName" value="${editedCategory.categoryName}" required>
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" rows="4">${editedCategory.description}</textarea>
                    </div>

                    <button type="submit" class="btn">
                        <c:choose>
                            <c:when test="${not empty editedCategory}">
                                Update Category
                            </c:when>
                            <c:otherwise>
                                Add Category
                            </c:otherwise>
                        </c:choose>
                    </button>
                </form>
            </div>
        </div>

    </body>
</html>

