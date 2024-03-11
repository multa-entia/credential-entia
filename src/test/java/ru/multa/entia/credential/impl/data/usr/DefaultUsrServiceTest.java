package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrEntity;
import ru.multa.entia.credential.api.data.usr.UsrRepo;
import ru.multa.entia.credential.impl.converter.usr.DefaultEntity2UsrConverter;
import ru.multa.entia.credential.impl.converter.usr.DefaultUsr2EntityConverter;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.utils.Results;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;


class DefaultUsrServiceTest {

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    private static final Function<Usr, UsrEntity> TO_ENTITY_CONVERTER = new DefaultUsr2EntityConverter();
    private static final Function<UsrEntity, Usr> TO_USR_CONVERTER = new DefaultEntity2UsrConverter();

    @Test
    void shouldCheckByIdGetting_ifEntityAbsence() {
        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.empty());

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<Usr> result = service.getById(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultUsrService.Code.ENTITY_BY_ID_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckByIdGetting() {
        DefaultUsrEntity entity = new DefaultUsrEntity(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.of(entity));

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<Usr> result = service.getById(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Usr gotterUsr = result.value();
        assertThat(gotterUsr.id()).isEqualTo(entity.getId());
        assertThat(gotterUsr.firstName()).isEqualTo(entity.getFirstName());
        assertThat(gotterUsr.paterName()).isEqualTo(entity.getPaterName());
        assertThat(gotterUsr.surname()).isEqualTo(entity.getSurname());
        assertThat(gotterUsr.email()).isEqualTo(entity.getEmail());
    }

    @Test
    void shouldCheckSaving() {
        ObjectId expectedId = new ObjectId();
        DefaultUsr expectedUsr = new DefaultUsr(
                null,
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        DefaultUsrEntity entity = new DefaultUsrEntity(
                null,
                expectedUsr.firstName(),
                expectedUsr.paterName(),
                expectedUsr.surname(),
                expectedUsr.email()
        );

        Function<UsrEntity, UsrRepo> repoFunction = e -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            e.setId(expectedId);
            Mockito.when(repo.save(Mockito.any())).thenReturn(e);

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoFunction.apply(entity), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<Usr> result = service.save(expectedUsr);

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Usr gotterUsr = result.value();
        assertThat(gotterUsr.id()).isEqualTo(expectedId);
        assertThat(gotterUsr.firstName()).isEqualTo(expectedUsr.firstName());
        assertThat(gotterUsr.paterName()).isEqualTo(expectedUsr.paterName());
        assertThat(gotterUsr.surname()).isEqualTo(expectedUsr.surname());
        assertThat(gotterUsr.email()).isEqualTo(expectedUsr.email());
    }

    @Test
    void shouldCheckSaving_idFail() {
        DefaultUsr expectedUsr = new DefaultUsr(
                null,
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.save(Mockito.any())).thenThrow(RuntimeException.class);

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<Usr> result = service.save(expectedUsr);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultUsrService.Code.FAIL_SAVING))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckByFirstNameGetting_ifAbsence() {
        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findByFirstName(Mockito.any())).thenReturn(List.of());

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getByFirstName(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultUsrService.Code.ENTITIES_BY_FIRST_NAME_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckByFirstNameGetting() {
        DefaultUsr expectedUsr = new DefaultUsr(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        DefaultUsrEntity expectedEntity = new DefaultUsrEntity(
                expectedUsr.id(),
                expectedUsr.firstName(),
                expectedUsr.paterName(),
                expectedUsr.surname(),
                expectedUsr.email()
        );

        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findByFirstName(Mockito.any())).thenReturn(List.of(expectedEntity));

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getByFirstName(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        List<Usr> gottenUsers = result.value();
        assertThat(gottenUsers).hasSize(1);
        assertThat(gottenUsers.get(0)).isEqualTo(expectedUsr);
    }

    @Test
    void shouldCheckByPaterNameGetting_idAbsence() {
        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findByPaterName(Mockito.any())).thenReturn(List.of());

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getByPaterName(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultUsrService.Code.ENTITIES_BY_PATER_NAME_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }


    @Test
    void shouldCheckByPaterNameGetting() {
        DefaultUsr expectedUsr = new DefaultUsr(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        DefaultUsrEntity expectedEntity = new DefaultUsrEntity(
                expectedUsr.id(),
                expectedUsr.firstName(),
                expectedUsr.paterName(),
                expectedUsr.surname(),
                expectedUsr.email()
        );

        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findByPaterName(Mockito.any())).thenReturn(List.of(expectedEntity));

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getByPaterName(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        List<Usr> gottenUsers = result.value();
        assertThat(gottenUsers).hasSize(1);
        assertThat(gottenUsers.get(0)).isEqualTo(expectedUsr);
    }

    @Test
    void shouldCheckBySurnameGetting_idAbsence() {
        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findBySurname(Mockito.any())).thenReturn(List.of());

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getBySurname(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultUsrService.Code.ENTITIES_BY_SURNAME_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }


    @Test
    void shouldCheckBySurnameGetting() {
        DefaultUsr expectedUsr = new DefaultUsr(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        DefaultUsrEntity expectedEntity = new DefaultUsrEntity(
                expectedUsr.id(),
                expectedUsr.firstName(),
                expectedUsr.paterName(),
                expectedUsr.surname(),
                expectedUsr.email()
        );

        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findBySurname(Mockito.any())).thenReturn(List.of(expectedEntity));

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getBySurname(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        List<Usr> gottenUsers = result.value();
        assertThat(gottenUsers).hasSize(1);
        assertThat(gottenUsers.get(0)).isEqualTo(expectedUsr);
    }

    @Test
    void shouldCheckByEmailGetting_ifAbsence() {
        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findByEmail(Mockito.any())).thenReturn(List.of());

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getByEmail(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultUsrService.Code.ENTITIES_BY_EMAIL_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckByEmailGetting() {
        DefaultUsr expectedUsr = new DefaultUsr(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        DefaultUsrEntity expectedEntity = new DefaultUsrEntity(
                expectedUsr.id(),
                expectedUsr.firstName(),
                expectedUsr.paterName(),
                expectedUsr.surname(),
                expectedUsr.email()
        );

        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito.when(repo.findByEmail(Mockito.any())).thenReturn(List.of(expectedEntity));

            return repo;
        };

        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        Result<List<Usr>> result = service.getByEmail(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        List<Usr> gottenUsers = result.value();
        assertThat(gottenUsers).hasSize(1);
        assertThat(gottenUsers.get(0)).isEqualTo(expectedUsr);
    }

    @Test
    void shouldCheckByIdDeleting() {
        AtomicReference<ObjectId> holder = new AtomicReference<>();
        Supplier<UsrRepo> repoSupplier = () -> {
            UsrRepo repo = Mockito.mock(UsrRepo.class);
            Mockito
                    .doAnswer(new Answer<Void>() {
                        @Override
                        public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                            holder.set(invocationOnMock.getArgument(0));
                            return null;
                        }
                    })
                    .when(repo)
                    .deleteById(Mockito.any());

            return repo;
        };

        ObjectId expectedId = new ObjectId();
        DefaultUsrService service = new DefaultUsrService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_USR_CONVERTER);
        service.deleteById(expectedId);

        assertThat(holder.get()).isEqualTo(expectedId);
    }
}