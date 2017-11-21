var module1 = (function(){
    var _count = 0;
    var m1 = function(){
	alert("m1");
      //...
    };
    var m2 = function(){
      //...
	alert("m2");
    };
    return {
      m1 : m1,
      m2 : m2
    };
  })();

