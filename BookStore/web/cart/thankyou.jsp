<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/common/header.jsp">
    <jsp:param name="pageTitle" value="Hoàn tất đơn hàng"/>
</jsp:include>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">
            
            <i class="bi bi-check-circle-fill text-success" style="font-size: 5rem;"></i>
            
            <h1 class="display-4 mt-3">Đặt hàng thành công!</h1>
            <p class="lead">Cảm ơn bạn đã mua hàng tại BookStore.</p>
            <p>Đơn hàng của bạn đang được xử lý và sẽ sớm được giao đến bạn.</p>
            
            <hr class="my-4">
            
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg">
                <i class="bi bi-arrow-left"></i> Quay về trang chủ
            </a>
            <a href="${pageContext.request.contextPath}/my-orders" class="btn btn-outline-secondary btn-lg">
                <i class="bi bi-receipt"></i> Xem lịch sử đơn hàng
            </a>
            
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />