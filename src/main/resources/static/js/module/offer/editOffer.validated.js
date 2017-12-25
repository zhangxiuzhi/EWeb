//验证报盘商品信息
function validateOfferInfo(){
    var valid = $("#form-offer [data-validetta],#form-offer select[data-validetta]").length;
    $("#form-offer [data-validetta],#form-offer select[data-validetta]").each(function(index,element){

        if(element.value == ""){
            insertErrorBubble($(element));
        }else{
/*            var $element = $(element);
            //报盘数量100倍数
            if($element.attr("id") == "offerNum"){
                var value = $element.val();
                var pos,H;
                if(value !="" && Number(value)%100>0) {
                    esteel_offerEdit_Futures.validatePricingNumber = false;   //验证点数量为100的倍数失败
                    insertErrorBubble($(element),"数量必须为100的倍数");
                }else{
                    esteel_offerEdit_Futures.validatePricingNumber = true;
                    $element.next(".validetta-bubble").remove();
                    valid -=1;
                }
            }else{
                valid -=1;
            }
*/
        	 valid -=1;
        }
    });

    //验证报盘数量是否为100倍数
    if(esteel_offerEdit_Futures.validatePricingNumber==false){
        valid +=1;
    }
    //console.log("验证商品",valid)
    return valid == 0 ?true : false;
}

function insertErrorBubble($element,errorText){
    $element.next(".validetta-bubble").remove();

    var pos, W = 0, H = 0;
    var $bubble = $("<div class='validetta-bubble validetta-bubble--bottom'></div>");
    $bubble.html("此项为必填项");

    //组件下拉
    if($element.parents(".react-selectbox").length>0) {
        //品名
        if($element[0].name =="ItemName"){
            esteel_offerEdit_Futures.selectBox_ItemName.addValidettaBubble();
        }
        //港口
        if($element[0].name =="Port"){
            esteel_offerEdit_Futures.selectBox_Port.addValidettaBubble();
        }
    }
    else if($element.hasClass("uploadFile")) {
        pos = $element.parent(".btn.btn-file").position();
        H = $element.parent(".btn.btn-file")[0].offsetHeight;
        $bubble.css({
            top:pos.top + H + 0,
            left:pos.left + W + 15
        });
        $element.parent(".btn.btn-file").after($bubble);
    }else{
        pos = $element.position();
        H = $element[0].offsetHeight;
        $bubble.css({
            top:pos.top + H + 0,
            left:pos.left + W + 15
        });
        $element.after($bubble);
    }
    $element.on("change",function(e){
        if(e.target.value!=""){
            $element.next(".validetta-bubble").remove();
            $element.parent(".btn.btn-file").next(".validetta-bubble").remove();
        }
    });
    if($element.hasClass("datetimepicker")){
        $element.on("dp.change", function (evt) {
            $(evt.currentTarget).parent(".input-group").removeClass("validetta-error");
            $(evt.currentTarget).siblings(".validetta-bubble").remove();
        });
    }
    if($element.attr("id") == "offerNum"){
        $bubble.html(errorText);
        $element.after($bubble);
    }

}