package ua.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.challenge.service.BaseFieldService;

@RestController
public class BaseFieldController {
    @Autowired
    private BaseFieldService baseFieldService;

    @PostMapping("/fields")
    public void generate(@RequestParam("generate") boolean isGenerate,
                        @RequestParam("count") int count) {
        if (isGenerate) {
            baseFieldService.generate(count);
        }
    }

    @DeleteMapping("/fields")
    public void delete() {
        this.baseFieldService.delete();
    }
}
