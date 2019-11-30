package zpo.project.fuelscanner.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;

public class OcrConfig {

    @Value("${languages.path}")
    private String languagesPath;

    public ITesseract config( ){
        ITesseract iTesseract = new Tesseract();
        iTesseract.setDatapath(languagesPath);
        iTesseract.setLanguage("pol");
        return iTesseract;
    }
}
