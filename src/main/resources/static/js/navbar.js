function displayAnonymousNavigationBar() {
    $("#navbar-login-button").removeClass("hidden");
    $("#navbar-registration-button").removeClass("hidden");
    $("#navbar-account-button").addClass("hidden");
}

function displayAuthenticatedNavigationBar(data) {

    var account = $('#navbar-account-button');
    account.text("Welcome, " + data.name + "!");
    account.append($("<span class='caret'></span>"));

    var list = $("#navbar-account").find("ul");
    list.empty();

    data.profileLinks.forEach(function(item) {

            console.log("Adding " + item.name);

            var ref = document.createElement("a");
            ref.appendChild(document.createTextNode(item.name));
            ref.href = item.reference;
            var li = document.createElement("li");
            li.appendChild(ref);
            list.append(li);
    });

    console.log("Adding logout");
    var ref = document.createElement("a");
    ref.appendChild(document.createTextNode("Logout"));
    ref.onclick = logout;
    ref.href = "#";
    var li = document.createElement("li");
    li.appendChild(ref);
    list.append(li);

    account.removeClass("hidden");
    $("#navbar-login-button").addClass("hidden");
    $("#navbar-registration-button").addClass("hidden");


    $(document).trigger("account-loaded");
}

function getAccountInformation() {
    var _csrf = $('meta[name=_csrf]').attr("content");

    $.ajax({
        type: 'GET',
        url: "/api/user/account",
        headers: {
            'X-CSRF-TOKEN': _csrf
        },
        success: function (data) {
            console.log("Account information successfully fetched! " + JSON.stringify(data));
            if (data.authenticated == "true") {
                displayAuthenticatedNavigationBar(data);
            }  else {
                displayAnonymousNavigationBar();
            }
        },
        error: function(data) {
            console.error("Fetching information abound account failed! " + JSON.stringify(data));
        }
    });
}

function logout() {
    console.log("Logout");
    $.ajax({
        url: "/signout",
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        data: {
            currentURL : window.location.pathname.substr(1)
        },
        success: function (data) {
            signOut(data);
        },
        error: function (data) {
            signOut(data);
        }
    });
}

function signOut(data) {
    $.ajax({
        url: '/logout',
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        success: function () {
            location = data.redirect;
        },
        error: function () {
            console.error("Logout failure! " + JSON.stringify(data));
        }
    });
}



function logIn() {
    var form = $("#login-form");
    var login = form.find('input[name="login"]').val();
    var password = form.find('input[name="password"]').val();

    var _csrf = $('meta[name=_csrf]').attr("content");

    $.ajax({
        type: 'POST',
        url: '/login',
        headers: {
            'X-CSRF-TOKEN': _csrf
        },
        data: {
            login: login,
            password: password
        },
        success: function (data) {
            console.log("Login success! " + JSON.stringify(data));
            if (data.status == "success") {
                $('#login-modal').modal('toggle');
                location.reload();
            } else {
                var alert = $('<div id="login-header-alert" class="alert alert-danger" role="alert">' +
                    "Login failed</div>");
                $("#login-header-alert").replaceWith(alert);
            }
        },
        error: function (data) {
            console.error("Login failure! " + JSON.stringify(data));
        }
    });
}

function register() {
    var form = $("#registration-form");
    var firstName = form.find('input[name="firstName"]').val();
    var lastName = form.find('input[name="lastName"]').val();
    var email = form.find('input[name="email"]').val();
    var password = form.find('input[name="password"]').val();
    // var repeatPassword = form.find('input[name="repeatPassword"]').val();
    var phone = form.find('input[name="phone"]').val();

    var _csrf = $('meta[name=_csrf]').attr("content");

    $.ajax({
        type: 'POST',
        url: '/register',
        headers: {
            'X-CSRF-TOKEN': _csrf
        },
        processData: false,
        contentType: 'application/json',
        data: JSON.stringify({
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password,
            phoneNumber: phone
        }),
        success: function (data) {
            var alert;
            if (data.status == 'success') {
                console.log("Registration success! " + JSON.stringify(data));
                location.reload();
                // alert = $('<div id="registration-header-alert" class="alert alert-success" role="alert">' +
                //     data.message + "</div>");
            } else {
                console.log("Registration error! " + JSON.stringify(data));
                alert = $('<div id="registration-header-alert" class="alert alert-danger" role="alert">' +
                    data.message + '</div>');
            }

            $("#registration-header-alert").replaceWith(alert);
        },
        error: function (data) {
            console.error("Registration error! " + JSON.stringify(data));
            var alert = $('<div id="registration-header-alert" class="alert alert-danger" role="alert">' +
                "Registration failed</div>");
            $("#registration-header-alert").replaceWith(alert);
        }
    });
}


$(document).ready(function () {
    setNavigation();
    getAccountInformation();
});

function setNavigation() {
    var path = window.location.pathname;
    path = path.replace(/\/$/, "");
    path = decodeURIComponent(path);

    $("#navigation a").each(function () {
        var href = $(this).attr('href');
        if (path.substring(0, href.length) === href) {
            $(this).closest('li').addClass('active');
        }
    });
}