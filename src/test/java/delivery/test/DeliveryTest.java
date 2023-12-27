package delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import delivery.data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        String city = DataGenerator.generateCity("ru");
        String name = DataGenerator.generateName("ru");
        String phone = DataGenerator.generatePhone("ru");
        Selenide.$("[data-test-id=city] input").setValue(city);
        Selenide.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        Selenide.$("[data-test-id=date] input").setValue(firstMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(name);
        Selenide.$("[data-test-id=phone] input").setValue(phone);
        Selenide.$("[data-test-id=agreement]").click();
        Selenide.$(byText("Запланировать")).click();
        Selenide.$(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        // clear не работает и не очищает поле
        Selenide.$("[data-test-id=city] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Selenide.$("[data-test-id=name] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Selenide.$("[data-test-id=phone] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Selenide.$("[data-test-id=city] input").setValue(city);
        Selenide.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        Selenide.$("[data-test-id=date] input").setValue(secondMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(name);
        Selenide.$("[data-test-id=phone] input").setValue(phone);
        Selenide.$(byText("Запланировать")).click();
        Selenide.$(byText("Перепланировать")).click();
        Selenide.$(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }
}
