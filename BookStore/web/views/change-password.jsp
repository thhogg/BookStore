<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Change-Password</title>
    <style>
        .container { width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 8px; }
        input { width: 100%; padding: 10px; margin: 8px 0; border: 1px solid #ddd; border-radius: 4px; }
        button { background: #007bff; color: white; padding: 10px; border: none; width: 100%; border-radius: 4px; }
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
<div class="container">
    <h2>Change-Password</h2>
    <p>Xin chào ${sessionScope.acc.userName} <strong>${user.fullname}</strong> (<code>@${user.userName}</code>)</p>

    <form method="post" action="<%= request.getContextPath() %>/change-password">
        <input type="password" name="oldPassword" placeholder="Mật khẩu cũ" required><br>
        <input type="password" name="newPassword" placeholder="Mật khẩu mới" required><br>
        <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu mới" required><br>
        <button type="submit">Change-Password</button>
    </form>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <c:if test="${not empty success}">
        <p class="success">${success}</p>
    </c:if>

    <br>
    <a href="<%= request.getContextPath() %>/home">Quay lại trang chủ</a>
</div>
</body>
</html>