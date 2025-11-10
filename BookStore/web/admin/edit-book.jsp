<%-- 
    Document   : edit-book
    Created on : Oct 31, 2025, 11:20:58 AM
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
                Home <span>/</span> Admin <span>/</span> Books <span>/</span> Edit Book
            </div>
            
            <c:if test="${not empty successMessage}">
                <div class="message success">${successMessage}</div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="message error">${errorMessage}</div>
            </c:if>
            
            <div class="form-container">
                <h2>Edit book</h2>
                <form id="editBookForm" action="editbook" method="post" enctype="multipart/form-data">

                     <input type="hidden" name="bookId" value="${book.bookID}" />
                    
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title" value="${book.title}" required>
                    </div>

                    <div class="form-group">
                        <label for="authorId">Author</label>
                        <input type="text" id="authorId" name="author" value="${book.author}">
                    </div>

                    <div class="form-group">
                        <label for="publisherId">Publisher</label>
                        <input type="text" id="publisher" name="publisher" value="${book.publisher}">
                    </div>

                    <div class="form-group">
                        <label for="categoryId">Category</label>
                        <select name="category">
                            <c:forEach items="${categories}" var="c">
                                <option value="${c.categoryID}" ${book.categoryID==c.categoryID?"selected":""}>
                                    ${c.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="isbn">ISBN</label>
                        <input type="text" id="isbn" name="isbn" value="${book.isbn}">
                    </div>

                    <div class="form-group">
                        <label for="price">Price</label>
                        <input type="number" id="price" name="price" min="0" value="${book.price}">
                    </div>

                    <div class="form-group">
                        <label for="stock">Stock</label>
                        <input type="number" id="stock" name="stock" min="0" value="${book.stock}">
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description">${book.description}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="imageUrl">Image</label>
                        <input type="file" id="imageUrl" name="imageUrl" accept="image/*">
                        <div class="preview" id="preview">
                            <img src="../${book.imageUrl}" alt="book image">
                        </div>
                    </div>
                    <button type="submit" class="btn">Update book</button>
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

