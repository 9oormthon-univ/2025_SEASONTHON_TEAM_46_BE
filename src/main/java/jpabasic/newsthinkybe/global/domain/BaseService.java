package jpabasic.newsthinkybe.global.domain;

import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T, ID> {
    protected T getEntityOrThrow(JpaRepository<T, ID> repo, ID id, String entityName) {
        return repo.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, entityName + " not found with id: " + id));
    }
}

