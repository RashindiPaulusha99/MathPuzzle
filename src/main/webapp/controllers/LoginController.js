$("#btnSignUp-register").click(function () {

    if ($("#email-register").val() == "" || $("#password-register").val() == ""){
        $("#warningAlertSection").css('display','block');
        $("#warningAlert").text("All fields are required!");

        $(document).ready(function() {
            // Delay for 5 seconds and then fade out in 0.5 seconds
            $('#warningAlertSection').delay(5000).fadeOut(500);
        });
    }else {

        var regDetail = {
            email:$("#email-register").val(),
            password: $("#password-register").val()
        }

        $.ajax({
            url: "http://localhost:8080/v1/user/register/save",
            method: "POST",
            crossDomain: true,
            contentType: "application/json",
            data: JSON.stringify(regDetail),
            success: function (response) {
                console.log(response)
                if (response.success == true) {

                    $("#email-register").val('')
                    $("#password-register").val('')

                    $("#successAlertSection").css('display','block');
                    $("#successAlert").text("Successfully Registered!")

                    $(document).ready(function() {
                        // Delay for 5 seconds and then fade out in 0.5 seconds
                        $('#successAlertSection').delay(5000).fadeOut(500);
                    });

                    $("#loginSection").css('display','block');
                    $("#gameSection").css('display','none');
                    $("#registerSection").css('display','none');
                    $("#btnLeftBack").css('display','block');
                    $("#welcomeSection").css('display','none');

                }
            },
            error: function (ob, statusText, error) {
                alert(statusText);
            }
        });
    }
});

$("#btnSignIn-login").click(function () {

    if ($("#email-login").val() == "" || $("#password-login").val() == ""){
        $("#warningAlertSection").css('display','block');
        $("#warningAlert").text("All fields are required!");

        $(document).ready(function() {
            // Delay for 5 seconds and then fade out in 0.5 seconds
            $('#warningAlertSection').delay(5000).fadeOut(500);
        });
    }else {
        var logDetail = {
            email:$("#email-login").val(),
            password: $("#password-login").val()
        }

        $.ajax({
            url: "http://127.0.0.1:8080/login",
            method: "POST",
            crossDomain: true,
            contentType: "application/json",
            data: JSON.stringify(logDetail),
            success: function (response) {
                if (response.success == true) {

                    $("#email-login").val('')
                    $("#password-login").val('')

                    $("#loginSection").css('display','none');
                    $("#registerSection").css('display','none');
                    $("#btnLeftBack").css('display','none');
                    $("#welcomeSection").css('display','none');
                    $("#gameSection").css('display','block');

                }else if (response.status == 401){
                    alert(response.message)
                }else if (response.status == 400){
                    alert(response.message)
                }
            },
            error: function (ob, statusText, error) {
                alert(statusText);
            }
        });
    }
});