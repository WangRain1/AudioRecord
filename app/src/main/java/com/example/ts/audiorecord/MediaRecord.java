/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.ts.audiorecord;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public class MediaRecord {

    MediaRecorder mRecorder;
    String mFilePath;

    UpdateTextImpl mText;
    public MediaRecord(String filePath,UpdateTextImpl updateText) {
        mFilePath = filePath;
        mText = updateText;
        mRecorder = new MediaRecorder();
    }


    public void startRecord() throws IOException {
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setMaxDuration(1000*60);
        mRecorder.prepare();
        mRecorder.start();

        getFFt();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getFFt();
        }
    };

    private void getFFt(){
        int audio = mRecorder.getMaxAmplitude();
        double db = 0;// 分贝
        if (audio > 1)
            db = 20 * Math.log10(audio);
        mText.updateMedio(db);
        mHandler.sendEmptyMessageDelayed(1,500);
    }

}
