package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.entities.TermVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermVersionJpaRepo extends JpaRepository<TermVersionEntity,Integer> {

    TermVersionEntity findByVersion(String version);
}