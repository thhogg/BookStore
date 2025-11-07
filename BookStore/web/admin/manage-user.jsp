<%-- 
    Document   : manage-user
    Created on : Oct 31, 2025, 6:25:26 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage User</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>
        <!-- MAIN BEGIN-->
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Users
            </div>

            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>

            <h1>All Users</h1>

            <div class="search-sort-container">
                <form method="post" action="users" >
                    <input type="text" name="search" placeholder="Search by name, email..." 
                           value="${search != null ? search : ''}" />

                    <select name="sort">
                        <option value="">Sort by</option>
                        <option value="fullname_asc" ${sort == 'fullname_asc' ? 'selected' : ''}>Fullname A-Z</option>
                        <option value="fullname_desc" ${sort == 'fullname_desc' ? 'selected' : ''}>Fullname Z-A</option>
                        <option value="username_asc" ${sort == 'username_asc' ? 'selected' : ''}>Username A-Z</option>
                        <option value="username_desc" ${sort == 'username_desc' ? 'selected' : ''}>Username Z-A</option>
                        <option value="email_asc" ${sort == 'email_asc' ? 'selected' : ''}>Email A-Z</option>
                        <option value="email_desc" ${sort == 'email_desc' ? 'selected' : ''}>Email Z-A</option>
                        <option value="role_asc" ${sort == 'role_asc' ? 'selected' : ''}>Role A-Z</option>
                        <option value="role_desc" ${sort == 'role_desc' ? 'selected' : ''}>Role Z-A</option>
                        <option value="registered_asc" ${sort == 'registered_asc' ? 'selected' : ''}>Registered Oldest</option>
                        <option value="registered_desc" ${sort == 'registered_desc' ? 'selected' : ''}>Registered Newest</option>
                    </select>

                    <button type="submit" class="add-btn"><i class="fa fa-search"></i> Search</button>
                </form>
            </div>

            <div>             
                <a href="edituser" class="add-btn">+ Add user</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>FULLNAME</th>
                        <th>USERNAME</th>
                        <th>EMAIL</th>
                        <th>ROLE</th>
                        <th>REGISTERED</th>  
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="u" varStatus="status">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>
                                <div class="product-name">${u.fullname}</div>
                                <div class="subtext"></div>
                            </td>   
                            <td>${u.userName}</td>
                            <td>${u.email}</td>
                            <td>${u.role}</td>
                            <td>
                                <fmt:formatDate value="${u.createdAt}" pattern="dd/MM/yyyy" />
                            </td>
                            <td>
                                <a href="edituser?type=edit&username=${u.userName}" class="btn btn-edit">
                                    <i class="fa-solid fa-pen"></i> Edit
                                </a>
                                <a href="delete?type=user&username=${u.userName}" class="btn btn-delete"
                                   data-title="${fn:escapeXml(u.userName)}" 
                                   onclick="return xacNhan(this)">
                                    <i class="fa-solid fa-trash"></i> Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- MAIN END-->

        <script>
            function xacNhan(el) {
                var title = el.getAttribute('data-title');
                return confirm("Do you really want to delete this user: " + title + "?");
            }
        </script>
    </body>
</html>
