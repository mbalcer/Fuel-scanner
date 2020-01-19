package zpo.project.fuelscanner.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.config.OcrConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

@Service
public class OcrService {
    private OcrConfig ocrConfig;

    @Autowired
    public OcrService(OcrConfig ocrConfig) {
        this.ocrConfig = ocrConfig;
    }

    public String doOcr(String url) {
        try {
            URL imageFile = new URL(url);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ITesseract instance = ocrConfig.config();
            return instance.doOCR(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String doOcr(File file) {
        ITesseract instance = ocrConfig.config();
        try {
            return instance.doOCR(file);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "";
    }
}
