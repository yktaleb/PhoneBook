function setupAddProductTypeButtonClickEvent() {
    $('#add-contact-button').click(function () {
        $("#contact-data-editor").removeClass("hidden");
    })
}

function saveNewContact() {
    var allContacts = $("#contacts-list");

    var contact = {};
    contact.lastName = $("#contact-last-name-input").val();
    contact.firstName = $("#contact-first-name-input").val();
    contact.patronymicName = $("#contact-patronymic-name-input").val();
    contact.mobilePhone = $("#contact-mobile-phone-input").val();
    contact.homePhone = $("#contact-home-phone-input").val();

    contact.email = $("#contact-email-input").val();

    $.ajax({
        type: 'POST',
        url: '/api/contact/add',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        processData: false,
        contentType: 'application/json',
        data: JSON.stringify(contact),
        success: function (data) {
            var alert;
            if (data.status == 'success') {
                console.log("Update success! " + JSON.stringify(data));
                alert = $('<div id="new-contact-alert-place" class="alert alert-success" role="alert">' +
                    data.message + "</div>");
                location.reload();
            } else {
                console.log("Error! " + JSON.stringify(data));
                alert = $('<div id="new-contact-alert-place" class="alert alert-danger" role="alert">' +
                    data.message + '</div>');
            }
            $("#new-contact-alert-place").replaceWith(alert);
        },
        error: function (data) {
            console.error("Error" + JSON.stringify(data));
            var alert = $('<div id="new-contact-alert-place" class="alert alert-danger" role="alert">' +
                "Error</div>");
            $("#new-contact-alert-place").replaceWith(alert);
        }
    });
}

function displayAllContacts() {
    $.ajax({
        url: '/api/contact/all',
        success: function (data) {
            addContactsToList(data);
        },
        error: function (data) {
            addContactsToList(data);
        }
    });

}

function addContactsToList(allContacts) {
    var contactsList = $("#contacts-list");
    contactsList.empty();

    allContacts.reverse();
    allContacts.forEach(function (contact) {
        var ref = document.createElement("a");
        ref.appendChild(document.createTextNode(contact.firstName));
        ref.className = "list-group-item";
        ref.href = "#";
        // ref.onclick = function () {
        //     selectItem(index);
        // };

        contactsList.prepend(ref);
    });
}

$(document).ready(function () {
    displayAllContacts();
    setupAddProductTypeButtonClickEvent();
});

