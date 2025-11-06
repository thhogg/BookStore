<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css">
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
    <style>
        body { background: #f8f9fa; }
        #logreg-forms {
            max-width: 400px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }
        .form-signup { display: none; }
        .form-control { border-radius: 25px; padding: 10px 20px; margin-bottom: 15px; }
        .btn { border-radius: 25px; padding: 10px 20px; }
    </style>
</head>
<body>
<div id="logreg-forms">

    <c:if test="${not empty message}">
        <p class="text-danger text-center"><i class="fas fa-exclamation-circle"></i> ${message}</p>
    </c:if>

    <!-- FORM ĐĂNG NHẬP -->
    <form class="form-signin" action="login" method="post">
        <h1 class="h3 mb-3 font-weight-normal text-center">Sign in</h1>

        <input name="username" type="text" class="form-control" placeholder="Username" 
               value="${username}" required autofocus>
        <input name="password" type="password" class="form-control" placeholder="Password" 
               value="${password}" required>

        <div class="form-group form-check">
            <input name="remember" value="1" type="checkbox" class="form-check-input" 
                   id="remember" ${remember == '1' ? 'checked' : ''}>
            <label class="form-check-label" for="remember">Remember password</label>
        </div>

        <button class="btn btn-success btn-block" type="submit">
            <i class="fas fa-sign-in-alt"></i> Sign in
        </button>
        <hr>
        <button type="button" class="btn btn-primary btn-block" id="btn-signup">
            <i class="fas fa-user-plus"></i> Sign up New Account
        </button>
    </form>

    <!-- FORM ĐĂNG KÝ -->
    <form action="signup" method="post" class="form-signup">
        <h1 class="h3 mb-3 font-weight-normal text-center">Sign up</h1>

        <input name="username" type="text" class="form-control" placeholder="Username" required>
        <input name="password" type="password" class="form-control" placeholder="Password" required>
        <input name="repass" type="password" class="form-control" placeholder="Repeat Password" required>
        <input name="fullname" type="text" class="form-control" placeholder="Full Name">
        <input name="email" type="email" class="form-control" placeholder="Email">
        <input name="phone" type="text" class="form-control" placeholder="Phone">
        <input name="address" type="text" class="form-control" placeholder="Address">

        <button class="btn btn-primary btn-block" type="submit">
            <i class="fas fa-user-plus"></i> Sign Up
        </button>
        <a href="#" id="cancel_signup" class="btn btn-link">
            <i class="fas fa-angle-left"></i> Back
        </a>
    </form>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script>
    $(() => {
        $('#btn-signup').click(() => {
            $('.form-signin').hide();
            $('.form-signup').show();
        });
        $('#cancel_signup').click(() => {
            $('.form-signup').hide();
            $('.form-signin').show();
        });
    });
</script>
</body>
</html>