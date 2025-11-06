<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.pageTitle} - BookStore</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
  <div class="container">
    <!-- Logo -->
    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">
      <i class="bi bi-book"></i> BookStore
    </a>

    <!-- Nút bật/tắt menu khi thu nhỏ -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Menu bên phải -->
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/home">Shop</a>
        </li>
        <li class="nav-item">
          <c:if test="${sessionScope.acc != null}">
            <a class="nav-link" href="${pageContext.request.contextPath}/my-orders">Đơn hàng của tôi</a>
          </c:if>
        </li>
        <li class="nav-item">
          <a class="btn btn-primary" href="${pageContext.request.contextPath}/cart/cart.jsp">
            <i class="bi bi-cart-fill"></i> Giỏ hàng
            <c:if test="${sessionScope.cart != null && not empty sessionScope.cart.items}">
              <span class="badge bg-danger">${sessionScope.cart.totalQuantity}</span>
            </c:if>
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>


<div class="container mt-4">