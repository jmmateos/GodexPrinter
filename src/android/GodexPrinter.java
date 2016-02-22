package es.jmmateos.plugins.GodexPrinter;

import java.io.IOException;
import java.util.Set;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import android.util.Log;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.godex.Godex;

public class GodexPrinter extends CordovaPlugin {

    private static final String CONNECTBT = "connectBT";
    private static final String DISCONNECTBT = "disconnectBT";
    private static final String SENDCOMMAND = "sendCommand";
    private static final String SENDCOMMAND1 = "sendCommand1";

    private static final String TAG = "GodexPrinter";

    private CallbackContext callbackContext;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice printerDevice;
    boolean N;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext)
                            throws JSONException {

        this.callbackContext = callbackContext;

		 if(action.equals(CONNECTBT)) {
            return this.connectBT(callbackContext, data);
        }
        else if(action.equals(DISCONNECTBT)) {
            return this.disconnectBT(callbackContext);
        }
        else if(action.equals(SENDCOMMAND)) {
            return this.sendCommand(callbackContext,data);
        }
        else if(action.equals(SENDCOMMAND1)) {
            return this.sendCommand1(callbackContext,data);
        }
        else {
            callbackContext.error("Incorrect action parameter: " + action);
        }

        return false;
    }

    private boolean findBT(CallbackContext callbackContext, String name) {
    	Log.d(TAG, "findBT entered.");
		try {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				Log.e(TAG, "No bluetooth adapter available");
			}
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				this.cordova.getActivity().startActivityForResult(enableBluetooth, 0);
			}
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice device : pairedDevices) {
					if (device.getName().equalsIgnoreCase(name)) {
						Log.d(TAG, "Bluetooth Device Found: " + device.getName());
						printerDevice = device;
						return true;
					}
				}
			}
		} catch (Exception e) {
			String errMsg = e.getMessage();
			Log.e(TAG, errMsg);
			callbackContext.error(errMsg);
		}
		return false;
	}

    private boolean connectBT(CallbackContext callbackContext, JSONArray data) throws JSONException {

        String BTName = data.getString(0);
        Log.d(TAG, "connectBT entered.");

        if (findBT(callbackContext, BTName)) {
			N=Godex.open(printerDevice.getAddress(),2);		            		

		    if(N==false) {
		    	callbackContext.error("Bluetooth Connect fail.");
		    	return false;
		    } else {
				callbackContext.success("Bluetooth Connected.");
				return true;
			}

        } else {
        	Log.d(TAG, "printer not found.");
        	callbackContext.error("Printer not found.");
        	return false;
        }

	}

    private boolean disconnectBT(CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "disconnectBT entered.");

        try {
			N=Godex.close();		            		
		    if(N==false) {
		    	callbackContext.error("Bluetooth Disconnect fail.");
		    	return false;
		    } else {
				callbackContext.success("Bluetooth Disconnected.");
				return true;
			}
		} catch (IOException e) {
			String errMsg = e.getMessage();
			Log.e(TAG, errMsg);
			callbackContext.error(errMsg);
        	return false;
    	}

	}

    private boolean sendCommand(CallbackContext callbackContext, JSONArray data) throws JSONException {
        Log.d(TAG, "sendCommand entered.");

        String Comm = data.getString(0);
		N=Godex.sendCommand(Comm);		            		

	    if(N==false) {
	    	callbackContext.error("Command Failed.");	    	
	    	return false;
	    } else {
			callbackContext.success("command sent correctly.");
			return true;
		}

	}

    private boolean sendCommand1(CallbackContext callbackContext, JSONArray data) throws JSONException {
        Log.d(TAG, "sendCommand1 entered.");

        String Comm = data.getString(0);
        String encoding = data.getString(1);
		N=Godex.sendCommand(Comm, encoding);		            		

	    if(N==false) {
	    	callbackContext.error("Command Failed.");	        	
	    	return false;
	    } else {
			callbackContext.success("command sent correctly.");
			return true;
		}

	}


}