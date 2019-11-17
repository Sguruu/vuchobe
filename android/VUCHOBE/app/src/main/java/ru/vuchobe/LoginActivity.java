package ru.vuchobe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.vuchobe.service.AuthService;
import ru.vuchobe.util.threadUtil.ThreadAppCompatActivity;
import ru.vuchobe.util.threadUtil.ThreadTask;
import ru.vuchobe.util.ui.OnClickDrawableTextView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ThreadAppCompatActivity {

    private LinearLayout main;

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
        main = findViewById(R.id.mainId);

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
        lockButton(logonButton);
        ThreadTask threadTask = this.asyncIO(() ->
                AuthService.logon(login, password, (body, exception) -> {
                    unlockButton();
                    if (exception != null) {
                        if (exception.getPasswordMessages().length != 0) {
                            passwordEditText.setError(exception.getPasswordMessages()[0]);
                            passwordEditText.requestFocus();
                        }
                        if (exception.getLoginMessages().length != 0) {
                            loginEditText.setError(exception.getLoginMessages()[0]);
                            passwordEditText.requestFocus();
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
        Intent intent = new Intent(this, RegActivity.class);
        this.startActivity(intent);
    }

    private void forgotPassword() {

    }

    private ArrayList<Button> blockButton = new ArrayList<>();

    private synchronized void lockButton(@NonNull Button button) {
        if (button == null) return;
        button.setTag(button.getText().toString());
        button.setText(this.getResources().getText(R.string.wait));
        blockButton.add(button);

        main.setEnabled(false);

        loginEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        logonButton.setEnabled(false);
        regButton.setEnabled(false);
        forgotPasswordButton.setEnabled(false);
    }

    private synchronized void unlockButton() {
        for (Button elem : blockButton) {
            elem.setText((String) elem.getTag());
        }
        blockButton.clear();

        main.setEnabled(true);

        loginEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        logonButton.setEnabled(true);
        regButton.setEnabled(true);
        forgotPasswordButton.setEnabled(true);
    }
}