package guru.springramework.sfgpetclinic.controllers;

import guru.springramework.sfgpetclinic.model.Pet;
import guru.springramework.sfgpetclinic.model.Visit;
import guru.springramework.sfgpetclinic.services.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/owners/{ownerId}/pets/{petId}")
@Controller
public class VisitController {

    private final String VIEWS_VISIT_CREATE_OR_UPDATE_FORM = "/pets/createOrUpdateVisit";

    private final PetService petService;

    public VisitController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/visits/new")
    public String initCreateVisitForm(Model model, @PathVariable Long petId) {
        Pet pet = petService.findById(petId);
        model.addAttribute("visit", new Visit());
        model.addAttribute("pet", pet);

        return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/visits/new")
    public String processCreateVisitForm(@PathVariable Long petId, @PathVariable Long ownerId,
                                         @ModelAttribute Visit visit, BindingResult result) {
        if(result.hasErrors())
            return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;

        Pet pet = petService.findById(petId);
        pet.addVisit(visit);

        petService.save(pet);
        return "redirect:/owners/" + ownerId;
    }

}
