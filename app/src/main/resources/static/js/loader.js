/**
 * Extend Jquery with toggle functions
 */
$.fn.extend({
    toggleText: function (a, b) {
        return this.text(this.text() === b ? a : b);
    },
    toggleTextStartsWith: function (a, b) {
        return this.text(this.text().startsWith(b) ? a : b);
    },
    toggleAttr: function (name, a, b) {
        let value = $(this).attr(name) === b ? a : b;
        $(this).attr(name, value);
    },
    toggleVal: function (a, b) {
        let value = $(this).val();
        $(this).val(value === b ? a : b);
    },
    toggleHtml: function (a, b) {
        let html = $(this).html();
        $(this).html(html === b ? a : b);
    },
    toggleDisabled: function () {
        const disabled = "disabled";
        if ($(this).prop(disabled)) {
            $(this).prop(disabled, false)
        } else {
            $(this).prop(disabled, true);
        }
    }
});

/**
 * Loader
 *
 * Сработает, когда документ и все ресурсы загружены
 */
window.onload = function () {
    const box = $('#loader-box');
    $('#loader-item').fadeOut(1500);
    box.delay(350).fadeOut('slow');
    setTimeout(function () {
        box.remove()
    }, 10000);
};

/**
 * Loader
 */
$(function () {

    /**
     * Texts
     * @type {string}
     */
    const loopingText = "Looping...";
    const generateText = "Generate";
    const spinnerHtml = '<i class="fa fa-spinner fa-spin"></i>';

    /**
     * For double-click prevention
     * @type {boolean}
     */
    let dblClick = false;

    /**
     * Timer id
     */
    let timerId;

    /**
     * Prevent double click
     *
     * @returns {boolean}
     */
    function preventDblClick() {
        if (dblClick) return true;
        else {
            dblClick = true;
            return false;
        }
    }

    /**
     * Enable click
     */
    function enableClick() {
        setTimeout(function () {
            dblClick = false;
        }, 500);
    }

    /**
     * Get form data
     * @returns {{beneficiary: (*|string|jQuery), yearTo: (*|string|jQuery), count: (*|string|jQuery), yearFrom: (*|string|jQuery)}}
     */
    function getFormData() {
        // $("#add-form").serialize();
        const form = $("#add-form");
        return {
            beneficiary: form.find("#beneficiary").val(),
            yearFrom: form.find("#year-from").val(),
            yearTo: form.find("#year-to").val(),
            count: form.find("#count").val(),
        };
    }

    /**
     * Generate btn
     */
    $("#generate-btn").on("click", function () {
        if (preventDblClick()) return;

        const that = $(this);
        const data = getFormData(); //$("#add-form").serialize();

        function toggle() {
            that.toggleHtml(generateText, spinnerHtml);
            that.toggleDisabled();
            that.parent().parent().parent().find("#beneficiary").toggleDisabled();
            that.parent().parent().parent().find("#year-from").toggleDisabled();
            that.parent().parent().parent().find("#year-to").toggleDisabled();
            that.parent().parent().parent().find("#count").toggleDisabled();
            that.parent().parent().parent().find("#loop").toggleDisabled();
        }

        toggle();
        xhrRequest("/api/add", data, null, function () {
            toggle();
            enableClick();
        });

        // generate(function () {
        //     toggle();
        //     enableClick();
        // });
    });

    /**
     * Loop controls
     */
    $("#loop").on("click", function () {
        if (preventDblClick()) return;

        const that = $(this);
        const data = getFormData(); //$("#add-form").serialize();
        const loopLabelSelector = that.parent().find("#loop-label");
        const generateBtnSelector = that.parent().parent().find("#generate-btn");
        let isStop = that.attr("is-looping") === 'true';
        let count = 0;

        function toggle() {
            that.toggleAttr("is-looping", "true", "false");
            that.toggleVal("Start loop", "Stop loop");
            generateBtnSelector.toggleHtml(generateText, spinnerHtml);
            generateBtnSelector.toggleDisabled();
            loopLabelSelector.toggleTextStartsWith("Loop is off", loopingText);
            that.parent().parent().find("#beneficiary").toggleDisabled();
            that.parent().parent().find("#year-from").toggleDisabled();
            that.parent().parent().find("#year-to").toggleDisabled();
            that.parent().parent().find("#count").toggleDisabled();
        }

        if (isStop) {
            clearInterval(timerId);
            toggle();
            enableClick();
        } else {
            toggle();
            timerId = setInterval(function () {
                xhrRequest("/api/add", data, null, function () {
                    if (that.attr("is-looping") === 'true')
                        loopLabelSelector.text(loopingText + " (" + ++count + ")");
                });
                // generate(function () {
                //     if (that.attr("is-looping") === 'true')
                //         loopLabelSelector.text(loopingText + " (" + ++count + ")");
                // });
            }, 1000);
            enableClick();
        }
    });

    /**
     * Chart
     */
    (function () {
        Chart.defaults.global.animation.duration = 3000;
        const start = 2001;
        const end = 2022;
        const range = [...Array(end - start + 1).keys()].map(x => x + start);
        const ctx = document.getElementById('canvas-analytics').getContext('2d');
        const chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: range,
                datasets: [
                    dataset('Oleksii Ivanovskii', '#283646'),
                    dataset('Mykola Lapinko', '#282946'),
                    dataset('Kyrylo Kulach', '#194b31'),
                ]
            }
        });

        function dataset(name, color) {
            return {
                label: name,
                data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                fill: false,
                borderColor: color,
                backgraundColor: color,
                tension: 0.1,
                borderWidth: 1
            }
        }

        function ajax() {
            xhrRequest("/api/info", {}, function (response) {
                response = JSON.parse(response);
                for (let i = 0; i < chart.data.datasets.length; i++) {
                    chart.data.datasets[i].data = response.data[i + 1];
                }
                chart.update();
            });
        }

        ajax();
        setInterval(function () {
            ajax();
        }, 5000);
    })();

    /**
     * Datepicker
     */
    $(".datepicker").datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years",
        startDate: "2001",
        endDate: "2022",
        autoclose: true //to close picker once year is selected
    });
});
