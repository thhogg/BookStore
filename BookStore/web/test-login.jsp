<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="model.User" %> 

<%
    // --- BẮT ĐẦU CODE JAVA GIẢ LẬP ---

    // 2. Tạo một đối tượng User giả
    User fakeUser = new User(); 

    // 3. Set các thông tin cho tài khoản giả (dùng đúng tên hàm trong User.java)
    fakeUser.setUserID(3); // ID giả
    fakeUser.setUserName("test_user"); // Tên đăng nhập giả
    fakeUser.setFullname("Người Dùng Thử Nghiệm"); // Tên đầy đủ giả
    fakeUser.setEmail("test@email.com"); // Email giả
    fakeUser.setRole("Customer"); // Quan trọng: Đặt vai trò là Khách hàng

    // 4. LƯU VÀO SESSION
    // Tên "account" phải khớp với tên bạn dùng trong CheckoutServlet
    session.setAttribute("account", fakeUser);

    // 5. CHUYỂN HƯỚNG SANG TRANG SHOP
    // Sau khi đăng nhập giả, tự động đi đến trang shop
    response.sendRedirect("test-shop.jsp");

%>

<!DOCTYPE html>
<html>
<head>
    <title>Đang đăng nhập...</title>
</head>
<body>
    <p>Đang thực hiện đăng nhập giả lập... 
    <a href="test-shop.jsp">Nhấn vào đây nếu trang không tự chuyển.</a>
    </p>
</body>
</html>