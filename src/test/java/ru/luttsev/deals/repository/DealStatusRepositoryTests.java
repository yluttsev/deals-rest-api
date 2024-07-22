package ru.luttsev.deals.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.luttsev.deals.model.entity.DealStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DealStatusRepositoryTests {

    @Autowired
    private DealStatusRepository dealStatusRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private DealStatus createStatus() {
        return DealStatus.builder()
                .id("test_id")
                .name("Test status")
                .build();
    }

    @Test
    @DisplayName("Поиск статуса сделки по ID")
    void testFindStatusById() {
        DealStatus persistStatus = testEntityManager.persist(createStatus());
        assertTrue(dealStatusRepository.findById(persistStatus.getId()).isPresent());
    }

    @Test
    @DisplayName("Создание нового статуса сделки")
    void testCreateNewDealStatus() {
        DealStatus dealStatus = createStatus();
        dealStatusRepository.save(dealStatus);
        assertNotNull(testEntityManager.find(DealStatus.class, dealStatus.getId()));
    }

    @Test
    @DisplayName("Обновление существующего статуса сделки")
    void testUpdateExistsDealStatus() {
        DealStatus dealStatus = testEntityManager.persist(createStatus());
        dealStatus.setName("Updated name");
        dealStatusRepository.save(dealStatus);
        assertEquals(testEntityManager.find(DealStatus.class, dealStatus.getId()).getName(), dealStatus.getName());
    }

    @Test
    @DisplayName("Удаление статуса сделки по ID")
    void testDeleteDealStatusById() {
        DealStatus dealStatus = testEntityManager.persist(createStatus());
        dealStatusRepository.deleteById(dealStatus.getId());
        assertNull(testEntityManager.find(DealStatus.class, dealStatus.getId()));
    }

}
