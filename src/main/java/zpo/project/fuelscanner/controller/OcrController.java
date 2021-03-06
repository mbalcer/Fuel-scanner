package zpo.project.fuelscanner.controller;

import org.bytedeco.javacpp.opencv_core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zpo.project.fuelscanner.config.ReceiptScanning;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.service.FileService;
import zpo.project.fuelscanner.service.OcrService;
import zpo.project.fuelscanner.service.ReceiptService;
import zpo.project.fuelscanner.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;


@RestController
@RequestMapping("/api/ocr")
@CrossOrigin
public class OcrController {
    private OcrService ocrService;
    private ReceiptService receiptService;
    private FileService fileService;
    private UserService userService;
    private boolean wantToRotate = false;

    @Autowired
    public ReceiptScanning receiptScanning = new ReceiptScanning();
    @Autowired
    public OcrController(OcrService ocrService, ReceiptService receiptService,  FileService fileService, UserService userService) {
        this.ocrService = ocrService;
        this.receiptService = receiptService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/url/{turned}")
    public Receipt doOcr(@RequestBody String url, @PathVariable boolean turned) {
        this.wantToRotate = turned;
        Receipt receipt = new Receipt();
        receipt.setUrl(url);
        String content = ocrService.doOcr(receipt.getUrl());
        receipt.setContent(content);
        receipt = receiptService.find(receipt);
        try {
            URL imageFile = new URL(url);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            File f = new File(System.getProperty("user.home")+File.separator+"forOCR.jpeg");
            ImageIO.write(bufferedImage,"jpeg",f);
            checkReceipt(receipt,f.getPath());
            f.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receipt;
    }

    @PostMapping("/file/{turned}")
    public Receipt doOcr(@RequestParam("file") MultipartFile file, @PathVariable boolean turned) throws IOException {
        this.wantToRotate = turned;
        InputStream inputStream = file.getInputStream();
        File localFile = fileService.copyFile(inputStream);
        String content = ocrService.doOcr(localFile);
        //For testing: receipt is owned by User1
        //Later change: receipt is owned by logged user
        Receipt receipt = new Receipt(0L, localFile.getAbsolutePath(), content, null, 0.0, 0.0, 0.0, userService.getUser(1L));

        receipt = receiptService.find(receipt);
        checkReceipt(receipt, localFile.getPath());
        return receipt;
    }
    public void checkReceipt(Receipt receipt,String path)
    {

        if(receipt.getLitres()==0.0)
        {
            System.out.println("jesteeeeem");
            opencv_core.IplImage resizeIMG = receiptScanning.beforeSmooth(path);
            File afterScanning = scanning(resizeIMG);
            String content = ocrService.doOcr(afterScanning);
            receiptService.getCount(content).entries().stream().forEach(l-> {
                Double cost = BigDecimal.valueOf(l.getKey() * l.getValue())
                        .setScale(2, RoundingMode.FLOOR)
                        .doubleValue();
                receipt.setLitres(l.getKey());
                receipt.setPricePerLitres(l.getValue());
                receipt.setCost(cost);
                    }
            );

        }
        if(receipt.getReceiptLocalDate()==null)
        {
            opencv_core.IplImage resizeIMG = receiptScanning.beforeSmooth(path);
            File afterScanning = scanning(resizeIMG);
            String content = ocrService.doOcr(afterScanning);
            receipt.setReceiptLocalDate(receiptService.getDate(content));
        }
    }
    public File scanning(opencv_core.IplImage resizeIMG){
        if(wantToRotate == true){
        opencv_core.IplImage squareEdgeDetectionImage = receiptScanning.squareEdgeDetection(resizeIMG,30);
        opencv_core.CvSeq findedSquareIMG = receiptScanning.findLargestSquare(squareEdgeDetectionImage);
        opencv_core.IplImage afterTransformIMG = receiptScanning.applyPerspectiveTransformThresholdOnOriginalImage(resizeIMG,findedSquareIMG,30);
        File afterRotatingAndSmoothing = receiptScanning.cleanImageSmoothingForOCR(afterTransformIMG);
       // opencv_core.IplImage afterSmoothingIMG =  cvLoadImage(afterSmoothing.getAbsolutePath());
      //  File finalImage = receiptScanning.rotateImage(afterSmoothingIMG);
        return afterRotatingAndSmoothing;
        }
        else{
            File afterSmoothing = receiptScanning.cleanImageSmoothingForOCR(resizeIMG);
            return afterSmoothing;
        }
    }
}
