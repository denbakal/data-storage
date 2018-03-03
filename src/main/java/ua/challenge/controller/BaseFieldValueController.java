package ua.challenge.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.challenge.service.BaseFieldValueService;
import ua.challenge.type.StoreType;

import java.util.List;
import java.util.Map;

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
        } else if (StoreType.CASSANDRA == type) {
        } else {
            this.baseFieldValueService.storeData(values);
        }
    }

    @PostMapping(value = "/field-values/column-data-store")
    public void storeColumnData(@RequestBody List<Map<String, String>> data) {
        this.baseFieldValueService.storeColumnData(data);
    }
}
