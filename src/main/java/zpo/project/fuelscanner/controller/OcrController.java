package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zpo.project.fuelscanner.model.Graphic;
import zpo.project.fuelscanner.service.FuelSumService;
import zpo.project.fuelscanner.service.GraphicService;
import zpo.project.fuelscanner.service.OcrService;
import zpo.project.fuelscanner.service.FileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin
public class OcrController {
    private OcrService ocrService;
    private GraphicService graphicService;
    private FuelSumService fuelSumService;
    private FileService fileService;

    @Autowired
    public OcrController(OcrService ocrService, GraphicService graphicService, FuelSumService fuelSumService, FileService fileService) {
        this.ocrService = ocrService;
        this.graphicService = graphicService;
        this.fuelSumService = fuelSumService;
        this.fileService = fileService;
    }

    @PostMapping("/url")
    public Graphic doOcr(@RequestBody Graphic graphic) {
        String content = ocrService.doOcr(graphic.getUrl());
        graphic.setContent(content);
        graphic = graphicService.createGraphic(graphic);
        fuelSumService.find(content);
        return graphic;
    }

    @PostMapping("/file")
    public ResponseEntity<String> doOcr(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        File localFile = fileService.copyFile(inputStream);
        String content = ocrService.doOcr(localFile);
        fuelSumService.find(content);

        return new ResponseEntity<String>(file.getOriginalFilename(), HttpStatus.OK);
    }
}
