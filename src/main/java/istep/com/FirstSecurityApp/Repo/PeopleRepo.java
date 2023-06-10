package istep.com.FirstSecurityApp.Repo;

import istep.com.FirstSecurityApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepo extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);

}
