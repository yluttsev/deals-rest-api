package ru.luttsev.deals.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.luttsev.deals.PostgresContainerConfig;
import ru.luttsev.deals.model.entity.ContractorRole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(PostgresContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContractorRoleRepositoryTests {

    @Autowired
    private ContractorRoleRepository contractorRoleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private ContractorRole createRole() {
        return ContractorRole.builder()
                .id("test_id")
                .name("Test role")
                .category("test category")
                .build();
    }

    @Test
    @DisplayName("Поиск роли по ID")
    void testFindRoleById() {
        ContractorRole role = createRole();
        testEntityManager.persist(role);
        assertTrue(contractorRoleRepository.findById(role.getId()).isPresent());
    }

    @Test
    @DisplayName("Создание новой роли")
    void testCreateNewRole() {
        ContractorRole role = createRole();
        contractorRoleRepository.save(role);
        assertNotNull(testEntityManager.find(ContractorRole.class, role.getId()));
    }

    @Test
    @DisplayName("Обновление существующей роли")
    void testUpdateExistsRole() {
        ContractorRole persistRole = testEntityManager.persist(createRole());
        persistRole.setName("Updated test role");
        contractorRoleRepository.save(persistRole);
        assertEquals(testEntityManager.find(ContractorRole.class, persistRole.getId()).getName(), persistRole.getName());
    }

    @Test
    @DisplayName("Удаление роли по ID")
    void testDeleteRoleById() {
        ContractorRole persistRole = testEntityManager.persist(createRole());
        contractorRoleRepository.deleteById(persistRole.getId());
        assertNull(testEntityManager.find(ContractorRole.class, persistRole.getId()));
    }

}
