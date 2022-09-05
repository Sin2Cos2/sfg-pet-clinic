package guru.springramework.sfgpetclinic.repositories;

import guru.springramework.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
