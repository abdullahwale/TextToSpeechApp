package com.example.comsats.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=4000;

private TextToSpeech mtts;
private EditText mEditText;
private SeekBar mSeekbarPitch;
    private SeekBar mSeekbarSpeed;
    private Button mButtonSpeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSpeek= findViewById(R.id.button_speek);

        mtts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            if(status == TextToSpeech.SUCCESS){
                int result = mtts.setLanguage(Locale.GERMAN);
                if(result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Language Not Supported");
                }else{
                    mButtonSpeek.setEnabled(true);
                }

            }else{
                Log.e("TTS", "Initilization Failed");
            }
            }
        });

        mEditText = findViewById(R.id.edit_text);
        mSeekbarPitch= findViewById(R.id.seek_bar_pitch);
        mSeekbarSpeed =findViewById(R.id.seek_bar_speed);

        mButtonSpeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();            }
        });
    }
    private void speak()
    {
        String txt= mEditText.getText().toString();
        float pitch= (float) mSeekbarPitch.getProgress()/50;
        if(pitch<0.1) pitch=0.1f;
        float speed= (float) mSeekbarSpeed.getProgress()/50;
        if(speed<0.1) speed=0.1f;

        mtts.setPitch(pitch);
        mtts.setSpeechRate(speed);
        //mtts.speek(txt,);

        mtts.speak(txt, TextToSpeech.QUEUE_FLUSH,null);
        //mtts.spook(txt,TextToSpeech.QUEUE_FLUSH,null);
       // mtts.spook();

    }

    @Override
    protected void onDestroy() {
        if(mtts!= null){
            mtts.stop();
            mtts.shutdown();
        }

        super.onDestroy();
    }
}
