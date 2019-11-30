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
    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping //@RequestBody Graphic graphic - usunalem #matkuc003 -testuje 
    public String doOcr() {
        String content = ocrService.doOcr("https://www.schronisko-piotrkow.pl/wp-content/uploads/2017/06/paragonzapaliwotippi.jpg");
        //graphic.setContent(content);
        System.out.println(content);
//        graphicService.createGraphic(new Graphic(0l,null,content));
        fuelSumService.find(content);
        //TODO save to database
        return content;
    }
}
