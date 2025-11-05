<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
    <jsp:param name="pageTitle" value="Giỏ hàng"/>
</jsp:include>

<h1><i class="bi bi-cart-fill"></i> Giỏ hàng của bạn</h1>

<c:if test="${sessionScope.cart == null || empty sessionScope.cart.items}">
    <div class="alert alert-info mt-3">
        Giỏ hàng của bạn đang trống.
        <a href="${pageContext.request.contextPath}/views/home.jsp" class="alert-link">Tiếp tục mua sắm</a>
    </div>
</c:if>

<c:if test="${sessionScope.cart != null && not empty sessionScope.cart.items}">
    <div class="table-responsive"> <table class="table table-hover align-middle mt-3">
            <thead class="table-light">
                <tr>
                    <th scope="col" colspan="2">Sản phẩm</th>
                    <th scope="col">Đơn giá</th>
                    <th scope="col">Số lượng</th>
                    <th scope="col">Thành tiền</th>
                    <th scope="col">Xóa</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <tr>
                        <td style="width: 100px;">
                            <img src="${item.book.imageUrl}" alt="${item.book.title}" class="img-fluid rounded" style="max-height: 120px;">
                        </td>
                        <td>${item.book.title}</td>
                        <td>
                            <fmt:formatNumber value="${item.book.price}" type="currency" currencySymbol="đ" />
                        </td>
                        <td>
                            ${item.quantity}
                        </td>
                        <td>
                            <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="đ" />
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/remove-from-cart?bookId=${item.book.bookID}" class="btn btn-outline-danger btn-sm">
                                <i class="bi bi-trash-fill"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="row justify-content-end mt-4">
        <div class="col-md-5">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h4 class="d-flex justify-content-between align-items-center">
                        <span>Tổng cộng:</span>
                        <strong class="text-danger">
                            <fmt:formatNumber value="${sessionScope.cart.totalMoney}" type="currency" currencySymbol="đ" />
                        </strong>
                    </h4>
                    <hr>
                    <a href="${pageContext.request.contextPath}/test-shop.jsp" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Tiếp tục mua sắm
                    </a>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success float-end">
                        Tiến hành Thanh toán <i class="bi bi-arrow-right"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</c:if>

<jsp:include page="/common/footer.jsp" />