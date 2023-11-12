
var question = null;
var correctAnswer = 0;
var score = 30;
var chances = 3;
var gameId = null;

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
                question = response.body.question;
                correctAnswer = response.body.solution;

            }else{

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });
});

$("#btnA1,#btnA2,#btnA3,#btnA4,#btnA5,#btnA6,#btnA7,#btnA8,#btnA9,#btnA10").on('click', function() {

    var buttonText = $(this).text();

    if (chances != 0){

        if (buttonText == correctAnswer){
            $('.win_container').css('transform', 'scale(1)');
            $('.win_container').css('display', 'block');

            chances = chances - 1;

            var scoreWinDetail = {
                "question_link":question,
                "answer":correctAnswer,
                "level":parseInt($("#display_level_header_count").text()),
                "is_correct":true,
                "score":score
            }

            saveScoreLevel(scoreWinDetail);
            $("#lives").text(chances);
            $("#livesforTry").text("+ "+chances);

        }else {
            $('.try_again_container').css('transform', 'scale(1)');
            $('.try_again_container').css('display', 'block');

            chances = chances - 1;
            score = score - 5;

            var scoreDetail = {
                "id":gameId,
                "question_link":question,
                "answer":correctAnswer,
                "level":parseInt($("#display_level_header_count").text()),
                "is_correct":false,
                "score":score
            }

            saveScoreLevel(scoreDetail);
            $("#lives").text(chances);
            $("#livesforTry").text("+ "+chances);
        }
    }else {
        $('#model_container').css('display', 'block');
        $('#playSection').css('display', 'none');

        var scoreFailDetail = {
            "id":gameId,
            "question_link":question,
            "answer":correctAnswer,
            "level":parseInt($("#display_level_header_count").text()),
            "is_correct":false,
            "score":score
        }

        saveScoreLevel(scoreFailDetail);
        $("#lives").text(chances);
        $("#livesforTry").text("+ "+chances);
        $("#fullScoreForFail").text(score);
        $("#levelforFail").text(parseInt($("#display_level_header_count").text()));

        question = null;
        correctAnswer = 0;
        score = 30;
        chances = 3;
        gameId = null;
    }


});

function saveScoreLevel(scoreDetail){

    $.ajax({
        url: "http://localhost:8080/v1/game/save/scoreLevel",
        method: "POST",
        crossDomain: true,
        contentType: "application/json",
        data: JSON.stringify(scoreDetail),
        success: function (response) {
            console.log(response)
            if (response.success == true) {

                console.log(response)
                gameId = response.data.id;

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });
}

$("#close_win_model").on('click',function () {
    $('.win_container').css('transform','scale(0)');
    $(".win_container").css('display', 'none');
    $("#gameStartSection").css('display', 'block');
    $("#playSection").css('display', 'none');
    $(".spend_time").text(0);
});

$("#close_over_model").on('click',function () {
    $("#model_container").css('display', 'none');
    $("#playSection").css('display', 'block');
    $(".spend_time").text(0);
});

$("#btnOk").on('click',function () {
    $('.model_container').css('transform','scale(0)');
    $(".model_container").css('display', 'none');
    $(".spend_time").text(0);
});

$("#close").on('click',function () {
    $(".spend_time").text(0);
    $('.model_container').css('transform', 'scale(0)');
    $(".model_container").css('display', 'none');
});

$("#btnOkInWin").on('click',function () {
    $('.win_container').css('transform', 'scale(0)');
    $(".win_container").css('display', 'none');
    $("#gameStartSection").css('display', 'block');
    $("#playSection").css('display', 'none');
    $("").css('display', 'none');
    $(".spend_time").text(0);
});

$("#closeInWin").on('click',function () {
    $(".spend_time").text(0);
    $('.win_container').css('transform', 'scale(0)');
    $(".win_container").css('display', 'none');
});

$("#close_try_model").on('click',function () {
    $(".spend_time").text(0);
    $('.try_again_container').css('transform', 'scale(0)');
    $(".try_again_container").css('display', 'none');
});

$("#btnReplay").on('click',function () {
    $(".spend_time").text(0);
    $('.try_again_container').css('transform', 'scale(0)');
    $(".try_again_container").css('display', 'none');
    $("#playSection").css('display', 'block');
});

