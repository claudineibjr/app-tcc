package com.google.sample.cloudvision;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class ActSegundaTela extends AppCompatActivity {

    private static final String API_KEY = "";

    private EditText edtName;
    private Button btnTraduzir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_segunda_tela);

        edtName = (EditText)findViewById(R.id.edtName);
        btnTraduzir = (Button)findViewById(R.id.btnTraduzir);


        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("Name")){
            final String word = bundle.getString("Name");
            //envia para o translate api para traduzir a palavra pro portugues
            final Handler textViewHandler = new Handler();

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {

                    Translate translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();
                    //Translate translate = options.getService();
                    final Translation translation = translate.translate(word, Translate.TranslateOption.targetLanguage("pt"));
                    textViewHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            edtName.setText(translation.getTranslatedText());
                        }
                    });
                    return null;
                }
            }.execute();

        }

    }
}
