package guru.springramework.sfgpetclinic.repositories;

import guru.springramework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Optional<Owner> findByLastName(String lastName);
    List<Owner> findAllByLastNameIgnoreCaseLike(String lastName);
}
