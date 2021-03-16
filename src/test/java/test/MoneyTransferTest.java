package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void firstStage() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldRemittanceFromSecondToFirst() {
        val validAmount = 100;
        val dashboardPage = new DashboardPage();
        dashboardPage.dashboardPage();
        val balanceFirstCard = dashboardPage.getFirstCardBalance() + validAmount;
        val balanceSecondCard = dashboardPage.getSecondCardBalance() - validAmount;
        val remittance = dashboardPage.topUpFirstCard();
        remittance.remittance();
        val cardInfo = DataHelper.getSecondCard();
        remittance.getTransaction(cardInfo, validAmount);
        assertEquals(balanceFirstCard, dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldRemittanceFromFirstToSecond() {
        val validAmount = 100;
        val dashboardPage = new DashboardPage();
        dashboardPage.dashboardPage();
        val balanceSecondCard = dashboardPage.getSecondCardBalance() + validAmount;
        val balanceFirstCard = dashboardPage.getFirstCardBalance() - validAmount;
        val remittance = dashboardPage.topUpSecondCard();
        remittance.remittance();
        val cardInfo = DataHelper.getFirstCard();
        remittance.getTransaction(cardInfo, validAmount);
        assertEquals(balanceFirstCard, dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldRemittanceInvalidAmount() {
        val invalidAmount = 20000;
        val dashboardPage = new DashboardPage();
        dashboardPage.dashboardPage();
        val remittance = dashboardPage.topUpFirstCard();
        remittance.remittance();
        val cardInfo = DataHelper.getSecondCard();
        remittance.getTransaction(cardInfo, invalidAmount);
        remittance.moneyTransferError();
    }

    @Test
    void shouldReturn() {
        val dashboardPage = new DashboardPage();
        dashboardPage.dashboardPage();
        val remittance = dashboardPage.topUpFirstCard();
        remittance.remittance();
        remittance.getCancel();
    }
}