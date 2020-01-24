package zpo.project.fuelscanner.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zpo.project.fuelscanner.model.FuellingDateSummary;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.ReceiptRepository;
import zpo.project.fuelscanner.repository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReceiptServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    ReceiptService receiptService;

    @Test
    void getAllReceiptLitresByUser() {
        User u1 = userRepository.findById(1L).get();
        double givenResult = receiptService.getAllReceiptLitresByUser(u1);
        double expectedResult = 39.12;
        assertEquals(expectedResult, givenResult, 0.001);
    }

    @Test
    void getAllReceiptLitresByUser2() {
        User u2 = userRepository.findById(2L).get();
        double givenResult = receiptService.getAllReceiptLitresByUser(u2);
        double expectedResult = 44.76;
        assertEquals(expectedResult, givenResult, 0.001);
    }

    @Test
    void getAllReceiptCostByUser() {
        User u1 = userRepository.findById(1L).get();
        double givenResult = receiptService.getAllReceiptCostByUser(u1);
        double expectedResult = 196.57;
        assertEquals(expectedResult, givenResult, 0.001);
    }

    @Test
    void getFuellingDateSummaryByUserGroupedByYearMonth() {
        User u1 = userRepository.findById(1L).get();
        List<FuellingDateSummary> givenResult = receiptService.getFuellingDateSummaryByUserGroupedByYearMonth(u1);
        givenResult.get(0).setCost(round(givenResult.get(0).getCost(), 2));
        givenResult.get(0).setLitres(round(givenResult.get(0).getLitres(), 2));
        givenResult.get(0).setAverageCostPerLitre(round(givenResult.get(0).getAverageCostPerLitre(), 2));
        givenResult.get(0).setMaxCostPerLitre(round(givenResult.get(0).getMaxCostPerLitre(), 2));
        givenResult.get(0).setMinCostPerLitre(round(givenResult.get(0).getMinCostPerLitre(), 2));

        List<FuellingDateSummary> expectedResult = new ArrayList<>();
        expectedResult.add(new FuellingDateSummary(YearMonth.of(2019, 12), 39.12, 196.57,
                round(196.57 / 39.12, 2), round(42.46 / 8.95, 2), round(51.54 / 10.05, 2)));
        assertEquals(expectedResult, givenResult);
    }

    @Test
    void getAllReceiptByUserAndFuellingLocalDateBetween() {
        User u1 = userRepository.findById(1L).get();
        List<Receipt> givenResult = receiptRepository
                .findAllByUserAndReceiptLocalDateBetween(u1, LocalDate.now().minusDays(365), LocalDate.now().minusDays(0));
        List<Receipt> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(receiptService.getReceipt(1),
                receiptService.getReceipt(2), receiptService.getReceipt(3)));
        assertEquals(expectedResult, givenResult);
    }

    @Test
    void getAllReceiptByUserAndFuellingLocalDateBetween2() {
        User u1 = userRepository.findById(1L).get();
        int givenResult = receiptRepository
                .findAllByUserAndReceiptLocalDateBetween(u1, LocalDate.now().minusDays(12), LocalDate.now().minusDays(0))
                .size();
        int expectedResult = 1;
        assertEquals(expectedResult, givenResult);
    }

    @Test
    void getAllReceiptByUserAndFuellingLocalDateBetween3() {
        User u1 = userRepository.findById(1L).get();
        List<Receipt> givenResult = receiptRepository
                .findAllByUserAndReceiptLocalDateBetween(u1, LocalDate.now().minusDays(15), LocalDate.now().minusDays(0));
        List<Receipt> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(receiptService.getReceipt(1),
                receiptService.getReceipt(2)));
        assertNotEquals(expectedResult, givenResult);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}