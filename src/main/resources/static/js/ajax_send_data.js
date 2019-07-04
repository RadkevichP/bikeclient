function ajaxSendData(url, transferData) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        url: url,
        data: JSON.stringify(transferData),
        success: function (data) {
            console.log("Success: ", JSON.stringify(data));
            window.location.href = data.url;
        },
        error: function (e) {
            console.log("Error! ", e);
        }
    });
}