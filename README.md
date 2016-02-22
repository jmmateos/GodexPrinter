# GodexPrinter
A Cordova/Phonegap plugin driver for Godex bluetooth printers

##Usage
You can send data in EZPL Godex Programming Language:

```
var printer = cordova.plugins.GodexPrinter;
  printer.connectBT ('MX30',function () {
    $scope.connBT = 'Conexion correcta.';
    var sc = printer.sendCommand;
      sc('^Q100,0,0');
      sc('^W72');
      sc('^H16');
      sc('^P1');
      sc('^S2');
      sc('^AD');
      sc('^C1');
      sc('^R0');
      sc('~Q+0');
      sc('^O0');
      sc('^D0');
      sc('^E10');
      sc('~R200');
      sc('^XSET,ROTATION,0');
      sc('^L');
      sc('AE,37,40,1,1,0,0E,Text Title Example.');
      sc('AB,77,86,1,1,0,0,other text example');
      sc('E', function (msg) {
              console.log(msg);
              printer.disconnectBT();
            }, function (err){
              console.log('Error: ' + err);
            });
    }, function (err){
      console.log('error in connect: ' + err;
    });      
```

##Install
###Cordova

```
cordova plugin add https://github.com/jmmateos/godexprinter.git
```

##EZPL - Godex Programming Language
For more information about EZPL please see the  [PDF Official Manual](http://www.godexintl.com/en/download/downloads/Download/1070)
