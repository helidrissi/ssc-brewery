package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.LoginSucces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSuccesRepository extends JpaRepository<LoginSucces,Long> {
}
