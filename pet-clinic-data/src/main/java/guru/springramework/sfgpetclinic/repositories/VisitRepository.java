package guru.springramework.sfgpetclinic.repositories;

import guru.springramework.sfgpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
