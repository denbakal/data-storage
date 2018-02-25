package ua.challenge.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.aspect.Loggable;
import ua.challenge.repository.BaseFieldValueRepository;
import ua.challenge.repository.BaseLaneValueRepository;
import ua.challenge.service.BaseFieldValueService;

import java.util.List;

@Service
@Log4j
public class BaseFieldValueServiceImpl implements BaseFieldValueService {
    @Autowired
    private BaseFieldValueRepository baseFieldValueRepository;

    @Autowired
    private BaseLaneValueRepository baseLaneValueRepository;

    @Override
    @Loggable
    public void storeData(List<String> values) {
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
}
