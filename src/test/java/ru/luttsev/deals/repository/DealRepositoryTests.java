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
import ru.luttsev.deals.model.entity.DealStatus;
import ru.luttsev.deals.model.entity.DealType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(PostgresContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DealRepositoryTests {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Deal createDeal() {
        DealType dealType = DealType.builder()
                .id("test_type")
                .name("test type")
                .build();
        DealStatus dealStatus = DealStatus.builder()
                .id("test_status")
                .name("test status")
                .build();
        return Deal.builder()
                .description("Test description")
                .agreementNumber("12345678")
                .agreementDate(LocalDate.now())
                .agreementStartDate(LocalDateTime.now())
                .availabilityDate(LocalDate.now().plusDays(1))
                .type(dealType)
                .status(dealStatus)
                .contractors(new ArrayList<>())
                .sum(BigDecimal.valueOf(100000))
                .closeDate(LocalDateTime.now().plusDays(7))
                .createDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Получение сделки по ID")
    void testGetDealById() {
        Deal persistDeal = testEntityManager.persist(createDeal());
        assertTrue(dealRepository.findById(persistDeal.getId()).isPresent());
    }

    @Test
    @DisplayName("Сохранение новой сделки")
    void testCreateNewDeal() {
        Deal deal = createDeal();
        dealRepository.save(deal);
        assertNotNull(testEntityManager.find(Deal.class, deal.getId()));
    }

    @Test
    @DisplayName("Обновление существующей сделки")
    void testUpdateExistsDeal() {
        Deal persistDeal = testEntityManager.persist(createDeal());
        persistDeal.setDescription("Updated test description");
        dealRepository.save(persistDeal);
        assertEquals(testEntityManager.find(Deal.class, persistDeal.getId()).getDescription(), persistDeal.getDescription());
    }

    @Test
    @DisplayName("Удаление сделки по ID")
    void testDeleteDealById() {
        Deal persistDeal = testEntityManager.persist(createDeal());
        dealRepository.deleteById(persistDeal.getId());
        assertNull(testEntityManager.find(Deal.class, persistDeal.getId()));
    }

}
