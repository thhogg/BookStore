<%-- 
    Document   : add-user
    Created on : Nov 1, 2025, 9:24:39 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Book</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
        <link rel="stylesheet" href="css/add-book.css"/>
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>

        <div class="main">

            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Users <span>/</span> Add User
            </div>

            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>

            <div class="form-container">
                <c:if test="${not empty editedUser}">
                    <h2>Edit User</h2>
                </c:if>

                <c:if test="${empty editedUser}">
                    <h2>Add New User</h2>
                </c:if>

                <form id="addUserForm" action="adduser" method="post">

                    <c:if test="${not empty editedUser}">
                        <input type="hidden" name="id" value="${editedUser.userID}" />
                        <input type="hidden" name="type" value="edit" />
                    </c:if>

                    <c:if test="${empty editedUser}">
                        <input type="hidden" name="type" value="add" />
                    </c:if>

                    <div class="form-group">
                        <label for="userName">Username</label>
                        <input type="text" id="userName" name="userName" value="${editedUser.userName}" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="password-wrapper">
                            <input type="password" id="password" name="password" value="${editedUser.password}" required>
                            <button type="button" id="togglePassword" class="eye-btn" aria-label="Toggle password visibility">
                                <i class="fa fa-eye"></i>
                            </button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fullname">Full Name</label>
                        <input type="text" id="fullname" name="fullname" value="${editedUser.fullname}" required>              
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" value="${editedUser.email}" required>
                    </div>

                    <div class="form-group">
                        <label for="phone">Phone</label>
                        <input type="text" id="phone" name="phone"  value="${editedUser.phone}"
                               pattern="[0-9+\-\s]{7,15}" title="Please enter a valid phone number">
                    </div>

                    <div class="form-group">
                        <label for="address">Address</label>
                        <textarea id="address" name="address" rows="3">${editedUser.address}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="role">Role</label>
                        <select id="role" name="role" required>
                            <option value="" disabled selected>Select role</option>
                            <option value="Admin" ${editedUser != null && editedUser.role.equals("Admin")?"selected":""}>
                                Admin
                            </option>
                            <option value="Customer" ${ editedUser != null &&editedUser.role.equals("Customer")?"selected":""}>
                                Customer
                            </option>
                            <option value="Manager" ${editedUser != null && editedUser.role.equals("Manager")?"selected":""}>
                                Manager
                            </option>
                        </select>
                    </div>

                    <button type="submit" class="btn">
                        <c:if test="${not empty editedUser}">
                            Update User
                        </c:if>

                        <c:if test="${empty editedUser}">
                            Add User
                        </c:if>
                    </button>
                </form>
            </div>
        </div>

        <script>
            const togglePassword = document.querySelector('#togglePassword');
            const passwordInput = document.querySelector('#password');

            togglePassword.addEventListener('click', function () {
                // đổi kiểu input
                const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordInput.setAttribute('type', type);

                // đổi icon
                this.querySelector('i').classList.toggle('fa-eye');
                this.querySelector('i').classList.toggle('fa-eye-slash');
            });
        </script>
    </body>
</html>
