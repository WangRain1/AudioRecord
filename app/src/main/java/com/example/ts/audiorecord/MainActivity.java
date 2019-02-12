/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.ts.audiorecord;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements UpdateTextImpl {

    String path = Environment.getExternalStorageDirectory().getPath() + "/record";
    TextView btnTextView;
    TextView audioText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //media
        btnTextView = findViewById(R.id.btn);
        File file = new File(path);
        final MediaRecord record = new MediaRecord(path + "/luyins",this);
        if (!file.exists())
        {
            file.mkdir();
        }
        btnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    record.startRecord();
                } catch (IOException e) {
                    Log.e("","------------" + e.getMessage());
                }
            }
        });

        //audio

        final AudioRecorder audioRecorder = new AudioRecorder(this);
        audioText = findViewById(R.id.AudioRecord);
        audioText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecorder.startRecord();
            }
        });
    }

    @Override
    public void updateMedio(double audio) {
        btnTextView.setText("音频：" + audio);
    }

    @Override
    public void updateAudio(double audio) {
        audioText.setText("音频：" + (int) audio);
    }
}
