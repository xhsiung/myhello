    var exec = require('cordova/exec');
    var channel = require('cordova/channel');

    channel.createSticky('onCordovaInfoReady');
    // Tell cordova channel to wait on the CordovaInfoReady event
    channel.waitForInitialization('onCordovaInfoReady');
	
    var Hello = function() {};
    Hello.prototype.greet = function (arg0 ,arg1 ,successCallback, errorCallback) {
        //對應javascript  cordova.exec(SuccessFn,  FailFn , Device , Fn , [ ]) //Fn即為發佈的function
        cordova.exec(successCallback, errorCallback, "Hello", "greet", [arg0 , arg1]);
    }

    var hello = new Hello();
    module.exports = hello;
