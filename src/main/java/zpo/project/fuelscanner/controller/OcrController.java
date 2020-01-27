package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.service.OcrService;
import zpo.project.fuelscanner.service.FileService;
import zpo.project.fuelscanner.service.ReceiptService;
import zpo.project.fuelscanner.service.UserService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin
public class OcrController {
    private OcrService ocrService;
    private ReceiptService receiptService;
    private FileService fileService;
    private UserService userService;

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
    public ResponseEntity<String> doOcr(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        File localFile = fileService.copyFile(inputStream);
        String content = ocrService.doOcr(localFile);

        //For testing: receipt is owned by User1
        //Later change: receipt is owned by logged user
        Receipt receipt = receiptService.createReceipt(
                new Receipt(0L, "", content, LocalDate.now(), 0.0, 0.0, 0.0,
                        userService.getUser(1L)));

        receipt = receiptService.find(receipt);
        receiptService.updateReceipt(receipt);

        return new ResponseEntity<String>(file.getOriginalFilename(), HttpStatus.OK);
    }
}
