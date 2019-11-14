package ru.vuchobe.service;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.vuchobe.util.threadUtil.ThreadService;

public final class AuthService {
    public static final String name = "Сервиз авторизации";

    public static final String loginIsEmptyMsg = "Логин не может быть пустым";
    public static final String passwordIsEmptyMsg = "Пароль не может быть пустым";

    public static void logon(
            @Nullable String login,
            @Nullable String password,
            @Nullable ManagerService.AsyncResult<Boolean, AuthException> result
    ) {
        ArrayList<String> loginErr = new ArrayList<>();
        ArrayList<String> passwordErr = new ArrayList<>();
        ArrayList<String> otherErr = new ArrayList<>();

        //default error
        if (login == null || login.trim().isEmpty()) {
            loginErr.add(loginIsEmptyMsg);
        }
        if (password == null || password.isEmpty()) {
            passwordErr.add(passwordIsEmptyMsg);
        }
        if (!loginErr.isEmpty() || !passwordErr.isEmpty() || !otherErr.isEmpty()) {
            ThreadService.get().asyncMain(() -> result.result(false, new AuthException(loginErr, passwordErr, otherErr)));
            return;
        }

        //default ok
        login = login.trim();
        //TODO дописать
        ThreadService.get().asyncMain(() -> result.result(true, null));
    }

    public static class AuthException extends ManagerService.ServiceException {

        private String[] loginMessages;
        private String[] passwordMessages;
        private String[] otherMessages;

        public AuthException(List<String> loginMsgs, List<String> passwordMsgs, List<String> otherMsgs) {
            this(
                    loginMsgs.toArray(new String[loginMsgs.size()]),
                    passwordMsgs.toArray(new String[passwordMsgs.size()]),
                    otherMsgs.toArray(new String[otherMsgs.size()])
            );
        }

        public AuthException(String[] loginMsgs, String[] passwordMsgs, String[] otherMsgs) {
            this.loginMessages = loginMsgs;
            this.passwordMessages = passwordMsgs;
            this.otherMessages = otherMsgs;
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
