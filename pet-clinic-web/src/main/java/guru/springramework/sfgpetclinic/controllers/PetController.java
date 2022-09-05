package guru.springramework.sfgpetclinic.controllers;

import guru.springramework.sfgpetclinic.model.Owner;
import guru.springramework.sfgpetclinic.model.Pet;
import guru.springramework.sfgpetclinic.model.PetType;
import guru.springramework.sfgpetclinic.model.Visit;
import guru.springramework.sfgpetclinic.services.OwnerService;
import guru.springramework.sfgpetclinic.services.PetService;
import guru.springramework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {

    private final String VIEWS_PET_CREATE_OR_UPDATE_FORM = "/pets/createOrUpdatePet";

    private final PetTypeService petTypeService;
    private final OwnerService ownerService;
    private final PetService petService;

    public PetController(PetTypeService petTypeService, OwnerService ownerService, PetService petService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @ModelAttribute("types")
    public Collection<PetType> getTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner getOwnerById(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void setAllowedFields(WebDataBinder wdb) {
        wdb.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreatePetForm(Model model, @PathVariable Long ownerId) {
        Pet pet = Pet.builder().owner(ownerService.findById(ownerId)).build();
        model.addAttribute("pet", pet);

        return VIEWS_PET_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreatePetForm(@PathVariable Long ownerId, @ModelAttribute Pet pet, BindingResult result) {
        if(result.hasErrors())
            return VIEWS_PET_CREATE_OR_UPDATE_FORM;

        Owner owner = ownerService.findById(ownerId);
        owner.addPet(pet);
        ownerService.save(owner);

        return "redirect:/owners/" + ownerId;
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdatePetForm(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);

        model.addAttribute("pet", pet);
        return VIEWS_PET_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdatePetForm(@PathVariable Long ownerId, @PathVariable Long petId,
                                       @ModelAttribute Pet pet, BindingResult result){
        if(result.hasErrors())
            return VIEWS_PET_CREATE_OR_UPDATE_FORM;

        Pet oldPet = petService.findById(petId);
        pet.setOwner(oldPet.getOwner());
        pet.setVisits(oldPet.getVisits());

        petService.save(pet);

        return "redirect:/owners/" + ownerId;
    }
}
