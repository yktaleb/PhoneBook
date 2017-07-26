function saveChange() {
    var user = {};
    user.lastName = $("#mf1").val();
    user.firstName = $("#mf2").val();
    user.patronymicName = $("#mf3").val();
    user.login = $("#mf4").val();
    $.ajax({
        type: 'POST',
        url: '/api/profile/update',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        processData: false,
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function (data) {
            var alert;
            if (data.status == 'success') {
                console.log("Update success! " + JSON.stringify(data));
                alert = $('<div id="update-profile-alert" class="alert alert-success" role="alert">' +
                    data.message + "</div>");
                var account = $('#navbar-account-button');
                account.text("Welcome, " + user.firstName + " " + user.lastName + "!");
            } else {
                console.log("Error! " + JSON.stringify(data));
                alert = $('<div id="update-profile-alert" class="alert alert-danger" role="alert">' +
                    data.message + '</div>');
            }
            $("#update-profile-alert").replaceWith(alert);
        },
        error: function (data) {
            console.error("Error" + JSON.stringify(data));
            var alert = $('<div id="update-profile-alert" class="alert alert-danger" role="alert">' +
                "Error</div>");
            $("#update-profile-alert").replaceWith(alert);
        }
    });
}

function changePassword() {
    var currentPassword = $("#currentPassword").val();
    var newPassword = $("#newPassword").val();
    $.ajax({
        type: 'POST',
        url: '/api/profile/changePassword',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        data: {
            currentPassword : currentPassword,
            newPassword : newPassword
        },
        success: function (data) {
            var alert;
            if (data.status == 'success') {
                console.log("Update success! " + JSON.stringify(data));
                alert = $('<div id="change-password-alert" class="alert alert-success" role="alert">' +
                    data.message + "</div>");
            } else {
                console.log("Error! " + JSON.stringify(data));
                alert = $('<div id="change-password-alert" class="alert alert-danger" role="alert">' +
                    data.message + '</div>');
            }
            $("#change-password-alert").replaceWith(alert);
        },
        error: function (data) {
            console.error("Error" + JSON.stringify(data));
            var alert = $('<div id="change-password-alert" class="alert alert-danger" role="alert">' +
                "Error </div>");
            $("#change-password-alert").replaceWith(alert);
        }
    });
}

function loadInfo() {
    $.ajax({
            url: '/api/profile/get',
            success: function (data) {
                showInfo(data);
            },
            error: function (data) {
                showInfo(data);
            }
        }
    );
}

function showInfo(data) {
    $("#mf1").val(data.lastName);
    $("#mf2").val(data.firstName);
    $("#mf3").val(data.patronymicName);
    $("#mf4").val(data.login);
}

$(document).ready(function () {
    loadInfo();
});

