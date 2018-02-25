package ua.challenge.controller;

import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.challenge.service.BaseFieldValueService;

import java.util.List;

@RestController
@Log4j
public class BaseFieldValueController {
    @Autowired
    private BaseFieldValueService baseFieldValueService;

    @PostMapping(value = "/field-values")
//    @PostMapping(value = "/field-values", consumes = "text/plain")
    public void storeData(@RequestBody List<String> values) {
        log.info("Values: " + values.size());
        baseFieldValueService.storeData(values);
    }
}
