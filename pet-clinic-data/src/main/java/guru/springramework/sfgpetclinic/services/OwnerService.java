package guru.springramework.sfgpetclinic.services;

import guru.springramework.sfgpetclinic.model.Owner;

import java.util.List;


public interface OwnerService extends CrudService<Owner, Long>{

    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameIgnoreCaseLike(String lastName);
}
