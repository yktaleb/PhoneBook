var contactsCache;

function setupAddProductTypeButtonClickEvent() {
    $('#add-contact-button').click(function () {
        var contactsList = $("#contacts-list");
        contactsList.find("a").removeClass("active");
        $("#contact-data-editor").removeClass("hidden");
        $("#contact-last-name-input").val("");
        $("#contact-first-name-input").val("");
        $("#contact-patronymic-name-input").val("");
        $("#contact-mobile-phone-input").val("");
        $("#contact-home-phone-input").val("");
        $("#contact-email-input").val("");
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

    contactsCache = allContacts;
    console.log(contactsCache);

    allContacts.reverse();
    allContacts.forEach(function (contact, index) {
        var ref = document.createElement("a");
        ref.appendChild(document.createTextNode(contact.lastName + " " + contact.firstName + " : " + contact.mobilePhone));
        ref.className = "list-group-item";
        ref.href = "#";
        ref.onclick = function () {
            selectItem(index);
        };

        contactsList.prepend(ref);
    });
}

function selectItem(index) {
    var selected = contactsCache.length - index;

    var contactsList = $("#contacts-list");
    $("#contact-data-editor").removeClass("hidden");
    contactsList.find("a").removeClass("active");

    contactsList.find("a:nth-child(" + (selected) + ")").addClass("active");

    $("#contact-last-name-input").val(contactsCache[index].lastName);
    $("#contact-first-name-input").val(contactsCache[index].firstName);
    $("#contact-patronymic-name-input").val(contactsCache[index].patronymicName);
    $("#contact-mobile-phone-input").val(contactsCache[index].mobilePhone);
    $("#contact-home-phone-input").val(contactsCache[index].homePhone);
    $("#contact-email-input").val(contactsCache[index].email);

}


$(document).ready(function () {
    displayAllContacts();
    setupAddProductTypeButtonClickEvent();
});

