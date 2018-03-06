package ua.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.challenge.aspect.Loggable;
import ua.challenge.dto.BaseFieldDto;
import ua.challenge.dto.BaseTableDto;
import ua.challenge.service.BaseFieldService;
import ua.challenge.service.BaseFieldValueService;
import ua.challenge.service.BaseTableService;
import ua.challenge.type.StoreType;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BaseTableController {
    @Autowired
    private BaseTableService baseTableService;

    @Autowired
    private BaseFieldService baseFieldService;

    @Autowired
    private BaseFieldValueService baseFieldValueService;

    @PostMapping(value = "/base-tables")
    public void post() {
        BaseTableDto baseTableDto = new BaseTableDto();
        baseTableDto.setVersionTable(1L);
        baseTableDto.setValidFrom(LocalDateTime.now());
        baseTableDto.setValidTo(LocalDateTime.MAX);

        baseTableService.save(baseTableDto);
    }

    @GetMapping(value = "/base-tables/{id}/fields")
    public List<BaseFieldDto> getFields(@PathVariable Long id) {
        return baseFieldService.getFieldsByBaseTableId(id);
    }

    @GetMapping("/base-tables/{id}/field-values")
    public List<String> getFieldValues(@PathVariable Long id,
                                       @RequestParam(required = false) StoreType type) {
        if (StoreType.CELL == type) {
            return this.baseFieldValueService.getCellValues(id);
        } else if (StoreType.MONGO == type) {
            return this.baseFieldValueService.getDocumentValues(id);
        } else if (StoreType.REDIS == type) {
            return this.baseFieldValueService.getKeyValues(id);
        } else {
            return this.baseFieldValueService.getValues(id);
        }
    }

    @DeleteMapping("/base-tables/{id}/field-values")
    public void deleteFieldValues(@PathVariable Long id,
                                  @RequestParam(required = false) StoreType type) {
        if (StoreType.CELL == type) {
            this.baseFieldValueService.removeCellData(id);
        } else if (StoreType.MONGO == type) {
            this.baseFieldValueService.removeDocumentData();
        } else if (StoreType.REDIS == type) {
            this.baseFieldValueService.removeKeyValueData();
        } else {
            this.baseFieldValueService.removeData(id);
        }
    }
}
