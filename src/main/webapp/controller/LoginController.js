$("#btnSignUp-register").click(function () {

    if ($("#email-register").val() == "" || $("#password-register").val() == "" || $("#username-register").val() == ""){
        $("#warningAlertSection").css('display','block');
        $("#warningAlert").text("All fields are required!");

        $(document).ready(function() {
            // Delay for 5 seconds and then fade out in 0.5 seconds
            $('#warningAlertSection').delay(5000).fadeOut(500);
        });
    }else {

        var regDetail = {
            email:$("#email-register").val(),
            password: $("#password-register").val(),
            username: $("#username-register").val(),
        }

        $.ajax({
            url: "http://localhost:8080/v1/player/register/save",
            method: "POST",
            crossDomain: true,
            contentType: "application/json",
            data: JSON.stringify(regDetail),
            success: function (response) {
                console.log(response)
                if (response.success == true) {

                    $("#email-register").val('')
                    $("#password-register").val('')
                    $("#username-register").val('')

                    $("#successAlertSection").css('display','block');
                    $("#successAlert").text("Successfully Registered!")

                    $(document).ready(function() {
                        // Delay for 5 seconds and then fade out in 0.5 seconds
                        $('#successAlertSection').delay(5000).fadeOut(500);
                    });

                    $("#loginSection").css('display','block');
                    $("#gameSection").css('display','none');
                    $("#gameStartSection").css('display','none');
                    $("#registerSection").css('display','none');
                    $("#btnLeftBack").css('display','block');
                    $("#welcomeSection").css('display','none');

                }else{
                    $("#errorAlertSection").css('display','block');
                    $("#errorAlert").text(response.message);

                    $(document).ready(function() {
                        // Delay for 5 seconds and then fade out in 0.5 seconds
                        $('#errorAlertSection').delay(5000).fadeOut(500);
                    });
                }
            },
            error: function (ob, statusText, error) {
                $("#errorAlertSection").css('display','block');
                $("#errorAlert").text(ob.responseJSON.message);

                $(document).ready(function() {
                    // Delay for 5 seconds and then fade out in 0.5 seconds
                    $('#errorAlertSection').delay(5000).fadeOut(500);
                });
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

        var auth = {
            username:"devglan-client",
            password: "devglan-secret",
        }

        $.ajax({
            url: "http://localhost:8080/oauth/token",
            method: "POST",
            crossDomain: true,
            contentType: "application/x-www-form-urlencoded", // Change content type to URL-encoded
            headers: {
                "Authorization": "Basic " + btoa(auth.username + ":" + auth.password)
            },
            data: {
                'username': $("#email-login").val(),
                'password': $("#password-login").val(),
                'grant_type': 'password'
            },
            success: function (response) {
                if (response.success == true) {

                    localStorage.setItem("authToken", response.access_token);
                    localStorage.setItem("playerId", response.user.id);

                    getLevelAndScore();

                    loadLeaderboard();

                    getUserDetails();

                    $("#email-login").val('')
                    $("#password-login").val('')

                    $("#successAlertSection").css('display','block');
                    $("#successAlert").text("Successfully Login!")

                    $(document).ready(function() {
                        // Delay for 5 seconds and then fade out in 0.5 seconds
                        $('#successAlertSection').delay(5000).fadeOut(500);
                    });

                    $("#loginSection").css('display','none');
                    $("#registerSection").css('display','none');
                    $("#btnLeftBack").css('display','none');
                    $("#welcomeSection").css('display','none');
                    $("#gameStartSection").css('display','block');
                    $("#gameSection").css('display','none');

                }else if (response.success == false){
                    $("#errorAlertSection").css('display','block');
                    $("#errorAlert").text(response.message);

                    $(document).ready(function() {
                        // Delay for 5 seconds and then fade out in 0.5 seconds
                        $('#errorAlertSection').delay(5000).fadeOut(500);
                    });
                }
            },
            error: function (ob, statusText, error) {
                $("#errorAlertSection").css('display','block');
                $("#errorAlert").text(ob.responseJSON.message);

                $(document).ready(function() {
                    // Delay for 5 seconds and then fade out in 0.5 seconds
                    $('#errorAlertSection').delay(5000).fadeOut(500);
                });
            }
        });
    }
});

function getLevelAndScore() {
    $.ajax({
        url: "http://localhost:8080/v1/game/get/start/scoreLevel",
        method: "GET",
        crossDomain: true,
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken")
        },
        success: function (response) {
            if (response.success == true) {

                $("#fullScoreCount").text(response.body.score);
                $("#fullScore").text(response.body.score);
                $("#fullRewardCount").text(response.body.reward);
                $(".levelCount").text(response.body.level+1);
                $("#display_level_header_count").text(response.body.level+1);
                $("#rewardCountPerLevel").text(response.body.level+1);

            }else if (response.success == false){

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });
}

function loadLeaderboard(){
    $("#tbody").empty();
    $.ajax({
        url: "http://localhost:8080/v1/game/get/leaderboard",
        method: "GET",
        crossDomain: true,
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken")
        },
        success: function (response) {
            if (response.success == true) {

                for (var bodyKey of response.body) {
                    let raw = `<tr><td>${bodyKey.rank}</td>
                        <td>${bodyKey.name}</td>
                        <td>${bodyKey.score}</td>
                        <td>${bodyKey.reward}</td>
                    </tr>`;
                    $("#tbody").append(raw);
                }
            }else{

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });
}

function getUserDetails() {
    $.ajax({
        url: "http://localhost:8080/v1/player/get/data",
        method: "GET",
        crossDomain: true,
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken")
        },
        success: function (response) {
            console.log(response.body)
            if (response.success == true) {

                $("#profileName").text(response.body.username);
                $("#profileEmail").text(response.body.email);

                console.log(response.body.image)
                if (response.body.image != null){
                    $("#profileLogo").attr('src', response.body.image);
                }else {
                    console.log("null")
                    $("#profileLogo").attr('src',"../assets/images/follow-5084833-4263863.webp");
                }

            }else if (response.success == false){

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });
}
