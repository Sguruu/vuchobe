package ru.vuchobe.service;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.vuchobe.model.Authorization;
import ru.vuchobe.model.AuthorizationToken;
import ru.vuchobe.util.threadUtil.ThreadService;

public final class AuthService {
    public static final String name = "Сервиз авторизации";

    public static final String loginIsEmptyMsg = "Логин не может быть пустым";
    public static final String passwordIsEmptyMsg = "Пароль не может быть пустым";
    public static final String loginIsNotEmailMsg = "Введите электронную почту";
    public static final String otherBodyNull = "Неверный логин или пароль";

    public static void logon(
            @Nullable String login,
            @Nullable String password,
            @Nullable ManagerService.AsyncResult<Boolean, AuthException> result
    ) {
        final ManagerService.AsyncResult<Boolean, AuthException> resultFunc = (result != null) ? result : (r, e) -> {
        };

        ArrayList<String> loginErr = new ArrayList<>();
        ArrayList<String> passwordErr = new ArrayList<>();
        ArrayList<String> otherErr = new ArrayList<>();

        //default error
        if (login == null || login.trim().isEmpty()) {
            loginErr.add(loginIsEmptyMsg);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            loginErr.add(loginIsNotEmailMsg);
        }
        if (password == null || password.isEmpty()) {
            passwordErr.add(passwordIsEmptyMsg);
        }
        if (!loginErr.isEmpty() || !passwordErr.isEmpty() || !otherErr.isEmpty()) {
            ThreadService.get().asyncMain(() -> resultFunc.run(false, new AuthException(loginErr, passwordErr, otherErr)));
            return;
        }

        //default ok
        login = login.trim();
        NetworkManager.objectObject(
                "/auth/login",
                NetworkManager.Method.POST,
                NetworkManager.mimeJson,
                Authorization.class,
                new Authorization(login, password),
                AuthorizationToken.class,
                (body, error) -> ThreadService.get().asyncMain(() -> {
                    if (error != null) {
                        resultFunc.run(false, new AuthException(null, null, new String[]{error.getMessage()}));
                        return;
                    }
                    if (body == null || body.getAccessToken().isEmpty()) {
                        resultFunc.run(false, new AuthException(null, null, new String[]{otherBodyNull}));
                        return;
                    }
                    NetworkManager.token = body.getAccessToken();
                    resultFunc.run(true, null);
                })

        );
    }

    public static class AuthException extends ManagerService.ServiceException {

        private String[] loginMessages;
        private String[] passwordMessages;
        private String[] otherMessages;

        private static String[] EMPTY_ARRAY = new String[0];

        public AuthException(List<String> loginMsgs, List<String> passwordMsgs, List<String> otherMsgs) {

            this(
                    (loginMsgs != null) ? loginMsgs.toArray(EMPTY_ARRAY) : EMPTY_ARRAY,
                    (passwordMsgs != null) ? passwordMsgs.toArray(EMPTY_ARRAY) : EMPTY_ARRAY,
                    (otherMsgs != null) ? otherMsgs.toArray(EMPTY_ARRAY) : EMPTY_ARRAY
            );
        }

        public AuthException(String[] loginMsgs, String[] passwordMsgs, String[] otherMsgs) {
            this.loginMessages = (loginMsgs != null) ? loginMsgs : EMPTY_ARRAY;
            this.passwordMessages = (passwordMsgs != null) ? passwordMsgs : EMPTY_ARRAY;
            this.otherMessages = (otherMsgs != null) ? otherMsgs : EMPTY_ARRAY;
        }

        @Override
        public @NonNull
        String getServiceName() {
            return AuthService.name;
        }

        public String[] getLoginMessages() {
            return loginMessages;
        }

        public String[] getPasswordMessages() {
            return passwordMessages;
        }

        public String[] getOtherMessages() {
            return otherMessages;
        }
    }
}
