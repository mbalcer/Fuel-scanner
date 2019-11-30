package zpo.project.fuelscanner.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.config.OcrConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

@Service
public class OcrService {

    public String doOcr(String url) {
        try {
            URL imageFile = new URL(url);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ITesseract instance = new OcrConfig().config();
            return instance.doOCR(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
