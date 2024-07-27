package ru.luttsev.deals.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.luttsev.deals.PostgresContainerConfig;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.entity.DealStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(PostgresContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DealContractorRepositoryTests {

    @Autowired
    private DealContractorRepository dealContractorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private DealContractor createDealContractor() {
        return DealContractor.builder()
                .deal(
                        Deal.builder()
                                .id(UUID.randomUUID())
                                .description("test deal")
                                .status(DealStatus.builder()
                                        .id("DRAFT")
                                        .name("Черновик")
                                        .build())
                                .createDate(LocalDateTime.now())
                                .build()
                )
                .name("Test role")
                .contractorId("test_id")
                .createDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Поиск контрагента сделки по ID")
    void testFindDealContractorById() {
        DealContractor dealContractor = createDealContractor();
        testEntityManager.persist(dealContractor);
        assertTrue(dealContractorRepository.findById(dealContractor.getId()).isPresent());
    }

    @Test
    @DisplayName("Создание нового контрагента сделки")
    void testCreateNewDealContractor() {
        DealContractor dealContractor = createDealContractor();
        dealContractorRepository.save(dealContractor);
        assertNotNull(testEntityManager.find(DealContractor.class, dealContractor.getId()));
    }

    @Test
    @DisplayName("Обновление существующего контрагента сделки")
    void testUpdateExistsDealContractor() {
        DealContractor dealContractor = testEntityManager.persist(createDealContractor());
        dealContractor.setName("Updated name");
        dealContractorRepository.save(dealContractor);
        assertEquals(testEntityManager.find(DealContractor.class, dealContractor.getId()).getName(), dealContractor.getName());
    }

    @Test
    @DisplayName("Удаление контрагента сделки по ID")
    void testDeleteDealContractorById() {
        DealContractor dealContractor = testEntityManager.persist(createDealContractor());
        dealContractorRepository.deleteById(dealContractor.getId());
        assertNull(testEntityManager.find(DealContractor.class, dealContractor.getId()));
    }

}
