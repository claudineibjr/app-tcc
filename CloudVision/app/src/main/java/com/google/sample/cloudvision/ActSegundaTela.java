package com.google.sample.cloudvision;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class ActSegundaTela extends AppCompatActivity {

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
            String word = bundle.getString("Name");
            edtName.setText(word);
        }

    }
}
