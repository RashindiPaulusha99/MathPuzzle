$(document).ready(function () {
    // Add change event handler to the file input
    $('#file-input').on('change', function () {
        displayImagePreview(this);
    });

    function displayImagePreview(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#profileLogo').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
            saveProfileImage(input.files[0]);
        }

    }
});

function saveProfileImage(image) {

    const formData = new FormData();
    formData.append('image', image);

    $.ajax({
        url: "http://localhost:8080/v1/player/update/image",
        method: "POST",
        crossDomain: true,
        contentType: false,
        processData: false,
        data:formData,
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken")
        },
        success: function (response) {
            console.log(response)
            if (response.success == true) {

            }else if (response.success == false){

            }
        },
        error: function (ob, statusText, error) {
            console.log(ob.responseJSON.message)
        }
    });

}
