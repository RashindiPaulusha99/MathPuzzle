$("#btnPlay").on('click', function() {

    $.ajax({
        url: "http://localhost:8080/v1/game/new",
        method: "GET",
        crossDomain: true,
        contentType: "application/json",
        success: function (response) {
            console.log(response)
            if (response.success == true) {

                $("#tomatoGame").attr("src", response.body.question);

            }else{

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });
});

$("#btnA1").on('click', function() {

    var buttonText = $(this).text();

    $.ajax({
        url: "http://localhost:8080/v1/game/new",
        method: "GET",
        crossDomain: true,
        contentType: "application/json",
        success: function (response) {
            console.log(response)
            if (response.success == true) {

                $("#winSection").css('display','block');
                $("#loginSection").css('display','none');
                $("#gameSection").css('display','none');
                $("#gameStartSection").css('display','none');
                $("#registerSection").css('display','none');
                $("#btnLeftBack").css('display','block');
                $("#welcomeSection").css('display','none');

            }else{

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });
});