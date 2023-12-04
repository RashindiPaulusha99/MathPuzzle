$("#welcomeSection").css('display','block');
$("#loginSection").css('display','none');
$("#registerSection").css('display','none');
$("#gameStartSection").css('display','none');
$("#playSection").css('display','none');
$("#profileSection").css('display','none');
$("#btnLeftBack").css('display','none');
$("#model_container").css('display','none');
$("#successAlertSection").css('display','none');
$("#warningAlertSection").css('display','none');
$("#errorAlertSection").css('display','none');

$(".try_again_container").css('display', 'none');
$("#win_container").css('display', 'none');

$("#btnLogin").click(function () {
    $("#loginSection").css('display','block');
    $("#registerSection").css('display','none');
    $("#gameStartSection").css('display','none');
    $("#playSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#welcomeSection").css('display','block');
    $("#btnLeftBack").css('display','block');
    $("#model_container").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#btnRegister").click(function () {
    $("#loginSection").css('display','none');
    $("#registerSection").css('display','block');
    $("#playSection").css('display','none');
    $("#gameStartSection").css('display','none');
    $("#welcomeSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#btnLeftBack").css('display','none');
    $("#model_container").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$(".btnLeftBack").click(function () {
    $("#loginSection").css('display','none');
    $("#registerSection").css('display','none');
    $("#playSection").css('display','none');
    $("#gameStartSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#btnLeftBack").css('display','none');
    $("#welcomeSection").css('display','block');
    $("#model_container").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#btnSignUp-login").click(function () {
    $("#loginSection").css('display','none');
    $("#registerSection").css('display','block');
    $("#playSection").css('display','none');
    $("#gameStartSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#btnLeftBack").css('display','block');
    $("#welcomeSection").css('display','none');
    $("#model_container").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#btnSignIn-register").click(function () {
    $("#loginSection").css('display','block');
    $("#registerSection").css('display','none');
    $("#playSection").css('display','none');
    $("#gameStartSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#btnLeftBack").css('display','block');
    $("#welcomeSection").css('display','none');
    $("#model_container").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#btnPlay").click(function () {
    $("#loginSection").css('display','none');
    $("#registerSection").css('display','none');
    $("#gameStartSection").css('display','none');
    $("#playSection").css('display','block');
    $("#welcomeSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#btnLeftBack").css('display','block');
    $("#winSection").css('display','none');
    $('#win_container').css('display','none');
    $("#model_container").css('display','none');
    $('.try_again_container').css('transform', 'scale(0)');
    $('.try_again_container').css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#backToStart").click(function () {
    $("#loginSection").css('display','none');
    $("#registerSection").css('display','none');
    $("#gameStartSection").css('display','block');
    $("#playSection").css('display','none');
    $("#welcomeSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#btnLeftBack").css('display','block');
    $('#win_container').css('display','none');
    $("#model_container").css('display','none');
    $('.try_again_container').css('transform', 'scale(0)');
    $('.try_again_container').css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#backToStartFromWin").click(function () {
    $("#loginSection").css('display','none');
    $("#registerSection").css('display','none');
    $("#gameStartSection").css('display','block');
    $("#playSection").css('display','none');
    $("#profileSection").css('display','none');
    $("#welcomeSection").css('display','none');
    $("#btnLeftBack").css('display','block');
    $('#win_container').css('display','none');
    $("#model_container").css('display','none');
    $('.try_again_container').css('transform', 'scale(0)');
    $('.try_again_container').css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});


