$("#welcomeSection").css('display','block');
$("#loginSection").css('display','none');
$("#registerSection").css('display','none');
$("#gameSection").css('display','none');
$("#btnLeftBack").css('display','none');
$("#successAlertSection").css('display','none');
$("#warningAlertSection").css('display','none');
$("#errorAlertSection").css('display','none');

$("#btnLogin").click(function () {
    $("#loginSection").css('display','block');
    $("#registerSection").css('display','none');
    $("#gameSection").css('display','none');
    $("#welcomeSection").css('display','none');
    $("#btnLeftBack").css('display','block');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#btnRegister").click(function () {
    $("#loginSection").css('display','none');
    $("#gameSection").css('display','none');
    $("#registerSection").css('display','block');
    $("#welcomeSection").css('display','none');
    $("#btnLeftBack").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$(".btnLeftBack").click(function () {
    $("#loginSection").css('display','none');
    $("#registerSection").css('display','none');
    $("#gameSection").css('display','none');
    $("#btnLeftBack").css('display','none');
    $("#welcomeSection").css('display','block');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#btnSignUp-login").click(function () {
    $("#loginSection").css('display','none');
    $("#gameSection").css('display','none');
    $("#registerSection").css('display','block');
    $("#btnLeftBack").css('display','block');
    $("#welcomeSection").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});

$("#btnSignIn-register").click(function () {
    $("#loginSection").css('display','block');
    $("#gameSection").css('display','none');
    $("#registerSection").css('display','none');
    $("#btnLeftBack").css('display','block');
    $("#welcomeSection").css('display','none');
    $("#successAlertSection").css('display','none');
    $("#warningAlertSection").css('display','none');
    $("#errorAlertSection").css('display','none');
});
