package guru.springramework.sfgpetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType petType;
    @ManyToOne
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "pet")
    @Builder.Default private Set<Visit> visits = new HashSet<>();

    public Pet addVisit(Visit visit) {
        this.visits.add(visit);
        visit.setPet(this);

        return this;
    }
}
