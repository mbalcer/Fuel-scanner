package zpo.project.fuelscanner.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

@Service
public class OcrService {

    @Value("${languages.path}")
    private String languagesPath;

    public String doOcr(String url) {
        try {
            URL imageFile = new URL(url);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ITesseract instance = new Tesseract();
            instance.setDatapath(languagesPath);
            instance.setLanguage("pol");
            return instance.doOCR(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
