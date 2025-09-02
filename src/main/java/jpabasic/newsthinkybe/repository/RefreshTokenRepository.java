package jpabasic.newsthinkybe.repository;

import jpabasic.newsthinkybe.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
