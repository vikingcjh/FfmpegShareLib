package com.soul.learn.ffmpegsharelib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.soul.learn.ffmpegsharelib.jniUtils.FfmpegStreamUtils;
import com.soul.learn.ffmpegsharelib.jniUtils.YuvUtils;
import com.soul.learn.ffmpegsharelib.ui.LiveActivity;

public class MainActivity extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private String ip;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        /*TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        tv.setText(new String(YuvUtils.Yuv444Split()));*/

        Button startButton = (Button) this.findViewById(R.id.btn_stream);

        final EditText urlEdittext_input= (EditText) this.findViewById(R.id.input_url);
        final EditText urlEdittext_output= (EditText) this.findViewById(R.id.output_url);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0){

                Intent intent = new Intent(MainActivity.this, LiveActivity.class);
                startActivity(intent);
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRunning) {
                            return;
                        }
                        isRunning = true;
                        String outputurl = "rtmp://" + "10.87.100.107"+":1935" + "/live/livestream";
                        Log.i("niodata", outputurl);
                        FfmpegStreamUtils.muxing(outputurl);
                        isRunning = false;
                    }
                }).start();*/

                /*String folderurl= Environment.getExternalStorageDirectory().getPath();

                String urltext_input=urlEdittext_input.getText().toString();
//                urltext_input = "045.flv";
                urltext_input = "113.mp4";
                String inputurl=folderurl+"/aaa/"+urltext_input;

                String outputurl=urlEdittext_output.getText().toString();
                outputurl = "rtmp://" + "10.87.100.107"+":1935" + "/live/livestream";

                Log.e("inputurl",inputurl);
                Log.e("outputurl",outputurl);
                String info="";

                FfmpegStreamUtils.stream(inputurl,outputurl);

                Log.e("Info",info);*/
            }
        });

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        ip = intToIp(ipAddress);
        Toast.makeText(this,ip,Toast.LENGTH_SHORT).show();
    }

    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
