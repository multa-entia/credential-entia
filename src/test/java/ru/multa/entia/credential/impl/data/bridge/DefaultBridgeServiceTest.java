package ru.multa.entia.credential.impl.data.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.api.data.bridge.BridgeEntity;
import ru.multa.entia.credential.api.data.bridge.BridgeRepo;
import ru.multa.entia.credential.impl.converter.bridge.DefaultBridge2EntityConverter;
import ru.multa.entia.credential.impl.converter.bridge.DefaultEntity2BridgeConverter;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.utils.Results;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBridgeServiceTest {

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    private static final Function<Bridge, BridgeEntity> TO_ENTITY_CONVERTER = new DefaultBridge2EntityConverter();
    private static final Function<BridgeEntity, Bridge> TO_BRIDGE_CONVERTER = new DefaultEntity2BridgeConverter();

    @Test
    void shouldCheckGettingById_ifAbsence() {
        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.empty());

            return repo;
        };

        Result<Bridge> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getById(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultBridgeService.Code.ENTITY_BY_ID_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGettingById() {
        BridgeEntityImpl expectedEntity = new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId());

        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.of(expectedEntity));

            return repo;
        };

        Result<Bridge> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getById(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(TO_BRIDGE_CONVERTER.apply(expectedEntity))
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckSaving_ifFail() {
        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito
                    .when(repo.save(Mockito.any()))
                    .thenThrow(RuntimeException.class);

            return repo;
        };

        Result<Bridge> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .save(new BridgeImpl(new ObjectId(), new ObjectId(), new ObjectId()));

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultBridgeService.Code.FAIL_SAVING))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckSaving() {
        BridgeEntityImpl entity = new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId());
        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito
                    .when(repo.save(Mockito.any()))
                    .thenReturn(entity);

            return repo;
        };

        Result<Bridge> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .save(new BridgeImpl(new ObjectId(), new ObjectId(), new ObjectId()));

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Bridge gottenBridge = result.value();
        assertThat(gottenBridge.id()).isEqualTo(entity.getId());
        assertThat(gottenBridge.userId()).isEqualTo(entity.getUserId());
        assertThat(gottenBridge.rightId()).isEqualTo(entity.getRightId());
    }

    @Test
    void shouldCheckGettingByUserId_ifAbsence() {
        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findByUserId(Mockito.any())).thenReturn(List.of());

            return repo;
        };

        Result<List<Bridge>> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getByUserId(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultBridgeService.Code.ENTITY_BY_USER_ID_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGettingByUserId() {
        List<BridgeEntity> bridgeEntities = List.of(
                new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId()),
                new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId())
        );

        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findByUserId(Mockito.any())).thenReturn(bridgeEntities);

            return repo;
        };

        Result<List<Bridge>> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getByUserId(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(bridgeEntities.stream().map(TO_BRIDGE_CONVERTER).toList())
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGettingByRightId_ifAbsence() {
        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findByRightId(Mockito.any())).thenReturn(List.of());

            return repo;
        };

        Result<List<Bridge>> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getByRightId(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultBridgeService.Code.ENTITY_BY_RIGHT_ID_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGettingByRightId() {
        List<BridgeEntity> bridgeEntities = List.of(
                new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId()),
                new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId())
        );

        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findByRightId(Mockito.any())).thenReturn(bridgeEntities);

            return repo;
        };

        Result<List<Bridge>> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getByRightId(new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(bridgeEntities.stream().map(TO_BRIDGE_CONVERTER).toList())
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGettingByUserAndRightId_ifAbsence() {
        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findByRightId(Mockito.any())).thenReturn(List.of());

            return repo;
        };

        Result<List<Bridge>> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getByUserAndRightIds(new ObjectId(), new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultBridgeService.Code.ENTITY_BY_USER_AND_RIGHT_IDS_ABSENCE))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGettingByUserAndRightId() {
        List<BridgeEntity> bridgeEntities = List.of(
                new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId()),
                new BridgeEntityImpl(new ObjectId(), new ObjectId(), new ObjectId())
        );

        Supplier<BridgeRepo> supplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito.when(repo.findByUserIdAndRightId(Mockito.any(), Mockito.any())).thenReturn(bridgeEntities);

            return repo;
        };

        Result<List<Bridge>> result = new DefaultBridgeService(supplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER)
                .getByUserAndRightIds(new ObjectId(), new ObjectId());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(bridgeEntities.stream().map(TO_BRIDGE_CONVERTER).toList())
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckDeleting() {
        Supplier<BridgeRepo> repoSupplier = () -> {
            BridgeRepo repo = Mockito.mock(BridgeRepo.class);
            Mockito
                    .doNothing()
                    .when(repo)
                    .deleteById(Mockito.any());
            return repo;
        };

        DefaultBridgeService service = new DefaultBridgeService(repoSupplier.get(), TO_ENTITY_CONVERTER, TO_BRIDGE_CONVERTER);
        Result<Bridge> result = service.deleteById(new ObjectId());

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
}