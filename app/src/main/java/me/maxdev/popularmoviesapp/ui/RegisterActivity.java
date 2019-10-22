package me.maxdev.popularmoviesapp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.maxdev.popularmoviesapp.R;
import me.maxdev.popularmoviesapp.model.User;
import me.maxdev.popularmoviesapp.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    //Declaration EditTexts
    EditText name, email, phone, password, confirm_password;

    TextView textViewLogin;

    //Declaration TextInputLayout
    TextInputLayout nameError, emailError, phoneError, passError, passConfirmError;

    //Declaration Button
    Button registerButton;

    //Declaration DatabaseHelper
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(this);
        initTextViewLogin();
        initializingViews();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = name.getText().toString();
                    String Email = email.getText().toString();
                    String Phone = phone.getText().toString();
                    String Password = password.getText().toString();
                    String Confirm_Password = confirm_password.getText().toString();

                    if (!databaseHelper.isEmailExists(Email)) {
                        databaseHelper.addUser(new User(null, UserName, Email, Phone, Password, Confirm_Password));
                        Snackbar.make(registerButton, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    } else {
                        Snackbar.make(registerButton, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initializingViews() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        confirm_password = findViewById(R.id.confirm_password);
        name = findViewById(R.id.name);
        emailError = findViewById(R.id.emailError);
        passError = findViewById(R.id.passError);
        nameError = findViewById(R.id.nameError);
        phoneError = findViewById(R.id.phoneError);
        passConfirmError = findViewById(R.id.passConfirmError);
        registerButton = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String username = name.getText().toString().trim();
        String email_address = email.getText().toString().trim();
        String phone_number = phone.getText().toString().trim();
        String password_user = password.getText().toString().trim();
        String confirm_pass = confirm_password.getText().toString().trim();

        //Handling validation for UserName field
        if (username.isEmpty()) {
            valid = false;
            nameError.setError("Please enter valid username!");
        } else {
            if (username.length() > 5) {
                valid = true;
                nameError.setError(null);
            } else {
                valid = false;
                nameError.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email_address).matches()) {
            valid = false;
            emailError.setError("Please enter valid email!");
        } else {
            valid = true;
            emailError.setError(null);
        }

        //Handling validation for Password field
        if (password_user.isEmpty()) {
            valid = false;
            passError.setError("Please enter valid password!");
        } else {
            if (password.length() > 5) {
                valid = true;
                passError.setError(null);
            } else {
                valid = false;
                passError.setError("Password is to short!");
            }

            //Handling validation for Password field
            if (confirm_pass.isEmpty()) {
                valid = false;
                passConfirmError.setError("Please enter a valid password!");
            } else {
                if (confirm_pass.length() > 5) {
                    valid = true;
                    passError.setError(null);
                } else {
                    valid = false;
                    passError.setError("Password is too short!");
                }

                //Handling validation for Password field
                if (phone_number.isEmpty()) {
                    valid = false;
                    phoneError.setError("Please enter a valid number!");
                } else {
                    valid = true;
                    phoneError.setError(null);
                }
            }
        }
        return valid;
    }
}
