package ru.vuchobe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import ru.vuchobe.util.ui.OnClickDrawableTextView;

public class RegActivity extends AppCompatActivity {

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

    private void showAndHidePassword(EditText editText) {
        int value = editText.getInputType() ^ InputType.TYPE_CLASS_TEXT;
        editText.setInputType(value);
    }

    private void registration() {

    }

    private void cancel() {
        this.onBackPressed();
    }
}
