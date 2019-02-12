/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.ts.audiorecord;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

public class AudioRecorder {

    int SAMPLE_RATE_IN_HZ = 8000;
    int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT,AudioFormat.ENCODING_PCM_16BIT);
    short[] buffer;
    AudioRecord mAudioRecord;

    UpdateTextImpl mText;

    public AudioRecorder(UpdateTextImpl text) {
        this.mText = text;

        buffer = new short[BUFFER_SIZE];
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            record();
        }
    };


    public void startRecord(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();

                record();
//                mAudioRecord.stop();
//                mAudioRecord.release();
//                mAudioRecord = null;
            }
        }).start();
    }

    public void record(){
        //r是实际读取的数据长度，一般而言r会小于buffersize
        int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
        long v = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (int i = 0; i < buffer.length; i++) {
            v += buffer[i] * buffer[i];
        }
        // 平方和除以数据总长度，得到音量大小。
        double mean = v / (double) r;
        double volume = 10 * Math.log10(mean);
        mText.updateAudio(volume);

        mHandler.sendEmptyMessageDelayed(1,500);
    }
}
