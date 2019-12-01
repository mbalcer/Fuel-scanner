package zpo.project.fuelscanner.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zpo.project.fuelscanner.FuelScannerApplication;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OcrConfigTest {

    @Autowired
    OcrConfig ocrConfig;

    @Test
    void config1() {
        ITesseract iTesseract = ocrConfig.config();
        assertNotNull(iTesseract);
    }

    @Test
    void config2() {
        String expectedResult = "alpha";
        String givenResult = getStringFromImg("src\\test\\resources\\1.png")
                .replaceAll("\\s","");
        assertEquals(expectedResult, givenResult);
    }

    @Test
    void config3() {
        String expectedResult = "ŻÓŁĆ";
        String givenResult = getStringFromImg("src\\test\\resources\\2.png")
                .replaceAll("\\s","");
        assertEquals(expectedResult, givenResult);
    }

    String getStringFromImg(String pathString) {
        ITesseract iTesseract = ocrConfig.config();
        try {
            return iTesseract.doOCR(new File(pathString));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "";
    }
}