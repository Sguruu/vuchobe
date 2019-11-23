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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.vuchobe.main.MainActivity;
import ru.vuchobe.service.AuthService;
import ru.vuchobe.util.threadUtil.ThreadAppCompatActivity;
import ru.vuchobe.util.threadUtil.ThreadTask;
import ru.vuchobe.util.ui.OnClickDrawableTextView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ThreadAppCompatActivity {

    /**
     * UI elements
     */
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

        initUI();                                                                                   //find all UI(поиск всех UI елементов)

        //Listener Click By Icon editText(Прослушивание нажатия на иконки для поля ввода)
        passwordEditText.setOnTouchListener((OnClickDrawableTextView) (view, position, drawable, event) -> {
            EditText editText = (EditText) view;
            if (position == OnClickDrawableTextView.Position.RIGHT && event.getActionMasked() == MotionEvent.ACTION_UP) {   //Если нажата правая иконка
                showAndHidePassword(editText);
                return true;
            }
            return false;
        });

        logonButton.setActivated(true);                                                             //set Button main (Color white) (Устанавливаем как главную)
        logonButton.setOnClickListener((view) ->                                                    //listener click by logon button (Прослушивание нажатия на кнопку ВОЙТИ)
                logon(loginEditText.getText().toString(), passwordEditText.getText().toString())    //Process logon (Выполнение входа)
        );

        regButton.setOnClickListener((view) ->                                                      //listener click by registration button (Прослушивание нажатия на кнопку РЕГИСТРАЦИЯ)
                registration()                                                                      //Process registration (Выполнение регистрации)
        );

        forgotPasswordButton.setOnClickListener((view) ->                                           //listener click by forgot password button (Прослушивание нажатия на кнопку ЗАБЫЛИ ПАРОЛЬ)
                forgotPassword()                                                                    //Process forgot (Выполнение востановления пароля)
        );

    }

    /**
     * find all uses UI elements
     * Поиск всех UI элементов
     */
    private void initUI() {
        main = findViewById(R.id.mainId);                                                           //Container all next UI elements (Контейнер всех следующих UI)

        loginEditText = findViewById(R.id.loginEditTextId);                                         //login input field UI (Поле ввода логина UI)
        passwordEditText = findViewById(R.id.passwordEditTextId);                                   //password input field UI (Поле ввода пароля UI)

        logonButton = findViewById(R.id.buttonLoginId);                                             //logon button UI (Кнопка ВОЙТИ UI)
        regButton = findViewById(R.id.buttonRegId);                                                 //reg button UI (Кнопка РЕГИСТАЦИЯ UI)
        forgotPasswordButton = findViewById(R.id.forgotPasswordId);                                 //forgot password button UI (Кнопка забыли пароль UI)
    }

    private void showAndHidePassword(EditText editText) {
        int value = editText.getInputType() ^ InputType.TYPE_CLASS_TEXT;                            //revers/switch show/hide password in UI (переключение видимости пароля в UI)
        editText.setInputType(value);                                                               //set new value (установка нового значения)
    }

    /**
     * Process logon
     * Выполнение авторизации
     *
     * @param login    логин
     * @param password пароль
     * @return запустилась ли задача
     */
    private boolean logon(String login, String password) {
        lockButton(logonButton);                                                                    //off all buttons (деактивация всех кнопок)
        ThreadTask threadTask = this.asyncIO(() ->                                                  //asunc run code in threadIO (Запуск кода в отдельном потоке IO)
                AuthService.logon(login, password, (body, exception) -> {                           //run network query authorization and take result (запуск интернет запроса авторизации и получение результата)
                    unlockButton();                                                                 //on all buttons. finish query (активировать все кнопки. Запрос уже выполнен)
                    if (exception != null) {                                                        //if exception show message for user (если получили ошибку вывести сообщение пользователю)
                        if (exception.getPasswordMessages().length != 0) {                          //password exception (ошибка в пароле)
                            passwordEditText.setError(exception.getPasswordMessages()[0]);
                            passwordEditText.requestFocus();
                        }
                        if (exception.getLoginMessages().length != 0) {                             //login exception (ошибка в логине)
                            loginEditText.setError(exception.getLoginMessages()[0]);
                            passwordEditText.requestFocus();
                        }
                        if (exception.getOtherMessages().length != 0) {                             //other exception (другие ошибки (нет интернета или json не сконвертировался или сервер вернул ошибку))
                            Snackbar.make(this.main, exception.getOtherMessages()[0], Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            Toast.makeText(
                                    this,
                                    exception.getOtherMessages()[0],
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                        return;                                                                     //if exception then return (Если есть ошибка то прекратить выполнение)
                    }
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);
                })
        );
        return threadTask.getStatus().isOK;
    }

    /**
     * Process registration
     * Выполнение регистрации
     */
    private void registration() {
        Intent intent = new Intent(this, RegActivity.class);                          //Create Intent on RegActivity (Создаем намерение на Activity регистрация)
        this.startActivity(intent);                                                                 //open Activity (Переход на Activity Регистрации)
    }

    /**
     * Process forgot password
     * Выполнение востановления пароля
     */
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