<!DOCTYPE html>
<html lang="en">
<head>
    <title>PhoneBook</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="css/login.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="/js/login.js"></script>

</head>

<body>
<div class="container">

<#include "resources/navbar.ftl"/>

    <div class="main-text">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs">
            <li class="active">
                <a href="#s_in" class="btn btn-lg" aria-controls="s_in" role="tab" data-toggle="tab">Sign in</a>
            </li>
            <li>
                <a href="#s_up" class="btn btn-lg" aria-controls="s_up" role="tab" data-toggle="tab">Sign up</a>
            </li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="s_in">
                <form class="form-signin" id="login-form">
                    <div id="login-header-alert"></div>
                    <input type="test" class="form-control input-s" name="login" placeholder="Login" required
                           autofocus>
                    <input type="password" class="form-control input-s" name="password" placeholder="Password" required>
                    <button id="login-btn" class="btn btn-lg btn-success btn-block" type="button" onclick="logIn()">Sign in</button>
                </form>
            </div>
            <div role="tabpanel" class="tab-pane fade" id="s_up">
                <form class="form-signup" id="registration-form">
                    <input type="text" class="form-control input-s" name="lastName" placeholder="Last name" required autofocus>
                    <input type="text" class="form-control input-s" name="firstName" placeholder="First name" required>
                    <input type="text" class="form-control input-s" name="patronymicName" placeholder="Patronymic name" required>
                    <input type="email" id="inputEmail" class="form-control input-s" name="login" placeholder="Login" required>
                    <input type="password" class="form-control input-s" name="password" placeholder="Password" required>
                    <button id="register-btn" class="btn btn-lg btn-success btn-block" type="button" onclick="register()">Sign up</button>
                </form>
            </div>
        </div>
    </div><!-- /.main-text -->

</div><!-- /.container -->


</body>
</html>