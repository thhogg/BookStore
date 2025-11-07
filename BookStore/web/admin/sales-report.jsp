<%-- 
    Document   : sales-report
    Created on : Nov 7, 2025, 10:16:16 AM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sales Report - Completed Orders</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Reports <span>/</span> Sales
            </div>

            <c:if test="${not empty errorMessage}">
                <div class="message error">${errorMessage}</div>
            </c:if>

            <h1><i class="fa-solid fa-chart-line"></i> Detailed Sales Report</h1>

            <div class="search-sort-container">
                <form method="get" action="salereport">
                    <input type="text" name="search" placeholder="Search by username, book title..." 
                           value="${search}" />

                    <select name="sort">
                        <option value="">Sort by</option>
                        <option value="username_asc" ${param.sort == 'username_asc' ? 'selected' : ''}>Username A-Z</option>
                        <option value="username_desc" ${param.sort == 'username_desc' ? 'selected' : ''}>Username Z-A</option>
                        <option value="book_asc" ${param.sort == 'book_asc' ? 'selected' : ''}>Book Title A-Z</option>
                        <option value="book_desc" ${param.sort == 'book_desc' ? 'selected' : ''}>Book Title Z-A</option>
                        <option value="date_desc" ${param.sort == 'date_desc' ? 'selected' : ''}>Order Date Newest</option>
                        <option value="date_asc" ${param.sort == 'date_asc' ? 'selected' : ''}>Order Date Oldest</option>
                        <option value="quantity_desc" ${param.sort == 'quantity_desc' ? 'selected' : ''}>Quantity Descending</option>
                        <option value="quantity_asc" ${param.sort == 'quantity_asc' ? 'selected' : ''}>Quantity Ascending</option>
                        <option value="amount_desc" ${param.sort == 'amount_desc' ? 'selected' : ''}>Total Amount Descending</option>
                        <option value="amount_asc" ${param.sort == 'amount_asc' ? 'selected' : ''}>Total Amount Ascending</option>
                    </select>

                    <button type="submit" class="add-btn"><i class="fa fa-search"></i> Search</button>

                    <c:if test="${not empty param.search or not empty param.sort}">
                        <a href="salesreport" class="btn btn-reset">Reset</a>
                    </c:if>
                </form>
            </div>

            <c:choose>
                <c:when test="${empty salesReport}">
                    <div class="message error">No completed sales records found.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>NO</th>
                                <th>CUSTOMER (FULLNAME)</th>
                                <th>USERNAME</th>
                                <th>BOOK TITLE</th>
                                <th>QUANTITY</th>
                                <th>UNIT PRICE</th>
                                <th>TOTAL AMOUNT</th>
                                <th>ORDER DATE</th>
                                <!--  <th>STATUS</th>-->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${salesReport}" var="item" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${item.fullName}</td>
                                    <td><strong>${item.userName}</strong></td>
                                    <td>${item.bookTitle}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.unitPrice}</td>
                                    <td>${item.totalAmount}</td>
                                    <td><fmt:formatDate value="${item.orderDate}" pattern="dd/MM/yyyy" /></td>
                                    <!-- <td><span class="status ${item.status}">${item.status}</span></td>-->
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

        </div>
    </body>
</html>