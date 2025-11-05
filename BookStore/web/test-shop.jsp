<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dal.BookDAO, java.util.List, model.Book" %>

<%@ page import="dal.BookDAO, java.util.List, model.Book" %>
<%
    BookDAO bookDAO = BookDAO.getInstance();
    List<Book> bookList = bookDAO.getAllBooks();
    request.setAttribute("bookList", bookList);
%>

<jsp:include page="/common/header.jsp">
    <jsp:param name="pageTitle" value="Trang chủ"/>
</jsp:include>

<div class="container mt-3">

<div class="row">
    <c:forEach var="book" items="${bookList}">
        <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
            <div class="card h-100 shadow-sm">
                <img src="${book.imageUrl}" class="card-img-top" alt="${book.title}" style="height: 300px; object-fit: cover;">

                <div class="card-body d-flex flex-column">
                    <h5 class="card-title text-truncate" title="${book.title}">
                        ${book.title}
                    </h5>
                    <p class="card-text text-danger fw-bold">
                    <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="đ" />
                    </p>

                    <a href="${pageContext.request.contextPath}/add-to-cart?bookId=${book.bookID}&quantity=1" class="btn btn-primary mt-auto">
                        <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                    </a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="/common/footer.jsp" />