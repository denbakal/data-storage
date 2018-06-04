package ua.challenge.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.DataAccessException;
//import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.aspect.Loggable;
import ua.challenge.dto.BaseFieldDto;
import ua.challenge.dto.BaseFieldValueDto;
import ua.challenge.dto.BaseLaneDto;
import ua.challenge.dto.BaseLaneValueDto;
import ua.challenge.entity.cassandra.TableData;
import ua.challenge.entity.elasticsearch.FieldIndex;
import ua.challenge.entity.mongo.TableValue;
import ua.challenge.mapper.BaseFieldValueMapper;
import ua.challenge.mapper.BaseLaneMapper;
import ua.challenge.mapper.BaseLaneValueMapper;
import ua.challenge.repository.BaseFieldValueRepository;
import ua.challenge.repository.BaseLaneRepository;
import ua.challenge.repository.BaseLaneValueRepository;
import ua.challenge.repository.elasticsearch.FieldIndexRepository;
import ua.challenge.repository.mongo.TableValueRepository;
import ua.challenge.service.BaseFieldService;
import ua.challenge.service.BaseFieldValueService;
import ua.challenge.type.ColumnInsertType;
import ua.challenge.util.DestinationPointGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
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

//    @Autowired
//    private CassandraTemplate cassandraTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    @Autowired
//    private TableValueRepository tableValueRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

//    @Autowired
//    private FieldIndexRepository fieldIndexRepository;

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
//        baseFieldValueRepository.saveValues(values);

        List<FieldIndex> fieldIndices = new ArrayList<>();

        long recordId = 1L;
        Date startDate = java.sql.Timestamp.valueOf(LocalDateTime.from(LocalDateTime.now()).minusDays(7));
        Date endDate = java.sql.Timestamp.valueOf(LocalDateTime.from(LocalDateTime.now()).plusDays(3));

        /*for (String value : values) {
            try {
                JSONObject lane = new JSONObject(value);
                ObjectMapper mapper = new ObjectMapper();
                FieldIndex fieldIndex = mapper.readValue(lane.getString("fields"), FieldIndex.class);
                fieldIndex.setRecordId(recordId);
                fieldIndex.setClientId(73L);
                fieldIndex.setFromPoint(DestinationPointGenerator.generate(new Random().nextInt(3)));
                fieldIndex.setToPoint(DestinationPointGenerator.generate(new Random().nextInt(3)));
                fieldIndex.setStart(startDate);
                fieldIndex.setEnd(endDate);

                fieldIndices.add(fieldIndex);

                recordId++;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        log.debug("Count index of fields: {}", fieldIndices.size());

        // bulk index
        this.fieldIndexRepository.save(fieldIndices);*/
    }

    @Override
    @Loggable
    public void storeColumnData(List<Map<String, String>> data, ColumnInsertType type) {
        final int TABLE_ID = 1;
        final int VERSION = 1;
        int rowIndex = 0;

        if (ColumnInsertType.INSERT == type) {
            for (Map<String, String> dataLane : data) {
                for (Map.Entry<String, String> entry : dataLane.entrySet()) {
//                    this.cassandraTemplate.insert(new TableData(TABLE_ID, VERSION, entry.getKey(), rowIndex, entry.getValue()));
                }
                rowIndex++;
            }
        } else if (ColumnInsertType.INGEST == type) {
            String insertSql = "insert into tabledata(table_id, version_id, field_name, row_index, value) values (?, ?, ?, ?, ?)";
            List<List<?>> tableData = new ArrayList<>();

            long startTime = System.currentTimeMillis();
            for (Map<String, String> dataLane : data) {
                for (Map.Entry<String, String> entry : dataLane.entrySet()) {
                    List<Object> newData = new ArrayList<>();
                    newData.add(TABLE_ID);
                    newData.add(VERSION);
                    newData.add(entry.getKey());
                    newData.add(rowIndex);
                    newData.add(entry.getValue());

                    tableData.add(newData);
                }
                rowIndex++;
            }
            System.out.println("elapsed time: " + (System.currentTimeMillis() - startTime));

//            this.cassandraTemplate.ingest(insertSql, tableData);
        } else if (ColumnInsertType.ASYNC == type) {
            for (Map<String, String> dataLane : data) {
                for (Map.Entry<String, String> entry : dataLane.entrySet()) {
//                    this.cassandraTemplate.insertAsynchronously(new TableData(TABLE_ID, VERSION, entry.getKey(), rowIndex, entry.getValue()));
                }
                rowIndex++;
            }
        }
    }

    @Override
    @Loggable
    public void storeKeyValueData(List<String> data) {
        final long TABLE_ID = 2L;
        int rowIndex = 0;

        String laneKey = "count:lane:" + TABLE_ID;
        this.redisTemplate.boundSetOps(laneKey).add(Integer.toString(data.size()));

        /*for (String dataLane : data) {
            String valueKey = "table:" + TABLE_ID + ":lane:" + rowIndex + ":fields";
            this.redisTemplate.boundSetOps(valueKey).add(dataLane);
            rowIndex++;
        }*/

        String valueKey = "table:" + TABLE_ID + ":fields";
//        this.redisTemplate.boundSetOps(valueKey).add(data.toArray(new String[] {}));
        this.redisTemplate.executePipelined(
                (RedisCallback<Object>) connection -> {
                    StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
//                    for(int i = 0; i < batchSize; i++) {
                        stringRedisConn.sAdd(valueKey, data.toArray(new String[] {}));
//                    }
                    return null;
                }
        );

        log.debug("Size of set data by lanes: {}", this.redisTemplate.boundSetOps(laneKey).size());
        log.debug("Size of set data by fields: {}", this.redisTemplate.boundSetOps(valueKey).size());
    }

    @Override
    public void removeColumnData() {
//        this.cassandraTemplate.truncate("datatable");
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

    @Override
    @Loggable
    public List<String> getDocumentValues(Long id) {
//        TableValue tableValue = this.tableValueRepository.findByTableId(id);
//        return tableValue == null ? Collections.emptyList() : tableValue.getLanes();
        return Collections.emptyList();
    }

    @Override
    @Loggable
    @Transactional
    public void storeDocumentData(List<String> data) {
        /*TableValue tableValue = new TableValue();
        tableValue.setTableId(1L);
        tableValue.setLanes(data);

        this.tableValueRepository.save(tableValue);*/
    }

    @Override
    @Loggable
    @Transactional
    public void removeDocumentData() {
//        this.tableValueRepository.deleteAll();
    }

    @Override
    @Loggable
    @Transactional
    public void removeKeyValueData() {
        final long TABLE_ID = 2L;
        String valueKey = "table:" + TABLE_ID + ":fields";

        this.redisTemplate.delete(valueKey);
    }

    @Override
    @Loggable
    public List<String> getKeyValues(Long id) {
        final long TABLE_ID = 2L;
        String valueKey = "table:" + TABLE_ID + ":fields";

        return new ArrayList<>(this.redisTemplate.boundSetOps(valueKey).members());
    }
}
