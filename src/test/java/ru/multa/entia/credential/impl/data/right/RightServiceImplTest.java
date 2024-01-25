package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.api.data.right.RightRepo;
import ru.multa.entia.credential.impl.converter.right.DefaultEntity2RightConverter;
import ru.multa.entia.credential.impl.converter.right.DefaultRight2EntityConverter;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.utils.Results;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;

class RightServiceImplTest {
    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    private static  final Function<Right, RightEntity> TO_ENTITY_CONVERTER = new DefaultRight2EntityConverter();
    private static final Function<RightEntity, Right> TO_RIGHT_CONVERTER = new DefaultEntity2RightConverter();


    @Test
    void shouldCheckGettingById_ifEntityIsAbsence() {
        Supplier<RightRepo> repoSupplier = () -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.empty());

            return repo;
        };

        ObjectId objectId = new ObjectId();
        RightServiceImpl service = new RightServiceImpl(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.getById(objectId);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(RightServiceImpl.Code.ENTITY_BY_ID_ABSENCE))
                        .args(objectId)
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGettingById_ifEntityIsPresent() {
        Function<RightEntity, RightRepo> repoFunction = entity -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.of(entity));

            return repo;
        };

        ObjectId expectedId = new ObjectId();
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setId(expectedId);
        entity.setValue(expectedValue);

        RightServiceImpl service = new RightServiceImpl(repoFunction.apply(entity), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.getById(expectedId);

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Right right = result.value();
        assertWith(right.id()).isEqualTo(expectedId);
        assertThat(right.value()).isEqualTo(expectedValue);
    }

    @Test
    void shouldCheckFindByValue_ifEntityAbsence() {
        Supplier<RightRepo> repoSupplier = () -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.findByValue(Mockito.any())).thenReturn(Optional.empty());

            return repo;
        };

        String expectedValue = Faker.str_().random();
        RightServiceImpl service = new RightServiceImpl(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.findOneByValue(expectedValue);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(RightServiceImpl.Code.ENTITY_BY_VALUE_ABSENCE))
                        .args(expectedValue)
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckFindByValue_ifEntityIsPresent() {
        Function<RightEntity, RightRepo> repoFunction = entity -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.findByValue(Mockito.any())).thenReturn(Optional.of(entity));

            return repo;
        };

        ObjectId expectedId = new ObjectId();
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setId(expectedId);
        entity.setValue(expectedValue);

        RightServiceImpl service = new RightServiceImpl(repoFunction.apply(entity), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.findOneByValue(expectedValue);

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Right right = result.value();
        assertWith(right.id()).isEqualTo(expectedId);
        assertThat(right.value()).isEqualTo(expectedValue);
    }

    @Test
    void shouldCheckSaving_ifEntityIsAbsence() {
        Supplier<RightRepo> repoSupplier = () -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.save(Mockito.any())).thenThrow(DuplicateKeyException.class);

            return repo;
        };

        String expectedValue = Faker.str_().random();
        Right right = RightImpl.create(expectedValue);

        RightServiceImpl service = new RightServiceImpl(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.save(right);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(RightServiceImpl.Code.FAIL_SAVING))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckSaving_ifEntityIsPresent() {
        Function<RightEntity, RightRepo> repoFunction = entity -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.save(Mockito.any())).thenReturn(entity);

            return repo;
        };

        ObjectId expectedId = new ObjectId();
        String expectedValue = Faker.str_().random();

        RightEntityImpl entity = new RightEntityImpl();
        entity.setId(expectedId);
        entity.setValue(expectedValue);

        RightServiceImpl service = new RightServiceImpl(repoFunction.apply(entity), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.save(RightImpl.create(expectedValue));

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Right gotterRight = result.value();
        assertWith(gotterRight.id()).isEqualTo(expectedId);
        assertThat(gotterRight.value()).isEqualTo(expectedValue);
    }

    @Test
    void shouldCheckDeleteById() {
        Supplier<RightRepo> repoSupplier = () -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito
                    .doNothing()
                    .when(repo)
                    .deleteById(Mockito.any());
            return repo;
        };

        ObjectId expectedId = new ObjectId();
        RightServiceImpl service = new RightServiceImpl(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.deleteById(expectedId);

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(null)
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckDeleteByValue_ifEntityAbsence() {
        Supplier<RightRepo> repoSupplier = () -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.deleteByValue(Mockito.any())).thenReturn(Optional.empty());

            return repo;
        };

        RightServiceImpl service = new RightServiceImpl(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.deleteByValue(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(null)
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckDeleteByValue_ifEntityPresent() {
        Function<RightEntity, RightRepo> repoFunction = entity -> {
            RightRepo repo = Mockito.mock(RightRepo.class);
            Mockito.when(repo.deleteByValue(Mockito.any())).thenReturn(Optional.of(entity));

            return repo;
        };

        ObjectId expectedId = new ObjectId();
        String expectedValue = Faker.str_().random();

        RightEntityImpl entity = new RightEntityImpl();
        entity.setId(expectedId);
        entity.setValue(expectedValue);

        RightServiceImpl service = new RightServiceImpl(repoFunction.apply(entity), TO_ENTITY_CONVERTER, TO_RIGHT_CONVERTER);
        Result<Right> result = service.deleteByValue(Faker.str_().random());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Right gottenRight = result.value();
        assertThat(gottenRight.id()).isEqualTo(expectedId);
        assertThat(gottenRight.value()).isEqualTo(expectedValue);
    }
}