package com.soul.learn.ffmpegsharelib.ui;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.soul.learn.ffmpegsharelib.R;
import com.soul.learn.ffmpegsharelib.jniUtils.StreamHelper;

import java.io.IOException;
import java.util.List;

public class LiveActivity extends Activity implements SurfaceHolder.Callback {

    static final String TAG="niodata";
    SurfaceView surfaceview;
    SurfaceHolder surfaceHolder;
    Camera camera;

    TextView stop;
    boolean recording = false;

    String filename = null;
    //音频部分
    private int frequence = 44100;
    //CHANNEL_IN_MONO	单声道
    private int channelConfig = AudioFormat.CHANNEL_IN_STEREO;//CHANNEL_IN_STEREO//CHANNEL_IN_MONO
//    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private int audioEncoding = AudioFormat.ENCODING_PCM_8BIT;

    private static int CAMERA_WIDTH = 352;//1920;
    private static int CAMERA_HEIGHT = 288; //1080;

    /*static {
        System.loadLibrary("native-lib");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        stop = (TextView) findViewById(R.id.tip);
        /*filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/b/"
                +"sqq.flv";
        Log.d(TAG,"name"+filename);*/

        String outputurl = "rtmp://" + "10.87.100.107"+":1935" + "/live/livestream";
//        String outputurl = "rtmp://" + "192.168.1.104"+":1935" + "/live/livestream";
        Log.i("niodata", outputurl);
        StreamHelper.init(outputurl);
        /*if(FfmpegHelper.init(filename.getBytes())==-1){
            stop.setText("无法连接网络");
            return;
        }*/
        Button bt = (Button) findViewById(R.id.stop);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onclick");
                if (recording) {
                    recording = false;
                    StreamHelper.finishStream();
                } else {
                    StreamHelper.initStartTime();
                    recording = true;
                    //开始录制
                    RecordTask task = new RecordTask();
                    task.execute();
                }
            }
        });

        surfaceview = (SurfaceView) findViewById(R.id.view);
        surfaceHolder = surfaceview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        /*//开始录制
        RecordTask task = new RecordTask();
        task.execute();*/

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "oncreate");
        // TODO Auto-generated method stub
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            camera = Camera.open(0);
            camera.setDisplayOrientation(90);
        } else {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
        }
        try {
            if (camera != null) {
                Camera.Parameters p = camera.getParameters();
//                p.setPreviewSize(352, 288);
                p.setPreviewSize(CAMERA_WIDTH,CAMERA_HEIGHT);
//                p.setFlashMode(Camera.Parameters.FOCUS_MODE_AUTO);
                p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//                p.setPreviewFormat(ImageFormat.YV12);
//                p.setPreviewFormat(ImageFormat.NV21);
                p.setPreviewFpsRange(30000, 30000);
                p.setRotation(180);
                List<int[]> list= p.getSupportedPreviewFpsRange();
                for (int i=0; i<list.size();i++) {
                    int[] a = list.get(i);
                    Log.i("NIODATA", "Fps List[" + i + "]= ");
                    for(int k=0;k<a.length;k++) {
                        Log.i("NIODATA", "a[" + k + "]= " + a[k]);
                    }

                }
                List<Camera.Size> list2 = p.getSupportedPreviewSizes();
                for (int i=0; i<list2.size();i++) {
                    Camera.Size a = list2.get(i);
                    Log.i("NIODATA", "Size List[" + i + "]= "+a.width+","+a.height);
                }
                camera.setParameters(p);

                camera.setPreviewDisplay(holder);
                surfaceHolder = holder;
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (camera != null) {
                camera.release();
                camera = null;
            }
        }
    }

    long lasttimestamp = 0;
    long curtimestamp = 0;
    byte[] gyuvdata;
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        Camera.Parameters p = camera.getParameters();
        p.setPreviewSize(CAMERA_WIDTH,CAMERA_HEIGHT);

        camera.setPreviewCallback(new Camera.PreviewCallback() {

            @Override
            public void onPreviewFrame(byte[] yuvdata, Camera arg1) {
                if(recording){
//                    long lasttimestamp = 0;
//                    long curtimestamp = 0;
                    curtimestamp = System.currentTimeMillis();
                    Log.i("niodata", "read vedio timestamp= " + curtimestamp);
                    Log.i("niodata", "read vedio time_duration= " + (curtimestamp - lasttimestamp));
//                    Log.i("niodata", "read vedio size= " + yuvdata.length);
                    lasttimestamp = curtimestamp;
//                    StreamHelper.sendVideo(yuvdata);
                    gyuvdata = yuvdata;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            StreamHelper.senddata(gyuvdata, gyuvdata.length,false);
                        }
                    }).start();

                }
            }

        });
        camera.setParameters(p);
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (Exception E) {

        }
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
            surfaceview = null;
            surfaceHolder = null;
        }

    }

    byte[] bb;
    class RecordTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            int bufferSize = AudioRecord.getMinBufferSize(frequence, channelConfig, audioEncoding);
            //int bufferSize = 1024;
            AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    frequence, channelConfig, audioEncoding , bufferSize);
            //开始录制
            record.startRecording();

            bb = new byte[bufferSize];
//            short[] sData = new short[bufferSize / 2];

            long lasttimestamp = 0;
            long curtimestamp = 0;

            while(recording){
                final int size = record.read(bb, 0,bufferSize);
//                int size = record.read(sData, 0,bufferSize/2);
//                Log.i("NIODATA", "AUDIO BUFFER GET SIZE = " + size);
                if(size >0){
//                    StreamHelper.sendAudio(bb, size);
                    curtimestamp = System.currentTimeMillis();
                    Log.i("niodata", "read audio timestamp= " + curtimestamp);
                    Log.i("niodata", "read audio time_duration= " + (curtimestamp - lasttimestamp));
                    Log.i("niodata", "read audio size= " + size);
                    lasttimestamp = curtimestamp;
//                    StreamHelper.senddata(bb, size,true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            StreamHelper.sendAudio(bb, size);
//                    byte bData[] = short2byte(sData);
//                    StreamHelper.senddata(bData, bData.length,true);
//                    StreamHelper.sendAudioShort(sData, size);
                        }
                    }).start();

                }
            }
            if(record.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING){
                record.stop();
            }
            record.release();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            stop.setText("已经停止了");
        }

    }


    //convert short to byte
    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }

}
