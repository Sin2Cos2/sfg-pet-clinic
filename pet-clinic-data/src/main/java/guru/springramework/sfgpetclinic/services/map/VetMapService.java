package guru.springramework.sfgpetclinic.services.map;

import guru.springramework.sfgpetclinic.model.Vet;
import guru.springramework.sfgpetclinic.services.SpecialtyService;
import guru.springramework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialtyService specialtyService;

    public VetMapService(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet obj) {
        super.delete(obj);
    }

    @Override
    public Vet save(Vet obj) {
        if(obj.getSpecialties().size() > 0) {
            obj.getSpecialties().forEach(specialty -> {
                if(specialty.getId() == null)
                    specialtyService.save(specialty);
            });
        }
        return super.save(obj);
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }
}
