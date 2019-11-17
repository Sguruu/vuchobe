package ru.vuchobe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import ru.vuchobe.service.AuthService;
import ru.vuchobe.util.threadUtil.ThreadAppCompatActivity;
import ru.vuchobe.util.threadUtil.ThreadTask;
import ru.vuchobe.util.ui.OnClickDrawableTextView;

public class RegActivity extends ThreadAppCompatActivity {

    private LinearLayout main;

    private EditText emailEditText;
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText repasswordEditText;

    private EditText asseptEditText;
    private Button regButton;
    private Button cancelButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        initUI();

        OnClickDrawableTextView passwordHideShow = (view, position, drawable, event) -> {
            EditText editText = (EditText) view;
            if (position == OnClickDrawableTextView.Position.RIGHT && event.getActionMasked() == MotionEvent.ACTION_UP) {
                showAndHidePassword(editText);
                return true;
            }
            return false;
        };

        passwordEditText.setOnTouchListener(passwordHideShow);
        repasswordEditText.setOnTouchListener(passwordHideShow);

        regButton.setActivated(true);

        regButton.setOnClickListener((view) ->
                registration()
        );

        cancelButton.setOnClickListener((view) ->
                cancel()
        );
    }

    private void initUI() {
        main = findViewById(R.id.mainId);

        emailEditText = findViewById(R.id.emailEditTextId);
        nameEditText = findViewById(R.id.nameEditTextId);
        passwordEditText = findViewById(R.id.passwordEditTextId);
        repasswordEditText = findViewById(R.id.repasswordEditTextId);

        asseptEditText = findViewById(R.id.politiceRegistrationId);
        regButton = findViewById(R.id.buttonRegId);
        cancelButton = findViewById(R.id.buttonCancelId);
    }

    private void showAndHidePassword(@NonNull EditText editText) {
        int value = editText.getInputType() ^ InputType.TYPE_CLASS_TEXT;
        editText.setInputType(value);
    }

    private boolean registration() {
        String email = emailEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String repassword = repasswordEditText.getText().toString();

        lockButton(regButton);
        ThreadTask threadTask = this.asyncIO(() ->
                AuthService.registration(email, name, password, repassword,
                        (body, exception) -> {
                            unlockButton();
                            if (exception != null) {
                                if (exception.isExist(AuthService.RegField.REPASSWORD)) {
                                    repasswordEditText.setError(exception.getMessageFor(AuthService.RegField.REPASSWORD));
                                    repasswordEditText.requestFocus();
                                }
                                if (exception.isExist(AuthService.RegField.PASSWORD)) {
                                    passwordEditText.setError(exception.getMessageFor(AuthService.RegField.PASSWORD));
                                    passwordEditText.requestFocus();
                                }
                                if (exception.isExist(AuthService.RegField.NAME)) {
                                    nameEditText.setError(exception.getMessageFor(AuthService.RegField.NAME));
                                    nameEditText.requestFocus();
                                }
                                if (exception.isExist(AuthService.RegField.EMAIL)) {
                                    emailEditText.setError(exception.getMessageFor(AuthService.RegField.EMAIL));
                                    emailEditText.requestFocus();
                                }
                                if (exception.isExist(AuthService.RegField.OTHER)) {
                                    Toast.makeText(
                                            this,
                                            exception.getMessageFor(AuthService.RegField.OTHER),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                                return;
                            }
                            //TODO Ложный правильный ответ (Не известно какой объект стоит ждать)
                            Toast.makeText(
                                    this,
                                    "OK",
                                    Toast.LENGTH_LONG
                            ).show();
                            onBackPressed();
                        }
                )
        );
        return threadTask.getStatus().isOK;
    }

    private void cancel() {
        this.onBackPressed();
    }

    private ArrayList<Button> blockButton = new ArrayList<>();

    private synchronized void lockButton(@NonNull Button button) {
        if (button == null) return;
        button.setTag(button.getText().toString());
        button.setText(this.getResources().getText(R.string.wait));
        blockButton.add(button);

        main.setEnabled(false);

        emailEditText.setEnabled(false);
        nameEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        repasswordEditText.setEnabled(false);

        asseptEditText.setEnabled(false);
        regButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }

    private synchronized void unlockButton() {
        for (Button elem : blockButton) {
            elem.setText((String) elem.getTag());
        }
        blockButton.clear();

        main.setEnabled(true);

        emailEditText.setEnabled(true);
        nameEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        repasswordEditText.setEnabled(true);

        asseptEditText.setEnabled(true);
        regButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }
}
