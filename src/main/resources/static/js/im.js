/**
 * Created by zhangxiuzhi on 2017/7/28.
 */
//接收消息的处理
function _message(message) {
    var msg = JSON.parse(message.body);
    var content = msg.content;


    //购买
    if (msg.code==1000){
        if (typeof(esteel_mySale)!="object"){
            notificationMsg("您有新的消息！", content, function(){
                if (typeof(esteel_mySale)!="object"){
                    window.location=systemPath+"offer/mySale";
                }
            }, function(){});
        }else{
            esteel_mySale.list.ajaxRequestData();
        }

        return;
    }

    //点价
    if (msg.code == 1010){
        // if (typeof(esteel_mySale)!="object"){
            notificationMsg("您有新的消息！", content, function(){
                if (typeof(esteel_mySale)=="object"){
                    esteel_mySale.list.ajaxRequestData();
                }else{
                    window.location=systemPath+"offer/mySale";
                }
            }, function(){});
        // }else{
        //     esteel_mySale.list.ajaxRequestData();
        // }
        return;
    }

    //点价信息变化
    if (msg.code == 1011 || msg.code == 1012 || msg.code == 1014) {

        if (typeof(esteel_myOrder)=="object"){
            esteel_myOrder.list.ajaxRequestData(null, null);
        }

        //平台处理点价时 处理卖方数据
        if ((msg.code == 1012 || msg.code == 1014)&&typeof(esteel_mySale)=="object"){
            esteel_mySale.list.ajaxRequestData();
        }

        return;
    }



}