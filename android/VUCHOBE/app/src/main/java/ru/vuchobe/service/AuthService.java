package ru.vuchobe.service;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.vuchobe.model.Authorization;
import ru.vuchobe.model.AuthorizationToken;
import ru.vuchobe.model.Registration;
import ru.vuchobe.util.threadUtil.ThreadService;
import ru.vuchobe.util.threadUtil.ThreadTask;

public final class AuthService {
    public static final String name = "Сервиз авторизации";

    public static final String loginIsEmptyMsg = "Логин не может быть пустым";
    public static final String emailIsEmptyMsg = "Введите электонную почту";
    public static final String passwordIsEmptyMsg = "Пароль не может быть пустым";
    public static final String loginIsNotEmailMsg = "Введите электронную почту";
    public static final String otherBodyNull = "Неверный логин или пароль";
    public static final String passwordIsNonEquals = "Пароли не совпадают";
    public static final String nameIsEmptyMsg = "Имя не может быть пустым";

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
            ThreadService.get().asyncMain((ThreadTask taskMain) ->
                    resultFunc.run(false, new AuthException(loginErr, passwordErr, otherErr))
            );
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
                (body, error) -> ThreadService.get().asyncMain((ThreadTask taskMain) -> {
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

    public static void registration(
            @Nullable String email,
            @Nullable String name,
            @Nullable String password,
            @Nullable String repassword,
            @Nullable ManagerService.AsyncResult<Boolean, RegException> result
    ) {
        final ManagerService.AsyncResult<Boolean, RegException> resultFunc = (result != null) ? result : (r, e) -> {
        };

        email = (email != null) ? email.trim() : "";
        name = (name != null) ? name.trim() : "";
        password = (password != null) ? password : "";
        repassword = (repassword != null) ? repassword : "";

        EnumMap<RegField, String> errorMsgs = new EnumMap<>(RegField.class);

        //find error
        if (email.isEmpty()) {
            errorMsgs.put(RegField.EMAIL, emailIsEmptyMsg);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMsgs.put(RegField.EMAIL, emailIsEmptyMsg);
        }

        if (name.isEmpty()) {
            errorMsgs.put(RegField.NAME, nameIsEmptyMsg);
        }

        if (password.isEmpty()) {
            errorMsgs.put(RegField.PASSWORD, passwordIsEmptyMsg);
        } else if (repassword.isEmpty()) {
            errorMsgs.put(RegField.REPASSWORD, passwordIsEmptyMsg);
        } else if (!password.equals(repassword)) {
            errorMsgs.put(RegField.REPASSWORD, passwordIsNonEquals);
        }

        if (!errorMsgs.isEmpty()) {
            ThreadService.get().asyncMain((ThreadTask taskMain) ->
                    resultFunc.run(false, new RegException(errorMsgs))
            );
            return;
        }

        NetworkManager.objectObject(
                "/auth/registration/STUDENT",
                NetworkManager.Method.POST,
                NetworkManager.mimeJson,
                Registration.class,
                new Registration(name, email, password),
                Integer.class,
                (body, error) -> ThreadService.get().asyncMain((ThreadTask taskMain) -> {
                    if (error != null) {
                        resultFunc.run(false, new RegException(error));
                        return;
                    }
                    resultFunc.run(true, null);
                })

        );
    }

    public enum RegField {
        EMAIL, NAME, PASSWORD, REPASSWORD, OTHER
    }

    public static class RegException extends ManagerService.ServiceException {
        private Map<RegField, String> errorMsgs = Collections.EMPTY_MAP;

        public RegException(EnumMap<RegField, String> errorMsgs) {
            this.errorMsgs = errorMsgs;
        }

        public RegException(@Nullable Throwable cause) {
            super(cause);
            if (cause != null) {
                errorMsgs = new EnumMap<>(RegField.class);
                errorMsgs.put(RegField.OTHER, cause.getMessage());
            }
        }

        @NonNull
        @Override
        public String getServiceName() {
            return AuthService.name;
        }

        public boolean isEmpty() {
            return errorMsgs.isEmpty();
        }

        public boolean isExist(RegField field) {
            return errorMsgs.containsKey(field);
        }

        public String getMessageFor(RegField field) {
            String result = errorMsgs.get(field);
            return (result != null) ? result : "";
        }
    }
}
