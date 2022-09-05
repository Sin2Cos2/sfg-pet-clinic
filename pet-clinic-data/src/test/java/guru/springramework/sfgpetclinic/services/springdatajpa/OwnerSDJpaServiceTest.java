package guru.springramework.sfgpetclinic.services.springdatajpa;

import guru.springramework.sfgpetclinic.model.Owner;
import guru.springramework.sfgpetclinic.model.Pet;
import guru.springramework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @InjectMocks
    OwnerSDJpaService ownerService;
    @Mock
    OwnerRepository ownerRepository;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.OwnerBuilder().id(1L).lastName("Smith").build();
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(owner);
        ownerSet.add(Owner.OwnerBuilder().id(2L).firstName("Gosha").build());

        when(ownerRepository.findAll()).thenReturn(ownerSet);

        Set<Owner> owners = ownerService.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(owner));

        assertNotNull(ownerService.findById(1L));
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(ownerService.findById(1L));
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.OwnerBuilder().id(2L).build();

        when(ownerRepository.save(any())).thenReturn(owner);

        assertNotNull(ownerService.save(ownerToSave));
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerService.delete(owner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(1L);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(Optional.ofNullable(owner));

        assertEquals("Smith", ownerService.findByLastName("Smith").getLastName());
    }
}