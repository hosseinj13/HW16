package org.example.base.service;

import lombok.RequiredArgsConstructor;
import org.example.base.entity.BaseEntity;
import org.example.base.repository.BaseRepository;
import org.example.exception.NotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

@RequiredArgsConstructor
public class BaseServiceImpl<T extends BaseEntity<ID>,
        ID extends Serializable,
        R extends BaseRepository<T, ID>>
        implements BaseService<T, ID>{

    protected final R repository;

    @Override
    public T saveOrUpdate(T entity) {
        return repository.saveOrUpdate(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(String.format("entity with %s not found", id)));
    }

    @Override
    public void deleteById(ID id) {
        T foundedEntity = findById(id);
        repository.delete(foundedEntity);
    }

    @Override
    public void delete(T t) throws IllegalStateException {
            repository.delete(t);
    }
}
