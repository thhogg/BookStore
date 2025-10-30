<%-- 
    Document   : add-book
    Created on : Oct 30, 2025, 11:01:13 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">
        <link rel="stylesheet" href="css/add-book.css"/>
    </head>
    <body>
        <%@include file="includes/sidebar_header.jsp" %>

        <div class="main">

            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Books
            </div>
            <div class="form-container">
                <h2>Add new book</h2>
                <form id="addBookForm" action="add" method="post" enctype="multipart/form-data">

                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title" required>
                    </div>

                    <div class="form-group">
                        <label for="authorId">Author</label>
                        <input type="text" id="authorId" name="author">
                    </div>

                    <div class="form-group">
                        <label for="publisherId">Publisher</label>
                        <input type="text" id="publisher" name="publisher">
                    </div>

                    <div class="form-group">
                        <label for="categoryId">Category</label>
                        <select name="category">
                            <c:forEach items="${categories}" var="c">
                                <option value="${c.categoryID}">${c.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="isbn">ISBN</label>
                        <input type="text" id="isbn" name="isbn">
                    </div>

                    <div class="form-group">
                        <label for="price">Price</label>
                        <input type="number" id="price" name="price" min="0">
                    </div>

                    <div class="form-group">
                        <label for="stock">Stock</label>
                        <input type="number" id="stock" name="stock" min="0">
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="imageUrl">Image</label>
                        <input type="file" id="imageUrl" name="imageUrl" accept="image/*">
                        <div class="preview" id="preview"></div>
                    </div>
                    <button type="submit" class="btn">Add book</button>
                </form>
            </div>
        </div>

        <script>
            // Preview ảnh bìa trước khi upload
            const imageInput = document.getElementById('imageUrl');
            const preview = document.getElementById('preview');

            imageInput.addEventListener('change', function () {
                preview.innerHTML = '';
                const file = this.files[0];
                if (file) {
                    const img = document.createElement('img');
                    img.src = URL.createObjectURL(file);
                    preview.appendChild(img);
                }
            });
        </script>
    </body>
</html>
