package ua.challenge.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.challenge.service.BaseFieldValueService;
import ua.challenge.type.StoreType;

import java.util.List;

@RestController
@Log4j
public class BaseFieldValueController {
    @Autowired
    private BaseFieldValueService baseFieldValueService;

    @PostMapping(value = "/field-values")
//    @PostMapping(value = "/field-values", consumes = "text/plain")
    public void storeData(@RequestBody List<String> values,
                          @RequestParam(required = false) StoreType type) {
        if (StoreType.CELL == type) {
            this.baseFieldValueService.storeCellData(values);
        } else if (StoreType.JSON == type) {
            this.baseFieldValueService.storeJsonData(values);
        } else {
            this.baseFieldValueService.storeData(values);
        }
    }
}
