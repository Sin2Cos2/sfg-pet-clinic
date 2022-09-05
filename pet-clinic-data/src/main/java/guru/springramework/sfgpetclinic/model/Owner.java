package guru.springramework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

    private String address;
    private String city;
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true)
    private Set<Pet> pets = new HashSet<>();

    @Builder(builderMethodName = "OwnerBuilder")
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public Owner addPet(Pet pet) {
        Optional<Pet> tmpPet = this.getPets().stream()
                .filter(tmp -> tmp.getPetType().getName().equalsIgnoreCase(pet.getPetType().getName()))
                .filter(tmp -> tmp.getName().equalsIgnoreCase(pet.getName()))
                .filter(tmp -> tmp.getBirthDate().isEqual(pet.getBirthDate()))
                .findFirst();

        if (!tmpPet.isPresent()) {
            this.pets.add(pet);
            pet.setOwner(this);
        }

        return this;
    }
}
