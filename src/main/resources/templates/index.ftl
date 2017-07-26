<!DOCTYPE html>
<html lang="en">
<head>
    <title>PhoneBook</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/typeahead.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.1/jquery.twbsPagination.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/typeahead.js/0.11.1/typeahead.bundle.min.js"></script>

    <script src="/js/index.js"></script>
</head>

<body>
<div class="container">
<#include "resources/navbar.ftl"/>
    <div class="row" >
        <div class=" col-sm-3 col-sm-offset-1">
            <div class="input-group">
                <button id="add-contact-button" type="button"
                        class="btn btn-default"><span class="glyphicon glyphicon-plus"></span>New contact</button>
                </span>
            </div>
            <div class="list-group" id="contacts-list"></div>
        </div>
        <div class="col-sm-7 hidden" id="contact-data-editor">

            <div class="row">
                <div class="col-sm-12" id="contact-data">
                    <div class="form-group">
                        <div id="new-contact-alert-place"></div>
                        <label for="contact-last-name">Last name*</label>
                        <input type="text" class="form-control" name="contact-last-name" placeholder="Last name"
                               id="contact-last-name-input">
                        <label for="contact-first-name">First name*</label>
                        <input type="text" class="form-control" name="contact-first-name" placeholder="First name"
                               id="contact-first-name-input">
                        <label for="contact-patronymic-name">Patronymic name*</label>
                        <input type="text" class="form-control" name="contact-patronymic-name" placeholder="Patronymic name"
                               id="contact-patronymic-name-input">
                        <label for="contact-mobile-phone">Mobile phone*</label>
                        <input type="text" class="form-control" name="contact-mobile-phone" placeholder="Mobile phone"
                               id="contact-mobile-phone-input">
                        <label for="contact-home-phone">Home phone</label>
                        <input type="text" class="form-control" name="contact-home-phone" placeholder="Home phone"
                               id="contact-home-phone-input">

                        <label for="contact-email">Email</label>
                        <input type="email" class="form-control" name="contact-email" placeholder="Email"
                               id="contact-email-input">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12" style="margin-top: 10px;">
                    <div class="col-xs-12 text-center">
                        <div class="form-group">
                            <a class="btn btn-success" onclick="saveNewContact()">
                                <span class="glyphicon glyphicon-floppy-disk"></span>Save
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="text-center">
                <ul id="pagination" class="pagination-xs"></ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>