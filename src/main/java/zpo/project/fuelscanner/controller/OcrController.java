package zpo.project.fuelscanner.controller;

import org.bytedeco.javacpp.opencv_core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import zpo.project.fuelscanner.config.ReceiptScanning;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.service.OcrService;
import zpo.project.fuelscanner.service.FileService;
import zpo.project.fuelscanner.service.ReceiptService;
import zpo.project.fuelscanner.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin
public class OcrController {
    private OcrService ocrService;
    private ReceiptService receiptService;
    private FileService fileService;
    private UserService userService;

    @Autowired
    public ReceiptScanning receiptScanning = new ReceiptScanning();
    @Autowired
    public OcrController(OcrService ocrService, ReceiptService receiptService,  FileService fileService, UserService userService) {
        this.ocrService = ocrService;
        this.receiptService = receiptService;
        this.fileService = fileService;
        this.userService = userService;
    }


//    @PostMapping("/url")
//    public Graphic doOcr(@RequestBody Graphic graphic) {
//        String content = ocrService.doOcr(graphic.getUrl());
//        graphic.setContent(content);
//        graphic = graphicService.createGraphic(graphic);
//        fuelSumService.find(content);
//        return graphic;
//    }

    @PostMapping("/url")
    public Receipt doOcr(@RequestBody String url) {
        Receipt receipt = new Receipt();
        receipt.setUrl(url);
        String content = ocrService.doOcr(receipt.getUrl());
        receipt.setContent(content);
        receipt = receiptService.find(receipt);
        receipt = receiptService.createReceipt(receipt);
        try {
            URL imageFile = new URL(url);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            File f = new File(System.getProperty("user.home")+File.separator+"forOCR.jpeg");
            ImageIO.write(bufferedImage,"jpeg",f);
            checkReceipt(f.getPath());
            f.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receipt;
    }


//    @PostMapping("/file")
//    public ResponseEntity<String> doOcr(@RequestParam("file") MultipartFile file) throws IOException {
//        InputStream inputStream = file.getInputStream();
//        File localFile = fileService.copyFile(inputStream);
//        String content = ocrService.doOcr(localFile);
//        fuelSumService.find(content);
//
//        return new ResponseEntity<String>(file.getOriginalFilename(), HttpStatus.OK);
//    }

    @PostMapping("/file")
    public Receipt doOcr(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        File localFile = fileService.copyFile(inputStream);
        String content = ocrService.doOcr(localFile);
        //For testing: receipt is owned by User1
        //Later change: receipt is owned by logged user
        Receipt receipt = receiptService.createReceipt(
                new Receipt(0L, "", content, null, 0.0, 0.0, 0.0,
                        userService.getUser(1L)));

        receipt = receiptService.find(receipt);
        receiptService.updateReceipt(receipt);
        checkReceipt(localFile.getPath());
        return receipt;
    }
    public void checkReceipt(String path)
    {
        long lastReceipt = receiptService.getReceipts().size();

        if(receiptService.getReceipt(lastReceipt).getLitres()==0.0)
        {
            opencv_core.IplImage resizeIMG = receiptScanning.beforeSmooth(path);
            File afterScanning = scanning(resizeIMG);
            String content = ocrService.doOcr(afterScanning);
            Receipt newReceipt = receiptService.getReceipt(lastReceipt);
            receiptService.getCount(content).entries().stream().forEach(l-> {
                Double cost = BigDecimal.valueOf(l.getKey() * l.getValue())
                        .setScale(2, RoundingMode.FLOOR)
                        .doubleValue();
                newReceipt.setLitres(l.getKey());
                newReceipt.setPricePerLitres(l.getValue());
                newReceipt.setCost(cost);
                    }
            );
            receiptService.updateReceipt(newReceipt);

        }
        if(receiptService.getReceipt(lastReceipt).getReceiptLocalDate()==null)
        {
            opencv_core.IplImage resizeIMG = receiptScanning.beforeSmooth(path);
            File afterScanning = scanning(resizeIMG);
            String content = ocrService.doOcr(afterScanning);
            Receipt newReceipt = receiptService.getReceipt(lastReceipt);
            newReceipt.setReceiptLocalDate(receiptService.getDate(content));
            receiptService.updateReceipt(newReceipt);
        }
    }
    public File scanning(opencv_core.IplImage resizeIMG){
        boolean wantToRotate = false;
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
