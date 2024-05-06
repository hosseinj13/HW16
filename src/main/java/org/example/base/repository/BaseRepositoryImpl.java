package org.example.base.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.example.base.entity.BaseEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class BaseRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
        implements BaseRepository<T, ID> {

    protected Session session;


    @Override
    public T saveOrUpdate(T entity) {
        beginTransaction();
        entity = saveWithoutTransaction(entity);
        commitTransaction();
        return entity;
    }

    private T saveWithoutTransaction(T entity) {
        if (entity.getId() == null)
            session.persist(entity);
        else
            entity = session.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(session.get(getEntityClass(), id));
    }


    @Override
    public void delete(T entity) {
        beginTransaction();
        session.remove(entity);
        commitTransaction();
    }

    @Override
    public void beginTransaction() {
        if (!session.getTransaction().isActive())
            session.beginTransaction();
    }

    @Override
    public void commitTransaction() {
        if (session.getTransaction().isActive())
            session.getTransaction().commit();
    }

    @Override
    public void rollback() {
        if (session.getTransaction().isActive())
            session.getTransaction().rollback();
    }

    public abstract Class<T> getEntityClass();
}
