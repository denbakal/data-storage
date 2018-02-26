package ua.challenge.service.impl;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.aspect.Loggable;
import ua.challenge.dto.BaseFieldDto;
import ua.challenge.dto.BaseFieldValueDto;
import ua.challenge.dto.BaseLaneDto;
import ua.challenge.dto.BaseLaneValueDto;
import ua.challenge.entity.BaseField;
import ua.challenge.mapper.BaseFieldValueMapper;
import ua.challenge.mapper.BaseLaneMapper;
import ua.challenge.mapper.BaseLaneValueMapper;
import ua.challenge.repository.BaseFieldValueRepository;
import ua.challenge.repository.BaseLaneRepository;
import ua.challenge.repository.BaseLaneValueRepository;
import ua.challenge.service.BaseFieldService;
import ua.challenge.service.BaseFieldValueService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BaseFieldValueServiceImpl implements BaseFieldValueService {
    @Autowired
    private BaseFieldValueRepository baseFieldValueRepository;

    @Autowired
    private BaseLaneValueRepository baseLaneValueRepository;

    @Autowired
    private BaseLaneRepository baseLaneRepository;

    @Autowired
    private BaseFieldService baseFieldService;

    @Autowired
    private BaseFieldValueMapper baseFieldValueMapper;

    @Autowired
    private BaseLaneMapper baseLaneMapper;

    @Autowired
    private BaseLaneValueMapper baseLaneValueMapper;

    @Override
    @Loggable
    public void storeData(List<String> values) {
        long id = 0L;

        for (String value : values) {
            BaseLaneValueDto baseLaneValueDto = new BaseLaneValueDto();
            baseLaneValueDto.setId(id);
            baseLaneValueDto.setValue(value);

            this.baseLaneValueRepository.save(this.baseLaneValueMapper.toBaseLaneValue(baseLaneValueDto));

            id++;
        }
    }

    @Override
    @Loggable
    public void storeJsonData(List<String> values) {
        final long BASE_TABLE_ID = 1L;
        baseFieldValueRepository.saveValues(values);
    }

    @Override
    @Loggable
    public List<String> getValues(Long id) {
        return this.baseFieldValueRepository.findFieldValuesByBaseTableId(id);
    }

    @Override
    @Loggable
    @Transactional
    public void removeData(Long id) {
        this.baseLaneValueRepository.delete(id);
    }

    @Override
    @Loggable
    @Transactional
    public void storeCellData(List<String> values) {
        final long BASE_TABLE_ID = 1L;
        List<BaseFieldDto> fields = this.baseFieldService.getFieldsByBaseTableId(BASE_TABLE_ID);
        Map<Integer, Long> fieldsByOrdinal = fields.stream()
                .collect(Collectors.toMap(BaseFieldDto::getOrdinal, BaseFieldDto::getId));

        int ordinalLane = 0;
        for (String value : values) {
            BaseLaneDto baseLaneDto = new BaseLaneDto();
            baseLaneDto.setBaseTableId(BASE_TABLE_ID);
            baseLaneDto.setOrdinal(ordinalLane);

            BaseLaneDto persistedLane = this.baseLaneMapper.fromBaseLane(this.baseLaneRepository.save(this.baseLaneMapper.toBaseLane(baseLaneDto)));
            try {
                JSONObject jsonObj = new JSONObject(value);

                for (Integer ordinal : fieldsByOrdinal.keySet()) {
                    String fieldValue = jsonObj.getString(ordinal.toString());
                    BaseFieldValueDto baseFieldValueDto = new BaseFieldValueDto();
                    baseFieldValueDto.setBaseTableId(BASE_TABLE_ID);
                    baseFieldValueDto.setFieldId(fieldsByOrdinal.get(ordinal));
                    baseFieldValueDto.setLaneId(persistedLane.getId());
                    baseFieldValueDto.setValue(fieldValue);

                    this.baseFieldValueRepository.save(this.baseFieldValueMapper.toBaseFieldValue(baseFieldValueDto));
                }
            } catch (JSONException error) {
                log.error(error);
            }
            ordinalLane++;
        }
    }

    @Override
    @Transactional
    public void removeCellData(Long id) {
        this.baseLaneRepository.delete(id);
        this.baseFieldValueRepository.delete(id);
    }

    @Override
    @Loggable
    @Transactional
    public List<String> getCellValues(Long id) {
        List<String> result = new ArrayList<>();
        List<BaseLaneDto> baseLanes = this.baseLaneMapper.fromBaseLaneList(this.baseLaneRepository.findByBaseTableId(id));

        Map<Integer, Long> baseLanesByOrdinal = baseLanes.stream()
                .collect(Collectors.toMap(BaseLaneDto::getOrdinal, BaseLaneDto::getId));

        for (Integer ordinalLane : baseLanesByOrdinal.keySet()) {
            Long currentLaneId = baseLanesByOrdinal.get(ordinalLane);
            List<BaseFieldValueDto> fieldValues = this.baseFieldValueMapper.fromBaseFieldValueList(this.baseFieldValueRepository.findByBaseLaneId(currentLaneId));

            int ordinalField = 0;
            JSONObject jsonObject = new JSONObject();

            for (BaseFieldValueDto fieldValue : fieldValues) {
                try {
                    jsonObject.put(Integer.toString(ordinalField), fieldValue.getValue());
                    ordinalField++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            result.add(jsonObject.toString());
        }

        return result;
    }
}
