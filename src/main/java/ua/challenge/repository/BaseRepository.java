package ua.challenge.repository;

import java.io.Serializable;
import java.util.List;

public interface BaseRepository<E, ID extends Serializable> {
    E findOne(ID id);

    List<E> findAll();

    void remove(ID id);

    void remove(E entity);

    E save(E entity);

    List<E> saveAll(List<E> entities);

    void clear();
}
