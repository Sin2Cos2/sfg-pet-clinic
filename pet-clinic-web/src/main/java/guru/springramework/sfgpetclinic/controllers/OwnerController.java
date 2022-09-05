package guru.springramework.sfgpetclinic.controllers;

import guru.springramework.sfgpetclinic.model.Owner;
import guru.springramework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final String VIEWS_OWNER_CREATE_OR_UPDATE = "/owners/createOrUpdateOwner";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    private void setAllowedFields(WebDataBinder wdb) {
        wdb.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.OwnerBuilder().build());
        return "/owners/findOwners";
    }

    @GetMapping
    public String processFindForm(@ModelAttribute Owner owner, BindingResult result, Model model) {
        if (owner.getLastName() == null)
            owner.setLastName("");

        List<Owner> owners = ownerService.findAllByLastNameIgnoreCaseLike("%" + owner.getLastName() + "%");

        if (owners.isEmpty()) {
            result.rejectValue("lastName", "notFound");
            return "/owners/findOwners";
        } else if (owners.size() == 1) {
            model.addAttribute("owner", owners.get(0));
            return "/owners/ownerDetails";
        }

        model.addAttribute("owners", owners);

        return "/owners/ownersList";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView mav = new ModelAndView("/owners/ownerDetails");
        mav.addObject("owner", ownerService.findById(ownerId));

        return mav;
    }

    @GetMapping("/new")
    public String initCreateOwnerForm(Model model) {
        model.addAttribute("owner", Owner.OwnerBuilder().build());

        return VIEWS_OWNER_CREATE_OR_UPDATE;
    }

    @PostMapping("/new")
    public String processCreationForm(@ModelAttribute Owner owner, BindingResult result) {
        if (result.hasErrors())
            return VIEWS_OWNER_CREATE_OR_UPDATE;

        ownerService.save(owner);

        return "redirect:/owners/" + owner.getId();
    }

    @GetMapping("/{id}/edit")
    public String initUpdateOwnerForm(@PathVariable Long id, Model model) {
        Owner owner = ownerService.findById(id);
        model.addAttribute("owner", owner);

        return VIEWS_OWNER_CREATE_OR_UPDATE;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateOwnerForm(@ModelAttribute Owner owner, @PathVariable Long id, BindingResult result) {
        if (result.hasErrors())
            return VIEWS_OWNER_CREATE_OR_UPDATE;

        Owner tmpOwner = ownerService.findById(id);

        owner.setId(tmpOwner.getId());
        owner.setPets(tmpOwner.getPets());
        ownerService.save(owner);

        return "redirect:/owners/" + id;
    }
}
