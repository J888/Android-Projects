package edu.temple.formapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput, retypePasswordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        retypePasswordInput = findViewById(R.id.retypePasswordInput);

        final Button theButton = findViewById(R.id.submitButtom);
        theButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String passString = passwordInput.getText().toString();
                String retypedPassString = retypePasswordInput.getText().toString();

                if(nameInput.getText().toString().equals("")) {
                    Toast.makeText(FormActivity.this, "Name is empty, try again", Toast.LENGTH_SHORT).show();
                }

                else if(emailInput.getText().toString().equals("")) {
                    Toast.makeText(FormActivity.this, "Email is empty, try again", Toast.LENGTH_SHORT).show();
                }

                else if(passwordInput.getText().toString().equals("")) {
                    Toast.makeText(FormActivity.this, "Password is empty, try again", Toast.LENGTH_SHORT).show();
                }

                else if(retypePasswordInput.getText().toString().equals("")) {
                    Toast.makeText(FormActivity.this, "You need to reenter the pasword", Toast.LENGTH_SHORT).show();
                }

                else if(!passString.equals(retypedPassString)) {

                    Toast.makeText(FormActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(FormActivity.this, "Your info has been saved", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}

