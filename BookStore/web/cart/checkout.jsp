<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
    <jsp:param name="pageTitle" value="Thanh toán"/>
</jsp:include>

<h1><i class="bi bi-wallet2"></i> Thông tin Thanh toán</h1>

<c:if test="${sessionScope.cart == null || empty sessionScope.cart.items || sessionScope.acc == null}">
    <div class="alert alert-warning" role="alert">
        Giỏ hàng của bạn đang trống hoặc bạn chưa đăng nhập.
        <a href="${pageContext.request.contextPath}/cart/cart.jsp" class="alert-link">Quay lại giỏ hàng</a>.
    </div>
</c:if>

<c:if test="${sessionScope.cart != null && not empty sessionScope.cart.items && sessionScope.acc != null}">
    
    <div class="row g-4 mt-3">
        
        <div class="col-md-7">
            <h4>Thông tin Giao hàng</h4>
            <div class="card shadow-sm">
                <div class="card-body">
                    <%-- Hiển thị lỗi nếu có (từ PlaceOrderServlet) --%>
                    <c:if test="${not empty requestScope.errorMsg}">
                        <div class="alert alert-danger" role="alert">
                            <strong>Lỗi:</strong> ${requestScope.errorMsg}
                        </div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/place-order" method="POST">
                        
                        <div class="mb-3">
                            <label for="name" class="form-label">Họ và Tên người nhận</label>
                            <input type="text" id="name" name="name" class="form-control" 
                                   value="${sessionScope.acc.fullname}" required>
                        </div>

                        <div class="mb-3">
                            <label for="phone" class="form-label">Số điện thoại</label>
                            <%-- Tự động điền SĐT nếu có --%>
                            <input type="tel" id="phone" name="phone" class="form-control" 
                                   value="${sessionScope.acc.phone}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="address" class="form-label">Địa chỉ Giao hàng</label>
                             <%-- Tự động điền Địa chỉ nếu có --%>
                            <input type="text" id="address" name="address" class="form-control" 
                                   value="${sessionScope.acc.address}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="notes" class="form-label">Ghi chú (Tùy chọn)</label>
                            <textarea id="notes" name="notes" rows="3" class="form-control"></textarea>
                        </div>
                        
                        <hr>
                        <button type="submit" class="btn btn-primary btn-lg w-100">
                            Xác nhận Đặt hàng
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-5">
            <h4>Tóm tắt Đơn hàng</h4>
            <div class="card shadow-sm">
                <div class="card-header">

                    <strong>${cart.items.size()}</strong> sản phẩm
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="item" items="${cart.items}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="my-0 text-truncate" style="max-width: 250px;">${item.book.title}</h6>
                                <small class="text-muted">Số lượng: ${item.quantity}</small>
                            </div>
                            <span class="text-muted">
                                <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="đ" />
                            </span>
                        </li>
                    </c:forEach>

                    <li class="list-group-item d-flex justify-content-between bg-light">
                        <strong>Tổng cộng (VND)</strong>
                        <strong class="text-danger">
                            <fmt:formatNumber value="${cart.totalMoney}" type="currency" currencySymbol="đ" />
                        </strong>
                    </li>
                </ul>
            </div>
        </div>

    </div>
</c:if>

<jsp:include page="/common/footer.jsp" />