package ru.vuchobe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import ru.vuchobe.service.AuthService;
import ru.vuchobe.util.threadUtil.ThreadAppCompatActivity;
import ru.vuchobe.util.threadUtil.ThreadTask;
import ru.vuchobe.util.ui.OnClickDrawableTextView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ThreadAppCompatActivity {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button logonButton;
    private Button regButton;
    private Button forgotPasswordButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();

        passwordEditText.setOnTouchListener((OnClickDrawableTextView) (view, position, drawable, event) -> {
            EditText editText = (EditText) view;
            if (position == OnClickDrawableTextView.Position.RIGHT && event.getActionMasked() == MotionEvent.ACTION_UP) {
                showAndHidePassword(editText);
                return true;
            }
            return false;
        });

        logonButton.setActivated(true);
        logonButton.setOnClickListener((view) ->
                logon(loginEditText.getText().toString(), passwordEditText.getText().toString())
        );

        regButton.setOnClickListener((view) ->
                registration()
        );

        forgotPasswordButton.setOnClickListener((view) ->
                forgotPassword()
        );

    }

    private void initUI() {
        loginEditText = findViewById(R.id.loginEditTextId);
        passwordEditText = findViewById(R.id.passwordEditTextId);

        logonButton = findViewById(R.id.buttonLoginId);
        regButton = findViewById(R.id.buttonRegId);
        forgotPasswordButton = findViewById(R.id.forgotPasswordId);
    }

    private void showAndHidePassword(EditText editText) {
        int value = editText.getInputType() ^ InputType.TYPE_CLASS_TEXT;
        editText.setInputType(value);
    }

    private boolean logon(String login, String password) {
        ThreadTask threadTask = this.asyncIO(() ->
                AuthService.logon(login, password, (body, exception) -> {
                    if (exception != null) {
                        if (exception.getLoginMessages().length != 0) {
                            loginEditText.setError(exception.getLoginMessages()[0]);
                        }
                        if (exception.getPasswordMessages().length != 0) {
                            passwordEditText.setError(exception.getPasswordMessages()[0]);
                        }
                        if (exception.getOtherMessages().length != 0) {
                            Toast.makeText(
                                    this,
                                    exception.getOtherMessages()[0],
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                        return;
                    }
                    Toast.makeText(
                            this,
                            "OK",
                            Toast.LENGTH_LONG
                    ).show();
                })
        );
        return threadTask.getStatus().isOK;
    }

    private void registration() {

    }

    private void forgotPassword() {

    }
}