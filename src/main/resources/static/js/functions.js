/**
 * Generate transaction ajax function
 */
function generate(onComplete) {
    $("#add-form").ajaxSubmit({
        url: '/api/add',
        type: 'post',
        // принудительно указываем заголовок Content-type для POST
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },
        success: function (response) {
        },
        complete: function () {
            // если это функция, то выполняем её
            if (typeof onComplete == 'function') onComplete();
        }
    });
}

/**
 * Ajax request
 */
function xhrRequest(url, data, onSuccess) {
    // аякс запрос
    $.ajax({
        type: 'post',
        url: url,
        data: data,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
        },
        success: function (response) {
            if (typeof onSuccess == 'function')
                onSuccess(response);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        }
    }).always(function (response) {
    });
}
