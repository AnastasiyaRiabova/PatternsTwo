package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(registeredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(exactText("  Личный кабинет"));
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(notRegisteredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(blockedUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! " + "Пользователь заблокирован"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] .input__box .input__control").val(wrongLogin);
        $("[data-test-id='password'] .input__box .input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] .input__box .input__control").val(registeredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
    }

    @Test
    @DisplayName("Should get error message if login and password are empty")
    void shouldGetErrorIfLoginAndPasswordEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val();
        $("[data-test-id='password'] .input__box .input__control").val();
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
        $("[data-test-id='password'].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should get error message if login is empty")
    void shouldGetErrorIfLoginEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val();
        $("[data-test-id='password'] .input__box .input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should get error message if password is empty")
    void shouldGetErrorIfPasswordEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(registeredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val();
        $("[data-test-id='action-login']").click();
        $("[data-test-id='password'].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

}
