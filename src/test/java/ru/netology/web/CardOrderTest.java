package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {
    @Test
    void shouldOrderInAdministrativeCenter() {
        open("http://localhost:9999");
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue("Санкт-Петербург");
        form.$("[data-test-id='name'] input").setValue("Иванов Иван");
        form.$("[data-test-id='phone'] input").setValue("+79991234567");
        form.$("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).waitUntil(visible, 15000);
    }

    @Test
    void shouldOrderNotInAdministrativeCenter() {
        open("http://localhost:9999");
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue("Череповец");
        form.$("[data-test-id='name'] input").setValue("Иванов Иван");
        form.$("[data-test-id='phone'] input").setValue("+79991234567");
        form.$("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна"));
    }
}