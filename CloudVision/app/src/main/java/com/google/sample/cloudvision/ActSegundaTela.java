package com.google.sample.cloudvision;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.widget.TextView;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


import java.util.ArrayList;
import java.util.Locale;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class ActSegundaTela extends AppCompatActivity {

    private static final String API_KEY = "";

    private TextView txtTarget;
    private TextView txtSource;
    private TextView txtFala;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_segunda_tela);

        txtTarget = (TextView) findViewById(R.id.txtTarget);
        txtSource = (TextView) findViewById(R.id.txtSource);
        txtFala = (TextView) findViewById(R.id.txtFala);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("Name")){
            final String word = bundle.getString("Name");
            txtTarget.setText(word);
            //envia para o translate api para traduzir a palavra pro portugues
            final Handler textViewHandler = new Handler();

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {

                    Translate translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();

                    final Translation translation = translate.translate(word, Translate.TranslateOption.targetLanguage("pt"));

                    textViewHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtSource.setText(translation.getTranslatedText());
                        }
                    });
                    return null;
                }
            }.execute();

        }

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String target = txtTarget.getText().toString();

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtFala.setText(result.get(0));
                    if(target.equals(result.get(0))){
                        txtFala.setTextColor(Color.GREEN);
                    } else
                        txtFala.setTextColor(Color.RED);
                }
                break;
            }

        }
    }




}
