package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

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
        SelenideElement date = $("[data-test-id='date'] input");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateOfMeeting = dateFormat.format(calendar.getTime());
//        LocalDate minDate = LocalDate.now();
//        minDate = minDate.plusDays(3);
//        String dateOfMeeting = dateFormat.format(minDate);
        date.setValue(dateOfMeeting);
        form.$("[data-test-id='phone'] input").setValue("+79991234567");
        form.$("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
//        $(withText("Успешно" + dateOfMeeting)).waitUntil(visible, 15000);
        $("[data-test-id='notification'] ").waitUntil(visible, 15000).shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dateOfMeeting));
    }

    @Test
    void shouldOrderNotInAdministrativeCenter() {
        open("http://localhost:9999");
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue("Череповец");
        form.$("[data-test-id='name'] input").setValue("Иванов Иван");
        SelenideElement date = $("[data-test-id='date'] input");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateOfMeeting = dateFormat.format(calendar.getTime());
        date.setValue(dateOfMeeting);
        form.$("[data-test-id='phone'] input").setValue("+79991234567");
        form.$("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }
}