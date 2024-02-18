package ru.multa.entia.credential.impl.data.usr;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.usr.UsrRepo;

import java.util.function.Supplier;

// TODO: impl
class UsrServiceImplTest {
    @Test
    void shouldCheckByIdGetting() {
        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            return repo;
        };
    }

    @Test
    void shouldCheckSaving() {

    }

    @Test
    void shouldCheckByFirstNameGetting() {

    }

    @Test
    void shouldCheckByPaterNameGetting() {

    }

    @Test
    void shouldCheckBySurnameGetting() {

    }

    @Test
    void shouldCheckByEmailGetting() {

    }

    @Test
    void shouldCheckByIdDeleting() {

    }
}