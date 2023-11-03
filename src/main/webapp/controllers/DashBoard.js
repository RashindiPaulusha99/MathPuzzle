$("#welcomeSection").css('display','block');
$("#loginSection").css('display','none');
$("#btnLeftBack").css('display','none');

$("#btnLogin").click(function () {
    $("#loginSection").css('display','block');
    $("#welcomeSection").css('display','none');
    $("#btnLeftBack").css('display','block');
});

$("#btnLeftBack").click(function () {
    $("#loginSection").css('display','none');
    $("#btnLeftBack").css('display','none');
    $("#welcomeSection").css('display','block');
});
