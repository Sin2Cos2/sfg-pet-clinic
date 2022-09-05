package guru.springramework.sfgpetclinic.services.map;

import guru.springramework.sfgpetclinic.model.Owner;
import guru.springramework.sfgpetclinic.model.Pet;
import guru.springramework.sfgpetclinic.model.PetType;
import guru.springramework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerService ownerService;
    Long ownerId = 1L;

    @BeforeEach
    void setUp() {
        ownerService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerService.save(Owner
                .OwnerBuilder()
                .id(ownerId)
                .firstName("John")
                .lastName("Krick")
                .city("Berg")
                .address("12 Joras")
                .telephone("7891274")
                .build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerService.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(3L);
        assertEquals(1, ownerService.findAll().size());

        ownerService.deleteById(1L);
        assertEquals(0, ownerService.findAll().size());
    }

    @Test
    void delete() {
        ownerService.delete(null);
        assertEquals(1, ownerService.findAll().size());

        ownerService.delete(ownerService.findById(ownerId));
        assertEquals(0, ownerService.findAll().size());
    }

    @Test
    void save() {
        Owner owner = Owner
                .OwnerBuilder()
                .firstName("Alex")
                .lastName("Asala")
                .build();
        owner.getPets().add(Pet.builder().name("loky").petType(new PetType("cat")).build());
        ownerService.save(owner);

        assertEquals(2, ownerService.findAll().size());
        assertNotNull(owner.getId());
        assertEquals(1, ownerService.findByLastName("Asala").getPets().size());
    }

    @Test
    void findById() {
        assertEquals(ownerId, ownerService.findById(ownerId).getId());
    }

    @Test
    void findByLastName() {
        assertEquals(1L, ownerService.findByLastName("Krick").getId());
        assertNull(ownerService.findByLastName("Fool"));
    }
}