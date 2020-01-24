package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.service.ReceiptService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/receipt")
@CrossOrigin
public class ReceiptController {

    @Autowired
    ReceiptService receiptService;

    @GetMapping("/all")
    public List<Receipt> getReceipts() {
        List<Receipt> receiptList = receiptService.getReceipts();
        return receiptList;
    }

    @GetMapping("/{id}")
    public Receipt getReceipt(@PathVariable long id) {
        return receiptService.getReceipt(id);
    }

    @GetMapping("/last")
    public Receipt getLastFuel() {
        return receiptService.getReceipts().stream().limit(1).collect(Collectors.toList()).get(0);
    }

}
