package me.maxdev.popularmoviesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

import me.maxdev.popularmoviesapp.R;
import me.maxdev.popularmoviesapp.model.User;
import me.maxdev.popularmoviesapp.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    //Declaration EditTexts
    EditText email, password;

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;

    //Declaration Button
    Button loginButton;

    //Declaration pattern for password fields
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    //Declaration DatabaseHelper
    DatabaseHelper databaseHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        initializingCreateAnAccount();
        initializingViews();

        //set click for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //Get values from EditText fields
                    String Email = email.getText().toString();
                    String Password = password.getText().toString();

                    //Authenticate user
                    User currentUser = databaseHelper.Authenticate(new User(null, null, Email, Password));
                    if (currentUser != null) {
                        Snackbar.make(loginButton, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
                        Intent activity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(activity);
                        finish();
                    } else {
                        Snackbar.make(loginButton, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    private void initializingCreateAnAccount() {
        TextView createAnAccount = (TextView) findViewById(R.id.account);
        createAnAccount.setText(fromHtml("<font color='#000000'>I don't have an account yet. </font><b><font color='#00bfff'>Create one!</b></font>"));
        createAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializingViews() {
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);

    }

    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password is to short!");
            }
        }
        return valid;
    }
}