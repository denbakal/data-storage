package ua.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.aspect.Loggable;
import ua.challenge.dto.BaseFieldDto;
import ua.challenge.mapper.BaseFieldMapper;
import ua.challenge.repository.BaseFieldRepository;
import ua.challenge.service.BaseFieldService;

import java.util.List;

@Service
public class BaseFieldServiceImpl implements BaseFieldService {
    @Autowired
    private BaseFieldRepository baseFieldRepository;

    @Autowired
    private BaseFieldMapper baseFieldMapper;

    @Override
    public List<BaseFieldDto> getFieldsByBaseTableId(Long baseTableId) {
        return baseFieldMapper.fromBaseFieldList(baseFieldRepository.findAllByBaseTableId(baseTableId));
    }

    @Override
    @Loggable
    @Transactional
    public void generate(int countFields) {
        final long BASE_TABLE_ID = 1L;

        for (int i = 0; i < countFields; i++) {
            BaseFieldDto baseFieldDto = new BaseFieldDto();
            baseFieldDto.setBaseTableId(BASE_TABLE_ID);
            baseFieldDto.setName("F" + i);
            baseFieldDto.setOrdinal(i);

            this.baseFieldRepository.save(this.baseFieldMapper.toBaseField(baseFieldDto));
        }
    }

    @Override
    @Loggable
    @Transactional
    public void delete() {
        this.baseFieldRepository.delete();
    }


}
