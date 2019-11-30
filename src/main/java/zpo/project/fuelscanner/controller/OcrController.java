package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.Graphic;
import zpo.project.fuelscanner.service.FuelSumService;
import zpo.project.fuelscanner.service.GraphicService;
import zpo.project.fuelscanner.service.OcrService;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {
    private OcrService ocrService;
    private GraphicService graphicService;
    private FuelSumService fuelSumService;

    @Autowired
    public OcrController(OcrService ocrService, GraphicService graphicService, FuelSumService fuelSumService) {
        this.ocrService = ocrService;
        this.graphicService = graphicService;
        this.fuelSumService = fuelSumService;
    }

    @PostMapping
    public Graphic doOcr(@RequestBody Graphic graphic) {
        String content = ocrService.doOcr(graphic.getUrl());
        graphic.setContent(content);
        graphic = graphicService.createGraphic(graphic);
        fuelSumService.find(content);
        return graphic;
    }
}
