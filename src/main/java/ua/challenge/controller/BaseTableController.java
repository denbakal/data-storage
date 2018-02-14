package ua.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.challenge.dto.BaseTableDto;
import ua.challenge.service.BaseTableService;

import java.time.LocalDateTime;

@RestController
public class BaseTableController {
    @Autowired
    private BaseTableService baseTableService;

    @PostMapping(value = "/base-tables")
    public void post() {
        BaseTableDto baseTableDto = new BaseTableDto();
        baseTableDto.setVersionTable(1L);
        baseTableDto.setValidFrom(LocalDateTime.now());
        baseTableDto.setValidTo(LocalDateTime.MAX);

        baseTableService.save(baseTableDto);
    }
}
