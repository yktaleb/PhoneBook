var contactsCache;
var selected;
var selectedItem;

function setupAddProductTypeButtonClickEvent() {
    $('#add-contact-button').click(function () {
        var contactsList = $("#contacts-list");
        contactsList.find("a").removeClass("active");
        $("#contact-data-editor").removeClass("hidden");
        $("#button-update").addClass("hidden");
        $("#button-delete").addClass("hidden");
        $("#button-save").removeClass("hidden");
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
                displayAllContacts();
                $("#contact-data-editor").addClass("hidden");
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

function deleteContact() {
    $.ajax({
        type: 'POST',
        url: '/api/contact/delete',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        data: {
            contactId: contactsCache[selected].contactId
        },
        success: function (data) {
            var alert;
            if (data.status == 'success') {
                displayAllContacts();
                $("#contact-data-editor").addClass("hidden");

            } else {
                console.log("Error! " + JSON.stringify(data));
                alert = $('<div id="new-contact-alert-place" class="alert alert-danger" role="alert">' +
                    "error" + '</div>');
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

function updateContact() {
    var contact = {};
    contact.contactId = contactsCache[selected].contactId;
    contact.lastName = $("#contact-last-name-input").val();
    contact.firstName = $("#contact-first-name-input").val();
    contact.patronymicName = $("#contact-patronymic-name-input").val();
    contact.mobilePhone = $("#contact-mobile-phone-input").val();
    contact.homePhone = $("#contact-home-phone-input").val();

    contact.email = $("#contact-email-input").val();

    $.ajax({
        type: 'POST',
        url: '/api/contact/update',
        headers: {
            'X-CSRF-TOKEN': $('meta[name=_csrf]').attr("content")
        },
        processData: false,
        contentType: 'application/json',
        data: JSON.stringify(contact),
        success: function (data) {
            var alert;
            if (data.status == 'success') {
                displayAllContacts();
                $("#contact-data-editor").addClass("hidden");
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
            $("#sort-type-select").val("0");
            addContactsToList(data);
        },
        error: function (data) {
            console.log("Error");
        }
    });

}

function addContactsToList(allContacts) {
    var contactsList = $("#contacts-list");
    contactsList.empty();

    contactsCache = allContacts;

    allContacts.reverse();
    allContacts.forEach(function (contact, index) {
        var ref = document.createElement("a");
        ref.appendChild(document.createTextNode(contact.lastName + " " +
            contact.firstName + " " + contact.patronymicName + " : " + contact.mobilePhone));
        ref.className = "list-group-item";
        ref.href = "#";
        ref.onclick = function () {
            selectItem(index);
        };

        contactsList.prepend(ref);
    });
}

function sortContacts() {
    var id = $("#sort-type-select").val();

    if (id == 1) {
        sortByLastName();
    } else if (id == 2) {
        sortByFirstName();
    } else if (id == 3) {
        sortByMobilePhone();
    }
}

function sortByLastName() {
    $.ajax({
        url: '/api/contact/getSort/lastName',
        success: function (data) {
            contactsCache = null;
            addContactsToList(data);
        },
        error: function (data) {
            console.log("error");
        }
    });
}
function sortByFirstName() {
    $.ajax({
        url: '/api/contact/getSort/firstName',
        success: function (data) {
            contactsCache = null;
            addContactsToList(data);
        },
        error: function (data) {
            console.log("error");
        }
    });
}

function sortByMobilePhone() {
    $.ajax({
        url: '/api/contact/getSort/mobilePhone',
        success: function (data) {
            contactsCache = null;
            addContactsToList(data);
        },
        error: function (data) {
            console.log("error");
        }
    });
}

function selectItem(index) {
    selectedItem = contactsCache.length - index;

    $("#button-update").removeClass("hidden");
    $("#button-delete").removeClass("hidden");
    $("#button-save").addClass("hidden");

    var contactsList = $("#contacts-list");
    $("#contact-data-editor").removeClass("hidden");
    contactsList.find("a").removeClass("active");

    contactsList.find("a:nth-child(" + (selectedItem) + ")").addClass("active");
    selected = index;

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

