package ua.challenge.repository.impl;

import org.springframework.stereotype.Repository;
import ua.challenge.entity.BaseTable;
import ua.challenge.repository.BaseTableRepository;

@Repository
public  class BaseTableRepositoryImpl extends BaseRepositoryImpl<BaseTable, Long> implements BaseTableRepository {
}
