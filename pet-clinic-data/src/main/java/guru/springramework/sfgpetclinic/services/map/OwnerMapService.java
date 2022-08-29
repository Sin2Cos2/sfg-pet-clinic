package guru.springramework.sfgpetclinic.services.map;

import guru.springramework.sfgpetclinic.model.Owner;
import guru.springramework.sfgpetclinic.services.OwnerService;
import guru.springramework.sfgpetclinic.services.PetService;
import guru.springramework.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner obj) {
        super.delete(obj);
    }

    @Override
    public Owner save(Owner obj) {

        if (obj == null)
            return null;

        if(obj.getPets() != null){
            obj.getPets().forEach(pet -> {
                if (pet.getPetType() != null) {
                    if (pet.getPetType().getId() == null) {
                        petTypeService.save(pet.getPetType());
                    }
                    petService.save(pet);
                } else {
                    throw new RuntimeException("Pet Type is required");
                }
            });
        }

        return super.save(obj);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        for (Map.Entry<Long, Owner> entry : this.map.entrySet()) {
            if (entry.getValue().getLastName().equalsIgnoreCase(lastName))
                return entry.getValue();
        }

        return null;
    }
}
