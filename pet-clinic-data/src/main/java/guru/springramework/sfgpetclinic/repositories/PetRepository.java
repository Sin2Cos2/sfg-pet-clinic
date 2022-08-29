package guru.springramework.sfgpetclinic.repositories;

import guru.springramework.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
