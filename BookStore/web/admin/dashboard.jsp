<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>

        <!-- CSS và Icon cơ bản -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
        <link rel="stylesheet" href="css/dashboard-style.css">
    </head>
    <body>

        <jsp:include page="includes/sidebar_header.jsp" />
        
        <div class="main">

            <div class="breadcrumb">
                <!-- Home trỏ về trang Shop test -->
                <a href="${pageContext.request.contextPath}/test-shop.jsp">Home</a> 
                <span>/</span> 
                <!-- Admin trỏ về Dashboard -->
                <a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a>
                <span>/</span> 
                Dashboard
            </div>

            <h1>Dashboard Overview</h1>

            <!-- KHU VỰC 4 CARD SỐ LIỆU -->
            <div class="dashboard-container">
                
                <!-- Tổng số sách -->
                <div class="dashboard-card card-books">
                    <div class="card-content">
                        <h3>${totalBooks}</h3>
                        <p>Tổng số sách</p>
                    </div>
                    <div class="card-icon">
                        <i class="fa-solid fa-book"></i>
                    </div>
                </div>

                <!-- Tổng số khách hàng -->
                <div class="dashboard-card card-users">
                    <div class="card-content">
                        <h3>${totalUsers}</h3>
                        <p>Tổng số khách hàng</p>
                    </div>
                    <div class="card-icon">
                        <i class="fa-solid fa-users"></i>
                    </div>
                </div>

                <!-- Tổng số đơn hàng -->
                <div class="dashboard-card card-orders">
                    <div class="card-content">
                        <h3>${totalOrders}</h3>
                        <p>Tổng số đơn hàng</p>
                    </div>
                    <div class="card-icon">
                        <i class="fa-solid fa-receipt"></i>
                    </div>
                </div>

                <!-- Tổng doanh thu -->
                <div class="dashboard-card card-revenue">
                    <div class="card-content">
                        <h3>
                            <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="đ" maxFractionDigits="0" />
                        </h3>
                        <p>Tổng doanh thu</p>
                    </div>
                    <div class="card-icon">
                        <i class="fa-solid fa-dollar-sign"></i>
                    </div>
                </div>
            </div>
            
            <!-- (Nội dung của trang Dashboard đã kết thúc ở đây) -->

        </div>
    </body>
</html>