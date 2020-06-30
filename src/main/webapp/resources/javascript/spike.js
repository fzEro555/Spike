var spike = {
    // Encapsulate spike related ajax url
    URL: {
        now: function () {
            return "/spike/time/now";
        },
        exposer: function (spikeId) {
            return "/spike/" + spikeId + "/exposer";
        },
        execution: function (spikeId, md5) {
            return "/spike/" + spikeId + "/" + md5 + "/execution";
        }
    },
    // Validate phone number
    validatePhone: function (phone) {
        return !!(phone && phone.length === 10 && !isNaN(phone));
    },
    // Detail page logic
    detail: {
        // Initialize detail page
        init: function (params) {
            var userPhone = $.cookie('userPhone');
            if (!spike.validatePhone(userPhone)) {
                console.log("No phone number detected")
                var userPhoneModal = $("#userPhoneModal");
                userPhoneModal.modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });

                $("#userPhoneBtn").click(function () {
                    console.log("Submit phone number button has been clicked");
                    var inputPhone = $("#userPhoneKey").val();
                    console.log("inputPhone" + inputPhone);
                    if (spike.validatePhone(inputPhone)) {
                        // Write phone number into cookie
                        $.cookie('userPhone', inputPhone, {expires: 7, path: '/spike'});
                        console.log($.cookie('userPhone'));
                        window.location.reload();
                    } else {
                        $("#userPhoneMessage").hide().html("<label class='label label-danger'>Wrong phone number</label>").show(300);
                    }
                });
            } else {
                console.log("Find phone number in cookie，start timing");
                var startTime = params['startTime'];
                var endTime = params['endTime'];
                var spikeId = params['spikeId'];
                console.log("Start time=======" + startTime);
                console.log("End   time=======" + endTime);
                $.get(spike.URL.now(), {}, function (result) {
                    if (result && result['success']) {
                        var currentTime = result['data'];
                        console.log("Server local time=========" + currentTime);
                        spike.countDown(spikeId, currentTime, startTime, endTime);
                    } else {
                        console.log('result' + result);
                    }
                });
            }

        }
    },
    handlerSpike: function (spikeId, mode) {
        // Get spike url
        mode.hide().html('<button class="btn btn-primary btn-lg" id="spikeBtn">Start spike</button>');
        console.debug("Start get spike url");
        $.get(spike.URL.exposer(spikeId), {}, function (result) {
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    console.log("Have a spike url");
                    var md5 = exposer['md5'];
                    var spikeUrl = spike.URL.execution(spikeId, md5);
                    console.log("Spike url:" + spikeUrl);
                    $("#spikeBtn").one('click', function () {
                        console.log("Start spike, button disabled");
                        $(this).addClass("disabled");
                        // Send spike request
                        $.post(spikeUrl, {}, function (result) {
                            var spikeResult = result['data'];
                            var status = spikeResult['status'];
                            var statusInfo = spikeResult['statusInfo'];
                            console.log("Spike status" + statusInfo);
                            // Show spike result
                            mode.html('<span class="label label-success">' + statusInfo + '</span>');

                        });

                    });
                    mode.show();
                } else {
                    console.warn("Spike url not exposed, cannot spike");
                    var currentTime = exposer['currentTime'];
                    var startTime = exposer['startTime'];
                    var endTime = exposer['endTime'];
                    spike.countDown(spikeId, currentTime, startTime, endTime);
                }
            } else {
                console.error("Search spike detail from server failed");
                console.log('result' + result.valueOf());
            }
        });
    },
    countDown: function (spikeId, currentTime, startTime, endTime) {
        console.log("Spike Id:" + spikeId + ", Server local time：" + currentTime + ", Start time:" + startTime + ", End time" + endTime);
        var spikeBox = $("#spike-box");
        if (currentTime < endTime && currentTime > startTime) {
            console.log("Time condition qualified, can start spike");
            spike.handlerSpike(spikeId, spikeBox);
        }
        else if (currentTime > endTime) {
            alert(currentTime > endTime);
            console.log(currentTime + ">" + endTime);

            console.warn("Spike ended, current time is:" + currentTime + ", spike end time is" + endTime);
            spikeBox.html("Spike ended");
        } else {
            console.log("Spike not started");
            alert(currentTime < startTime);
            var killTime = new Date(startTime + 1000);
            console.log(killTime);
            console.log("Start time count down");
            spikeBox.countdown(killTime, function (event) {
                var format = event.strftime("Spike countdown: %D days %H hours %M minutes %S seconds");
                console.log(format);
                spikeBox.html(format);
            }).on('finish.countdown', function () {
                console.log("Prepare to execute callback, get spike url, execute spike");
                console.log("Countdown end");
                spike.handlerSpike(spikeId, spikeBox);
            });
        }
    }
};
