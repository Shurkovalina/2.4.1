package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class Remittance {
    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement depositAmount = $("[data-test-id=amount] input");
    private SelenideElement fromNumber = $("[data-test-id=from] input");
    private SelenideElement buttonTransfer = $(withText("Пополнить"));
    private SelenideElement cancelTransfer = $(withText("Отмена"));

    public void remittance() {
        heading.shouldBe(visible);
    }

    public DashboardPage getTransaction(DataHelper.getCardInfo getCardInfo, int amount) {
        depositAmount.setValue(String.valueOf(amount));
        fromNumber.setValue(getCardInfo.getNumber());
        buttonTransfer.click();
        return new DashboardPage();
    }

    public DashboardPage getCancel() {
        cancelTransfer.click();
        return new DashboardPage();
    }

    public void moneyTransferError() {
        $(withText("Ошибка")).shouldBe(Condition.visible);
    }
}
