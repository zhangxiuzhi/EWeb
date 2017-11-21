/**
 * Created by zhangxiuzhi on 2017/6/26.
 */

var message = (function () {



    var messageReject = function (msg) {
        switch (msg.code) {
            case 1000: {

                break;
            }
            case 1010: {
                jbsframe.ajaxRequest({
                        url: "offer/confirmPricing",
                        data: {
                            keyId: msg.keyId,
                            status: 2
                        }
                    },
                    function () {
//
//                                    alert("222222");
                    }
                );
                break;
            }
            default: {
                alert("default");
            }
        }
    };


    var messageOk = function (msg) {
        switch (msg.code) {
            case 1000: {

                break;
            }
            case 1010: {
                jbsframe.ajaxRequest({
                        url: "offer/confirmPricing",
                        data: {
                            keyId: msg.keyId,
                            status: 1
                        }
                    },
                    function () {

                    }
                );
                break;
            }
            case 1011: {
                //刷新点价列表
                try {
                    esteel_myOrder.list_pricing.ajaxRequestData(null, null);
                } catch (e) {
                }
                break;
            }
            default: {
                alert("default");
            }
        }
    };
    var errorCallback = function () {
        stomp.connect({}, callback, errorCallback);
    };

    return {
        messageReject: messageReject,
        messageOk:messageOk,
        errorCallback:errorCallback

    };
})();



