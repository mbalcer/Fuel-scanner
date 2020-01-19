package zpo.project.fuelscanner.service;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.FuelSum;
import zpo.project.fuelscanner.repository.FuelSumRepo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FuelSumService {
    @Autowired
    private FuelSumRepo fuelSumRepo;

    @PostConstruct
    public void init() {

        FuelSum fuelSum = new FuelSum(0l,3.0,2.0,150.0);
        fuelSumRepo.save(fuelSum);
    }
    public List<FuelSum> getFuelSums() {
        return fuelSumRepo.findAll(Sort.by(Sort.Order.desc("id")));
    }
    public FuelSum getFuelSum(long id) {
        return fuelSumRepo.getOne(id);
    }
    public void createFuelSum(FuelSum fuelSum) {
        fuelSum =  fuelSumRepo.save(fuelSum);
    }
    public void updateFuelSum(FuelSum fuelSum) { fuelSum = fuelSumRepo.save(fuelSum); }
    public void deleteFuelSum(long id) {
        fuelSumRepo.deleteById(id);
    }
    public void find(String line) {
      //  String line = "DBREF  1A1F A  102  VERVA 98  10 L  SUMA PLN 100 zł GAZ LPG  20 L  SUMA PLN 200 DSAFSADFSADFSADF VERVA 95 30 L  SUMA PLN 300 200";
        //System.out.println(Arrays.toString(getCount(line).toArray()));
        getCount(line).entries()
                .stream()
                .forEach(k-> {
                            Double cost = BigDecimal.valueOf(k.getKey() * k.getValue())
                                    .setScale(2, RoundingMode.FLOOR)
                                    .doubleValue();
                            fuelSumRepo.save(new FuelSum(0l, k.getKey(), k.getValue(), cost));
                        });
    }
    private static MultiValuedMap<Double,Double> getCount(final String str) {
        final Pattern finalPrice = Pattern.compile("\\s*PLN.?([\\d+]{1,5}\\.?[\\d]{0,5})",Pattern.DOTALL);

        //final Pattern litres = Pattern.compile("\\b\\S?LEJ \\S?AP\\S?DOW\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bVERVA 95\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bDIESEL\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bVERVA 98\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bBENZYNA\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})|\\bGAZ LPG\\D*\\(?\\d?\\)?\\s*\\D*\\b\\s*([\\d+]{1,5}\\.?[\\d]{0,5})"); // w tym przypadku dodamy wiecej nazw
        final Pattern litres = Pattern.compile("([\\d+]{1,5}\\.?[\\d]{0,5})\\s?[L]?\\s?[x+]+",Pattern.CASE_INSENSITIVE);
        final Pattern pricePerLitres = Pattern.compile("[x+]+\\s*([\\d+]{1,5}\\.?[\\d]{0,5})",Pattern.CASE_INSENSITIVE);
        final MultiValuedMap<Double,Double> countValues = new ArrayListValuedHashMap<>();
        final Matcher matcher = finalPrice.matcher(str); //price
        final Matcher matcher2 = litres.matcher(str); //litres
        final Matcher matcher3 = pricePerLitres.matcher(str); //pricePerLitres
        while (matcher2.find() && matcher3.find()) {
            System.out.println(matcher3.group());
           // countValues.put(Double.parseDouble(matcher2.group().replaceAll("\\b\\S?LEJ \\S?AP\\S?DOW\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bBENZYNA\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bVERVA 98\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bDIESEL\\D*\\(?\\d?\\)?\\s*\\D*\\b|\\bGAZ LPG\\D*\\(?\\d?\\)?\\s*\\D*\\b\\b|\\bVERVA 95\\D*\\(?\\d?\\)?\\s*\\D*\\b","")),Double.parseDouble(matcher3.group(1)));// w tym przypadku dodamy wiecej nazw
             countValues.put(Double.parseDouble(matcher2.group().replaceAll("\\s?[L]?\\s?[x+]+","")),Double.parseDouble(matcher3.group(1)));

        }
        return countValues;
    }

}

