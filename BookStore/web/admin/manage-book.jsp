<%-- 
    Document   : manage-book
    Created on : Oct 30, 2025, 9:58:52 PM
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

            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>

            <h1>All Books</h1>

            <div class="search-sort-container">
                <form method="post" action="books" >
                    <input type="text" name="search" placeholder="Search by title, author..." 
                           value="${search != null ? search : ''}" />

                    <select name="sort">
                        <option value="" ${sort == null || sort == '' ? 'selected' : ''}>Sort by</option>
                        <option value="title_asc" ${sort == 'title_asc' ? 'selected' : ''}>Title A → Z</option>
                        <option value="title_desc" ${sort == 'title_desc' ? 'selected' : ''}>Title Z → A</option>
                        <option value="price_asc" ${sort == 'price_asc' ? 'selected' : ''}>Price Low → High</option>
                        <option value="price_desc" ${sort == 'price_desc' ? 'selected' : ''}>Price High → Low</option>
                        <option value="stock_asc" ${sort == 'stock_asc' ? 'selected' : ''}>Stock Low → High</option>
                        <option value="stock_desc" ${sort == 'stock_desc' ? 'selected' : ''}>Stock High → Low</option>
                    </select>

                    <button type="submit" class="add-btn"><i class="fa fa-search"></i> Search</button>
                </form>
            </div>

            <div>             
                <a href="addbook" class="add-btn">+ Add book</a>
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
                                <a href="delete?type=book&id=${b.bookID}" class="btn btn-delete"
                                   data-title="${fn:escapeXml(b.title)}" 
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
                return confirm("Do you really want to delete this book: " + title + "?");
            }
        </script>

    </body>
</html>
