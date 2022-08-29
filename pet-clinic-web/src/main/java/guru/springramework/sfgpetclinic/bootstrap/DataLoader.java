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

        Owner owner1 = new Owner();
        owner1.setFirstName("Micheal");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Brickerel");
        owner1.setCity("Miami");
        owner1.setTelephone("1231231234");

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("9 Baltului");
        owner2.setCity("Bucharest");
        owner2.setTelephone("187911");


        System.out.println("Loaded Owners....");

        Pet dog = new Pet();
        dog.setPetType(dogType);
        dog.setName("Luke");
        dog.setOwner(owner1);
        dog.setBirthDate(LocalDate.now());
        owner1.getPets().add(dog);

        Pet cat = new Pet();
        cat.setPetType(catType);
        cat.setName("Luci");
        cat.setOwner(owner2);
        cat.setBirthDate(LocalDate.now());
        owner2.getPets().add(cat);

        ownerService.save(owner1);
        ownerService.save(owner2);

        Visit catVisit = new Visit();
        catVisit.setPet(cat);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        visitService.save(catVisit);

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(radiology);

        vetService.save(vet1);


        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(surgery);

        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }
}
