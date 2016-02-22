
var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec'),
    channel = require('cordova/channel');


var GodexPrinter = {


    connectBT: function (BTName, win, fail) {
        cordova.exec(win, fail, 'GodexPrinter', 'connectBT', [BTName]);
    },

    disconnectBT: function (win, fail) {
        cordova.exec(win, fail, 'GodexPrinter', 'disconnectBT', []);
    },

    sendCommand: function (command, encoding, win, fail) {
    	if (encoding && typeof encoding != "function") {
    		cordova.exec(win, fail, 'GodexPrinter', 'sendCommand1', [command,encoding]);
    	} else {
    		cordova.exec(encoding, win, 'GodexPrinter', 'sendCommand', [command]);
    	}
        
    }

};

module.exports = GodexPrinter;