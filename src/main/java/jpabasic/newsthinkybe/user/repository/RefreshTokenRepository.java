package jpabasic.newsthinkybe.user.repository;

import jpabasic.newsthinkybe.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
