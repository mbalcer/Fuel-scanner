package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.Graphic;
import zpo.project.fuelscanner.service.OcrService;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {
    private OcrService ocrService;

    @Autowired
    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping
    public String doOcr(@RequestBody Graphic graphic) {
        String content = ocrService.doOcr(graphic.getUrl());
        graphic.setContent(content);
        //TODO save to database
        return content;
    }
}
