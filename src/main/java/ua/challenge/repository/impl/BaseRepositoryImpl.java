package ua.challenge.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
public abstract class BaseRepositoryImpl<E, ID extends Serializable> implements BaseRepository<E, ID> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected JPAQueryFactory queryFactory;

    private Class<E> entityClass;
    private Class<ID> id;

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = genericSuperclass.getActualTypeArguments();

        this.entityClass = (Class<E>) types[0];
        this.id = (Class<ID>) types[1];
    }

    @Override
    public E findOne(ID id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<E> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        Root<E> from = query.from(entityClass);

        return entityManager.createQuery(query.select(from)).getResultList();
    }

    @Override
    @Transactional
    public void remove(ID id) {
        entityManager.remove(findOne(id));
    }

    @Override
    @Transactional
    public void remove(E entity) {
        entityManager.remove(entity);
    }

    @Override
    @Transactional
    public E save(E entity) {
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);

        if (id == null) {
            entityManager.persist(entity);
            return entity;
        }

        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public List<E> saveAll(List<E> entities) {
        entities.forEach(this::save);

        return entities;
    }

    @Override
    public void clear() {
        entityManager.clear();
    }
}
