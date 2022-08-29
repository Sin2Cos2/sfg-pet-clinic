package guru.springramework.sfgpetclinic.services.map;

import guru.springramework.sfgpetclinic.model.Visit;
import guru.springramework.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public Visit findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public Visit save(Visit obj) {

        if (obj.getPet() == null || obj.getPet().getOwner() == null ||
                obj.getPet().getId() == null || obj.getPet().getOwner().getId() == null)
            return null;

        return super.save(obj);
    }

    @Override
    public void delete(Visit obj) {
        super.delete(obj);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }
}
