function logIn() {
    var form = $("#login-form");
    var login = form.find('input[name="login"]').val();
    var password = form.find('input[name="password"]').val();

    $.ajax({
        type: 'POST',
        url: '/login',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        data: {
            login: login,
            password: password
        },
        success: function (data) {
            console.log("Login success! " + JSON.stringify(data));
            if (data.status == "success") {
                location = "/";
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
    var lastName = form.find('input[name="lastName"]').val();
    var firstName = form.find('input[name="firstName"]').val();
    var patronymicName = form.find('input[name="patronymicName"]').val();
    var login = form.find('input[name="login"]').val();
    var password = form.find('input[name="password"]').val();

    $.ajax({
        type: 'POST',
        url: '/register',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        processData: false,
        contentType: 'application/json',
        data: JSON.stringify({
            lastName: lastName,
            firstName: firstName,
            patronymicName: patronymicName,
            login: login,
            password: password
        }),
        success: function (data) {
            var alert;
            console.log(data.status);
            console.log(data.message);
            if (data.status == 'success') {
                console.log("Registration success! " + JSON.stringify(data));
                // location.reload();
            } else {
                console.log("Registration error! " + JSON.stringify(data));
                alert = $('<div id="registration-header-alert" class="alert alert-danger" role="alert">' +
                    data.message + '</div>');
                $("#registration-header-alert").replaceWith(alert);
            }

        },
        error: function (data) {
            console.error("Registration error! " + JSON.stringify(data));
            var alert = $('<div id="registration-header-alert" class="alert alert-danger" role="alert">' +
                "Registration failed</div>");
            $("#registration-header-alert").replaceWith(alert);
        }
    });
}