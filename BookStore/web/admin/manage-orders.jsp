<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Orders</title>
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
        <link rel="stylesheet" href="css/manage-orders.css"/>
    </head>
    <body>

        <%@include file="includes/sidebar_header.jsp" %>

        <%-- 
            2. NỘI DUNG TRANG: (Đã chính xác)
        --%>
        <div class="main">

            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Orders
            </div>
            
            <%-- Hiển thị thông báo (Đã chính xác) --%>
            <c:if test="${not empty sessionScope.successMsg}">
                <div class="success-message">${sessionScope.successMsg}</div>
                <c:remove var="successMsg" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.errorMsg}">
                <div class="error-message">${sessionScope.errorMsg}</div>
                <c:remove var="errorMsg" scope="session" />
            </c:if>
                
            <%-- 
                3. NỘI DUNG CHÍNH (Bảng đơn hàng):
            --%>
            <div class="form-container"> 
                <h2>Manage All Orders</h2>
                
                <fmt:setLocale value="vi_VN" scope="session"/>
                <fmt:setTimeZone value="GMT+7" scope="session"/>
                
                <c:if test="${empty allOrders}">
                    <p>Không tìm thấy đơn hàng nào.</p>
                </c:if>

                <c:if test="${not empty allOrders}">
                
                    <%-- SỬA LỖI: Bỏ dấu chấm "." thừa sau chữ class --%>
                    <table class="table" style="width: 100%;"> 
                        <thead>
                            <tr>
                                <th>Mã Đơn</th>
                                <th>Khách hàng</th>
                                <th>Ngày Đặt</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái</th>
                                <th style="width: 250px;">Cập nhật Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%-- Vòng lặp (Đã chính xác) --%>
                            <c:forEach var="order" items="${allOrders}">
                                <tr>
                                    <td>#${order.orderID}</td>
                                    <td>${order.userName}</td>
                                    <td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yy HH:mm"/></td>
                                    <td><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="đ" /></td>
                                    <td>
                                        <span class="status ${order.status}">${order.status}</span>
                                    </td>
                                    <td>
                                        <%-- Form cập nhật (Đã chính xác) --%>
                                        <form action="${pageContext.request.contextPath}/admin/update-order-status" method="POST" class="update-form">
                                            <input type="hidden" name="orderId" value="${order.orderID}">
                                            <select name="newStatus">
                                                <option value="Pending" ${order.status == 'Pending' ? 'selected' : ''}>Chờ xử lý</option>
                                                <option value="Shipped" ${order.status == 'Shipped' ? 'selected' : ''}>Đang giao</option>
                                                <option value="Completed" ${order.status == 'Completed' ? 'selected' : ''}>Hoàn thành</option>
                                                <option value="Cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Đã hủy</option>
                                            </select>
                                            <button type="submit" class="btn">Lưu</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div> 
        </div> <%-- Kết thúc <div class="main"> --%>
    </body>
</html>