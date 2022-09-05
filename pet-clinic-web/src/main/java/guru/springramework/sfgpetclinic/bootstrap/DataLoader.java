package guru.springramework.sfgpetclinic.bootstrap;

import guru.springramework.sfgpetclinic.model.*;
import guru.springramework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) {

        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dogType = new PetType();
        dogType.setName("dog");
        petTypeService.save(dogType);

        PetType catType = new PetType();
        catType.setName("cat");
        petTypeService.save(catType);

        System.out.println("Loaded Pet types....");

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");

        specialtyService.save(radiology);
        specialtyService.save(surgery);
        specialtyService.save(dentistry);

        Owner owner1 = Owner
                .OwnerBuilder()
                .firstName("Michael")
                .lastName("Weston")
                .address("123 Brickerel")
                .city("Miami")
                .telephone("1231231234")
                .build();

        Owner owner3 = Owner
                .OwnerBuilder()
                .firstName("Joe")
                .lastName("Weston")
                .address("1 Brickerel")
                .city("Srow")
                .telephone("1231231234")
                .build();

        Owner owner2 = Owner
                .OwnerBuilder()
                .firstName("Fiona")
                .lastName("Glemanne")
                .address("9 Baltului")
                .city("Bucharest")
                .telephone("187911")
                .build();

        System.out.println("Loaded Owners....");

        Pet dog = Pet
                .builder()
                .petType(dogType)
                .name("Luke")
                .owner(owner1)
                .birthDate(LocalDate.now())
                .build();
        owner1.getPets().add(dog);

        Pet cat = Pet
                .builder()
                .petType(catType)
                .name("Luci")
                .owner(owner2)
                .birthDate(LocalDate.now())
                .build();
        owner2.getPets().add(cat);

        ownerService.save(owner1);
        ownerService.save(owner2);
        ownerService.save(owner3);

        Visit catVisit = Visit
                .builder()
                .pet(cat)
                .date(LocalDate.now())
                .description("Sneezy Kitty")
                .build();

        visitService.save(catVisit);

        Vet vet1 = Vet
                .builder()
                .firstName("Sam")
                .lastName("Axe")
                .build();
        vet1.getSpecialties().add(radiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialties().add(surgery);

        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }
}
