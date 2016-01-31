package com.axsoho.hello;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex on 2016/1/31.
 */
public class Hello extends CordovaPlugin{
    String TAG = "Hello";

    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.d(TAG, "get message");
                    Bundle _bundle = msg.getData();
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject( _bundle.getString("argMsg0") );
                        Toast.makeText(cordova.getActivity(), obj.getString("name") + obj.getString("mobile"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        //return super.execute(action, args, callbackContext);
        if (action.equals("greet")){
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("ALEXLOG" , "call ThreadPool");
                    Message msg = new Message();
                    Bundle _bundle = msg.getData();
                    try {

                        JSONObject jobj = args.getJSONObject(0);
                        boolean yesno = args.getBoolean(1);
                        _bundle.putString("argMsg0" , jobj.toString() );
                        _bundle.putBoolean("argMsg1", yesno);
                        msg.what = 0 ;
                        h.sendMessage(msg);
                        //回調
                        callbackContext.success("my call back");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            //cordova.getActivity().runOnUiThread();  用此方法仍會阻塞
            return true;
        }

        // if (action.equals("greet2")){}
        return false;

    }
}
