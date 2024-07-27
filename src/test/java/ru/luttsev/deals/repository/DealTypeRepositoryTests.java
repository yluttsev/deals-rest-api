package ru.luttsev.deals.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.luttsev.deals.PostgresContainerConfig;
import ru.luttsev.deals.model.entity.DealType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(PostgresContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DealTypeRepositoryTests {

    @Autowired
    private DealTypeRepository dealTypeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private DealType createDealType() {
        return DealType.builder()
                .id("test_id")
                .name("Test type")
                .build();
    }

    @Test
    @DisplayName("Поиск типа сделки по ID")
    void testFindDealTypeById() {
        DealType persistDeal = testEntityManager.persist(createDealType());
        assertTrue(dealTypeRepository.findById(persistDeal.getId()).isPresent());
    }

    @Test
    @DisplayName("Создание нового типа сделки")
    void testCreateNewDealType() {
        DealType dealType = createDealType();
        dealTypeRepository.save(dealType);
        assertNotNull(testEntityManager.find(DealType.class, dealType.getId()));
    }

    @Test
    @DisplayName("Обновление существующего типа сделки")
    void testUpdateExistsDealType() {
        DealType dealType = testEntityManager.persist(createDealType());
        dealType.setName("Updated name");
        dealTypeRepository.save(dealType);
        assertEquals(testEntityManager.find(DealType.class, dealType.getId()).getName(), dealType.getName());
    }

    @Test
    @DisplayName("Удаление типа сделки по ID")
    void testDeleteDealStatusById() {
        DealType dealType = testEntityManager.persist(createDealType());
        dealTypeRepository.deleteById(dealType.getId());
        assertNull(testEntityManager.find(DealType.class, dealType.getId()));
    }

}
