package zpo.project.fuelscanner.service;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import zpo.project.fuelscanner.model.FuellingDateSummary;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.ReceiptRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    public List<Receipt> getReceipts() {
        return receiptRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    public Receipt getReceipt(long id) {
        return receiptRepository.findById(id).get();
    }

    public Receipt createReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public void updateReceipt(Receipt receipt) {
        receiptRepository.save(receipt);
    }

    public void deleteReceipt(long id) {
        receiptRepository.deleteById(id);
    }


    //From FuelSumService
    public Receipt find(Receipt receipt) {
        //  String line = "DBREF  1A1F A  102  VERVA 98  10 L  SUMA PLN 100 zł GAZ LPG  20 L  SUMA PLN 200 DSAFSADFSADFSADF VERVA 95 30 L  SUMA PLN 300 200";
        //System.out.println(Arrays.toString(getCount(line).toArray()));
        getCount(receipt.getContent()).entries()
                .stream()
                .forEach(k-> {
                    Double cost = BigDecimal.valueOf(k.getKey() * k.getValue())
                            .setScale(2, RoundingMode.FLOOR)
                            .doubleValue();

                    receipt.setLitres(k.getKey());
                    receipt.setPricePerLitres(k.getValue());
                    receipt.setCost(cost);
                    receipt.setReceiptLocalDate(getDate(receipt.getContent()));
                    //Merging -> save not here but in doOCR()
                    //fuelSumRepo.save(new FuelSum(0l, k.getKey(), k.getValue(), cost));
                });
        return receipt;
    }
    private static MultiValuedMap<Double,Double> getCount(final String str) {
        final Pattern finalPrice = Pattern.compile("\\s*PLN.?([\\d+]{1,5}\\.?[\\d]{0,5})",Pattern.DOTALL);

        //final Pattern litres = Pattern.compile("\\b\\S?LEJ \\S?AP\\S?DOW\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bVERVA 95\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bDIESEL\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bVERVA 98\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bBENZYNA\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bGAZ LPG\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})"); // w tym przypadku dodamy wiecej nazw
        final Pattern litres = Pattern.compile("([\\d+]{1,5}\\.?[\\d]{0,5})\\s?[L]?\\s?[x+*]+",Pattern.CASE_INSENSITIVE);
        final Pattern pricePerLitres = Pattern.compile("[x+*]+\\s*([\\d+]{1,5}\\.?[\\d]{0,5})",Pattern.CASE_INSENSITIVE);
        final MultiValuedMap<Double,Double> countValues = new ArrayListValuedHashMap<>();
        final Matcher matcher = finalPrice.matcher(str); //price
        final Matcher matcher2 = litres.matcher(str); //litres
        final Matcher matcher3 = pricePerLitres.matcher(str); //pricePerLitres
        while (matcher2.find() && matcher3.find()) {
            System.out.println(matcher3.group());
            // countValues.put(Double.parseDouble(matcher2.group().replaceAll("\\b\\S?LEJ \\S?AP\\S?DOW\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bBENZYNA\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bVERVA 98\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bDIESEL\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bGAZ LPG\\D*\\(?\\d?\\)?\\s*\\D*\\b\\b|\\bVERVA 95\\D*\\(?\\d?\\)?\\s*\\D*\\b","")),Double.parseDouble(matcher3.group(1)));// w tym przypadku dodamy wiecej nazw
            countValues.put(Double.parseDouble(matcher2.group().replaceAll("\\s?[L]?\\s?[x+*]+","")),Double.parseDouble(matcher3.group(1)));

        }
        return countValues;
    }
    private static LocalDate getDate(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final Pattern date = Pattern.compile("\\d{2}-\\d{2}-\\d{4}",Pattern.CASE_INSENSITIVE);
        LocalDate foundDate = null;
        Matcher dateMatcher = date.matcher(str);
        while(dateMatcher.find())
        {
            foundDate = LocalDate.parse(dateMatcher.group(),formatter);
        }
        return foundDate;
    }


    //From FuellingService
    public double getAllReceiptLitresByUser(User user) {
        return receiptRepository.findAllByUser(user)
                .stream()
                .mapToDouble(receipt ->
                        receipt.getLitres())
                .sum();
    }

    public double getAllReceiptCostByUser(User user) {
        return receiptRepository.findAllByUser(user)
                .stream()
                .mapToDouble(receipt ->
                        receipt.getCost())
                .sum();
    }

    public List<FuellingDateSummary> getFuellingDateSummaryByUserGroupedByYearMonth(User user) {
        return receiptRepository.findAllByUser(user)
                .stream()
                .collect(Collectors.groupingBy(receipt -> YearMonth.of(receipt.getReceiptLocalDate().getYear(), receipt.getReceiptLocalDate().getMonth())))
                .entrySet()
                .stream()
                .map(o -> new FuellingDateSummary(o.getKey(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> value.getLitres())
                                .sum(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> value.getCost())
                                .sum(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> (value.getCost() / value.getLitres()))
                                .min().orElse(0),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> (value.getCost() / value.getLitres()))
                                .max().orElse(0)))
                .collect(Collectors.toList());
    }

    public List<Receipt> getAllReceiptByUserAndFuellingLocalDateBetween(User user, LocalDate start, LocalDate end) {
        return receiptRepository.findAllByUserAndReceiptLocalDateBetween(user, start, end);
    }

}
