<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
    <jsp:param name="pageTitle" value="Đơn hàng của tôi"/>
</jsp:include>

<h1><i class="bi bi-receipt-cutoff"></i> Lịch sử Đơn hàng</h1>

<%-- 1. Khối kiểm tra rỗng (Sửa link cho nhất quán) --%>
<c:if test="${empty orderList}">
    <div class="alert alert-info mt-3">
        Bạn chưa có đơn hàng nào.
        <a href="${pageContext.request.contextPath}/home" class="alert-link">Bắt đầu mua sắm</a>
    </div>
</c:if>

<%-- 2. Khối hiển thị đơn hàng --%>
<c:if test="${not empty orderList}">
    
    <%-- Thiết lập này là chính xác --%>
    <fmt:setLocale value="vi_VN" scope="session"/>
    <fmt:setTimeZone value="GMT+7" scope="session"/>
    
    <div class="table-responsive mt-3">
        <table class="table table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th scope="col">Mã Đơn</th>
                    <th scope="col">Ngày Đặt</th>
                    <th scope="col">Thông tin giao hàng</th>
                    <th scope="col">Tổng tiền</th>
                    <th scope="col">Trạng thái</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <th scope="row">#${order.orderID}</th>
                        <td>
                            <%-- SỬA 1: Hiển thị đầy đủ Giờ:Phút:Giây và Ngày/Tháng/Năm --%>
                            <fmt:formatDate value="${order.orderDate}" pattern="HH:mm:ss dd/MM/yyyy" />
                        </td>
                        <td class="text-truncate" style="max-width: 300px;">
                            ${order.shippingAddress}
                        </td>
                        <td>
                            <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="đ" />
                        </td>
                        <td>
                            <%-- Khối logic này đã RẤT TỐT --%>
                            <c:choose>
                                <c:when test="${order.status == 'Pending'}">
                                    <span class="badge bg-warning text-dark">Chờ xử lý</span>
                                </c:when>
                                <c:when test="${order.status == 'Shipped'}">
                                    <span class="badge bg-info text-dark">Đang giao</span>
                                </c:when>
                                <c:when test="${order.status == 'Completed'}">
                                    <span class="badge bg-success">Hoàn thành</span>
                                </c:when>
                                <c:when test="${order.status == 'Cancelled'}">
                                    <span class="badge bg-danger">Đã hủy</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">${order.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<jsp:include page="/common/footer.jsp" />