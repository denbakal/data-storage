package ua.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.challenge.dto.BaseTableDto;
import ua.challenge.mapper.BaseTableMapper;
import ua.challenge.repository.BaseTableRepository;
import ua.challenge.service.BaseTableService;

@Service
public class BaseTableServiceImpl implements BaseTableService {
    @Autowired
    private BaseTableRepository baseTableRepository;

    @Autowired
    private BaseTableMapper baseTableMapper;

    @Override
    public void save(BaseTableDto baseTableDto) {
        this.baseTableRepository.save(this.baseTableMapper.toBaseTable(baseTableDto));
    }
}
