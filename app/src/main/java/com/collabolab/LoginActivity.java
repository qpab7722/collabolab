package com.collabolab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    EditText etID, etPW;
    boolean b_id=false, b_pw=false;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etID = findViewById(R.id.et_id);
        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_id = false;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.mainGray));
                } else {
                    b_id = true;
                    if (b_pw) {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.mainColor));
                    }
                }
            }
        });
        etPW = findViewById(R.id.et_password);
        etPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_pw = false;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.mainGray));
                } else {
                    b_pw = true;
                    if (b_id) {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.mainColor));
                    }
                }
            }
        });

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setEnabled(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetDateActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
