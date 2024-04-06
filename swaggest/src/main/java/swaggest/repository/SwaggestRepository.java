package swaggest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swaggest.entity.Swaggest;

import java.util.List;

@Repository
public interface SwaggestRepository extends JpaRepository<Swaggest, Long> {
    List<Swaggest> findByUser(String user);
}
