package guru.springramework.sfgpetclinic.services;

import guru.springramework.sfgpetclinic.model.Owner;


public interface OwnerService extends CrudService<Owner, Long>{

    Owner findByLastName(String lastName);
}