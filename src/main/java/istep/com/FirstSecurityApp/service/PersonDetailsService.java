package istep.com.FirstSecurityApp.service;

import istep.com.FirstSecurityApp.Repo.PeopleRepo;
import istep.com.FirstSecurityApp.models.Person;
import istep.com.FirstSecurityApp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepo peopleRepo;
    @Autowired
    public PersonDetailsService(PeopleRepo peopleRepo) {
        this.peopleRepo = peopleRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Person> person= peopleRepo.findByUsername(username);
       if(person.isEmpty())
           throw new UsernameNotFoundException("User not found!");

       return new PersonDetails(person.get());
    }
}
