function displayAnonymousNavigationBar() {
    $("#navbar-account-button").addClass("hidden");
    $("#navbar-contacts-button").addClass("hidden");
}

function displayAuthenticatedNavigationBar(data) {

    $("#navbar-contacts-button").removeClass("hidden");

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
}

function getAccountInformation() {
    $.ajax({
        type: 'GET',
        url: "/api/user/account",
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
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

$(document).ready(function () {
    getAccountInformation();
});
