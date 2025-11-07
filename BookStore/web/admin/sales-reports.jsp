<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sales Reports</title>
        
        <%-- 1. SỬ DỤNG CSS GIỐNG HỆT FILE MANAGE-BOOK.JSP --%>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mystyle.css">
        <link rel="stylesheet" href="css/sales-reports.css">
    </head>
    <body>
        <%-- 2. SỬ DỤNG SIDEBAR CHUNG --%>
        <%@include file="includes/sidebar_header.jsp" %>

        <%-- 3. SỬ DỤNG BỐ CỤC CHÍNH <div class="main"> --%>
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Sales Reports
            </div>
            
            <c:if test="${not empty sessionScope.successMsg}">
                <div class="message success">${sessionScope.successMsg}</div>
                <c:remove var="successMsg" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.errorMsg}">
                <div class="message error">${sessionScope.errorMsg}</div>
                <c:remove var="errorMsg" scope="session" />
            </c:if>

            <h1>Detailed Sales Report</h1>

            <%-- 4. SỬ DỤNG FORM SEARCH/SORT (Giống file mẫu) --%>
            <div class="search-sort-container">
                <%-- 1. FORM SEARCH & SORT --%>
                    <form action="${pageContext.request.contextPath}/admin/sales-reports" method="GET" class="report-controls">
                        <div class="search-box">
                            <input type="text" name="searchKey" value="${searchKey}" placeholder="Search by username, book title...">
                        </div>
                        <select name="sortBy" class="sort-box">
                            <option value="date_asc" ${sortBy == 'date_asc' ? 'selected' : ''}>Sort by Date (Oldest)</option>
                            <option value="date_desc" ${sortBy == 'date_desc' ? 'selected' : ''}>Sort by Date (Newest)</option>
                            <option value="customer_asc" ${sortBy == 'customer_asc' ? 'selected' : ''}>Sort by Customer (A-Z)</option>
                            <option value="book_asc" ${sortBy == 'book_asc' ? 'selected' : ''}>Sort by Book Title (A-Z)</option>
                        </select>
                        <button type="submit" class="btn btn-search">
                            <i class="fa-solid fa-magnifying-glass"></i> Search
                        </button>
                    </form>
            </div>
                        
            <fmt:setLocale value="vi_VN" scope="session"/>
            
            <table> 
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>ORDER DATE</th>
                        <th>CUSTOMER (FULLNAME)</th>
                        <th>USERNAME</th>
                        <th>BOOK TITLE</th>
                        <th>QTY</th>
                        <th>UNIT PRICE</th>
                        <th>TOTAL AMOUNT (Order)</th>
                        <th>STATUS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty reportData}">
                        <tr>
                            <td colspan="9" style="text-align: center; padding: 20px;">
                                Không tìm thấy dữ liệu báo cáo nào.
                            </td>
                        </tr>
                    </c:if>
                    
                    <%-- Dùng varStatus="status" giống file mẫu --%>
                    <c:forEach var="row" items="${reportData}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td><fmt:formatDate value="${row.orderDate}" pattern="dd/MM/yyyy"/></td>
                            <td>${row.customerFullName}</td>
                            <td>${row.userName}</td>
                            <td>
                                <%-- Dùng style product-name giống file mẫu --%>
                                <div class="product-name">${row.bookTitle}</div>
                                <div class="subtext">Order ID: ${row.orderID}</div>
                            </td>
                            <td>${row.quantity}</td>
                            
                            <%-- 6. SỬA LỖI CÚ PHÁP (fmt_ thành fmt:) --%>
                            <td><fmt:formatNumber value="${row.unitPrice}" type="currency" currencySymbol="đ" /></td>
                            <td><fmt:formatNumber value="${row.orderTotalAmount}" type="currency" currencySymbol="đ" /></td>
                            <td>
                                <%-- Dùng class "status" (giả sử mystyle.css có) --%>
                                <span class="status ${row.status}">
                                    ${row.status}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div> 
        
    </body>
</html> 