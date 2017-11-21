/**   
 * 对Date的扩展，将 Date 转化为指定格式的String   
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符   
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
 * eg:   
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04   
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04   
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04   
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18   
 */    
Date.prototype.pattern=function(fmt) {     
    var o = {     
    "M+" : this.getMonth()+1, //月份     
    "d+" : this.getDate(), //日     
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时     
    "H+" : this.getHours(), //小时     
    "m+" : this.getMinutes(), //分     
    "s+" : this.getSeconds(), //秒     
    "q+" : Math.floor((this.getMonth()+3)/3), //季度     
    "S" : this.getMilliseconds(), //毫秒
	'tt': this.getHours() < 12 ? 'am' : 'pm',
    'TT': this.getHours() < 12 ? 'AM' : 'PM'     
    };     
    var week = {     
    "0" : "/u65e5",     
    "1" : "/u4e00",     
    "2" : "/u4e8c",     
    "3" : "/u4e09",     
    "4" : "/u56db",     
    "5" : "/u4e94",     
    "6" : "/u516d"    
    };     
    if(/(y+)/.test(fmt)){     
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));     
    }     
    if(/(E+)/.test(fmt)){     
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);     
    }     
    for(var k in o){     
        if(new RegExp("("+ k +")").test(fmt)){     
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));     
        }     
    }     
    return fmt;     
}   


/*
d：将日显示为不带前导零的数字，如1    
dd：将日显示为带前导零的数字，如01    
ddd：将日显示为缩写形式，如Sun    
dddd：将日显示为全名，如Sunday    
M：将月份显示为不带前导零的数字，如一月显示为1    
MM：将月份显示为带前导零的数字，如01   
MMM：将月份显示为缩写形式，如Jan   
MMMM：将月份显示为完整月份名，如January   
yy：以两位数字格式显示年份   
yyyy：以四位数字格式显示年份   
h：使用12小时制将小时显示为不带前导零的数字，注意||的用法   
hh：使用12小时制将小时显示为带前导零的数字   
H：使用24小时制将小时显示为不带前导零的数字   
HH：使用24小时制将小时显示为带前导零的数字   
m：将分钟显示为不带前导零的数字   
mm：将分钟显示为带前导零的数字   
s：将秒显示为不带前导零的数字   
ss：将秒显示为带前导零的数字   
l：将毫秒显示为不带前导零的数字   
ll：将毫秒显示为带前导零的数字   
tt：显示am/pm   
TT：显示AM/PM   
*/
Date.prototype.langDate = function(language){
	
	var zeroize = function (value, length) {    
        if (!length) {    
            length = 2;    
        }    
        value = new String(value);    
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {    
            zeros += '0';    
        }    
            return zeros + value;    
    };    
	
	function Showdate(_string){
		var date = new Date();
		return _string.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
			switch ($0) {    
				case 'd': return date.getDate();    
				case 'dd': return zeroize(date.getDate());    
				case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];    
				case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];    
				case 'M': return date.getMonth() + 1;    
				case 'MM': return zeroize(date.getMonth() + 1);    
				case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];    
				case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];    
				case 'yy': return new String(date.getFullYear()).substr(2);    
				case 'yyyy': return date.getFullYear();    
				case 'h': return date.getHours() % 12 || 12;    
				case 'hh': return zeroize(date.getHours() % 12 || 12);    
				case 'H': return date.getHours();    
				case 'HH': return zeroize(date.getHours());    
				case 'm': return date.getMinutes();    
				case 'mm': return zeroize(date.getMinutes());    
				case 's': return date.getSeconds();    
				case 'ss': return zeroize(date.getSeconds());    
				case 'l': return date.getMilliseconds();    
				case 'll': return zeroize(date.getMilliseconds());    
				case 'tt': return date.getHours() < 12 ? 'am' : 'pm';    
				case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';  
			}
		});
	}
	
	function Showdate_week(){
		var date = new Date();
		return ['周日', '周一', '周二', '周三', '周四', '周五', '周六'][date.getDay()];    
	}


	if(language == "en" || language == "en_US"){
		return (Showdate('ddd,MMM dd,yyyy HH:mm'));
	}else{
		return (Showdate("yyyy-MM-dd")+" "+Showdate_week()+" " +Showdate("HH:mm:ss"));
	}
}



/*
document.write("前天："+GetDateStr(-2)); 
document.write("<br />昨天："+GetDateStr(-1)); 
document.write("<br />今天："+GetDateStr(0)); 
document.write("<br />明天："+GetDateStr(1)); 
document.write("<br />后天："+GetDateStr(2)); 
document.write("<br />大后天："+GetDateStr(3)); 
*/
function GetDateStr(AddDayCount) {

    var dd = new Date();

    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期

    var y = dd.getFullYear();

    var m = dd.getMonth()+1;//获取当前月份的日期
	if(m <10){
		m = "0"+m;
	}

    var d = dd.getDate();
	if(d <10){
		d = "0"+d;
	}

    return y+"-"+m+"-"+d;

}
function GetDateStr2(currentDay,AddDayCount) {
	 var dd = new Date(currentDay);

    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期

    var y = dd.getFullYear();

    var m = dd.getMonth()+1;//获取当前月份的日期
	if(m <10){
		m = "0"+m;
	}

    var d = dd.getDate();
	if(d <10){
		d = "0"+d;
	}

    return y+"-"+m+"-"+d;
}

function GetDateStr3(AddDayCount) {
	  var dd = new Date();

    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期

   
    return dd;

}



/*
返回日期时间
 document.write("前天："+GetDateStr(-2));
 document.write("<br />昨天："+GetDateStr(-1));
 document.write("<br />今天："+GetDateStr(0));
 document.write("<br />明天："+GetDateStr(1));
 document.write("<br />后天："+GetDateStr(2));
 document.write("<br />大后天："+GetDateStr(3));
 */
function GetDateTimeStr(AddDayCount){
	var dd = new Date();

	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期

	var y = dd.getFullYear();

	var m = dd.getMonth()+1;//获取当前月份的日期
	if(m <10){
		m = "0"+m;
	}

	var d = dd.getDate();
	if(d <10){
		d = "0"+d;
	}

	return y+"-"+m+"-"+d+" "+dd.getHours()+":"+dd.getMinutes()+":"+dd.getSeconds();
}