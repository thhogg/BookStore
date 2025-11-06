<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>

    <!-- Font Awesome & CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
    <link rel="stylesheet" href="css/mystyle.css">
    <link rel="stylesheet" href="css/dashboard-style.css">
</head>

<body>
<%@include file="includes/sidebar_header.jsp" %>

<div class="main">
    <div class="breadcrumb">
        Home <span>/</span> Admin <span>/</span> Dashboard
    </div>

    <fmt:setLocale value="vi_VN" scope="session"/>

    <!-- Dòng 1: 4 thẻ thống kê chính -->
    <div class="dashboard-container">
        <div class="dashboard-card card-revenue">
            <div class="card-content">
                <h3><fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="đ" /></h3>
                <p>Tổng Doanh thu (Đơn đã hoàn thành)</p>
            </div>
            <div class="card-icon"><i class="fa-solid fa-dollar-sign"></i></div>
        </div>

        <div class="dashboard-card card-users">
            <div class="card-content">
                <h3>${totalUsers}</h3>
                <p>Tổng số Khách hàng (Role Customer)</p>
            </div>
            <div class="card-icon"><i class="fa-solid fa-users"></i></div>
        </div>

        <div class="dashboard-card card-books">
            <div class="card-content">
                <h3>${totalBooks}</h3>
                <p>Tổng số Sách (trong kho)</p>
            </div>
            <div class="card-icon"><i class="fa-solid fa-book"></i></div>
        </div>

        <div class="dashboard-card card-orders">
            <div class="card-content">
                <h3>${totalOrders}</h3>
                <p>Tổng số Đơn hàng (tất cả trạng thái)</p>
            </div>
            <div class="card-icon"><i class="fa-solid fa-receipt"></i></div>
        </div>
    </div>

    <!-- Dòng 2: 2 thẻ thống kê mới -->
    <div class="dashboard-container" style="margin-top: 25px;">
        <div class="dashboard-card card-sold">
            <div class="card-content">
                <h3>${totalBooksSold}</h3>
                <p>Sách đã bán (từ đơn hoàn thành)</p>
            </div>
            <div class="card-icon"><i class="fa-solid fa-cart-shopping"></i></div>
        </div>

        <div class="dashboard-card card-buyers">
            <div class="card-content">
                <h3>${totalPurchasingCustomers}</h3>
                <p>Khách hàng đã mua (Unique)</p>
            </div>
            <div class="card-icon"><i class="fa-solid fa-user-check"></i></div>
        </div>
    </div>

    <!-- Dòng 3: Hai bảng song song -->
    <div class="dashboard-row" style="display: flex; gap: 25px; margin-top: 30px; align-items: flex-start;">

        <!-- Bảng xếp hạng Khách hàng -->
        <div class="dashboard-col" style="flex: 1;">
            <div class="form-container">
                <h2>Bảng xếp hạng Khách hàng (Top 10)</h2>

                <c:if test="${empty customerRankings}">
                    <p>Chưa có dữ liệu xếp hạng.</p>
                </c:if>

                <c:if test="${not empty customerRankings}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Xếp hạng</th>
                                <th>Tên Khách hàng</th>
                                <th>Tổng số đơn</th>
                                <th>Tổng chi tiêu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rank" items="${customerRankings}" varStatus="loop" begin="0" end="9">
                                <tr>
                                    <td><strong>#${loop.count}</strong></td>
                                    <td>${rank.userName}</td>
                                    <td>${rank.orderCount} đơn</td>
                                    <td><strong><fmt:formatNumber value="${rank.totalSpent}" type="currency" currencySymbol="đ" /></strong></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>

        <!-- Bảng Sách Bán Chạy -->
        <div class="dashboard-col" style="flex: 1;">
            <div class="form-container">
                <h2>Sách Bán Chạy Nhất (Top 5)</h2>

                <c:if test="${empty bestSellers}">
                    <p>Chưa có dữ liệu sách bán chạy.</p>
                </c:if>

                <c:if test="${not empty bestSellers}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Hạng</th>
                                <th>Tên Sách</th>
                                <th>Đã bán</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="${bestSellers}" varStatus="loop">
                                <tr>
                                    <td><strong>#${loop.count}</strong></td>
                                    <td>${book.title}</td>
                                    <td><strong>${book.totalSold}</strong> cuốn</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div> <!-- Kết thúc .dashboard-row -->

</div>
</body>
</html>
