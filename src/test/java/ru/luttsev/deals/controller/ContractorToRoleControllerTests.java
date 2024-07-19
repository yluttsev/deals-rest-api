package ru.luttsev.deals.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.luttsev.deals.TestApplicationConfig;
import ru.luttsev.deals.model.entity.ContractorRole;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;
import ru.luttsev.deals.model.payload.dealcontractor.SetRoleToDealContractorPayload;
import ru.luttsev.deals.service.ContractorRoleService;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ContractorToRoleController.class})
@Import(TestApplicationConfig.class)
@AutoConfigureMockMvc
class ContractorToRoleControllerTests {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContractorRoleService contractorRoleService;

    private DealContractor createContractor(String name) {
        return DealContractor.builder()
                .id(UUID.randomUUID())
                .name(name)
                .roles(new ArrayList<>())
                .build();
    }

    private ContractorRole createContractorRole(String name) {
        return ContractorRole.builder()
                .id(name + " ID")
                .name(name)
                .build();
    }

    @Test
    @DisplayName("Добавление роли контрагенту")
    void testAddRoleToDealContractor() throws Exception {
        DealContractor contractor = createContractor("Contractor1");
        ContractorRole role = createContractorRole("Role1");
        contractor.getRoles().add(role);
        when(contractorRoleService.addRole(contractor.getId().toString(), role.getId())).thenReturn(contractor);
        contractor.getRoles().clear();

        mvc.perform(post("/contractor-to-role/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        SetRoleToDealContractorPayload.builder()
                                                .contractorId(contractor.getId().toString())
                                                .roleId(role.getId())
                                                .build()
                                )
                        ))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        modelMapper.map(contractor, DealContractorPayload.class)
                                )
                        )
                );
    }

    @Test
    @DisplayName("Удаление роли у контрагента")
    void deleteRoleToDealContractor() throws Exception {
        DealContractor contractor = createContractor("Contractor2");
        ContractorRole role = createContractorRole("Role2");
        when(contractorRoleService.deleteRole(contractor.getId().toString(), role.getId())).thenReturn(contractor);
        contractor.getRoles().add(role);

        mvc.perform(delete("/contractor-to-role/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        SetRoleToDealContractorPayload.builder()
                                                .roleId(role.getId())
                                                .contractorId(contractor.getId().toString())
                                                .build()
                                )
                        ))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        modelMapper.map(contractor, DealContractorPayload.class)
                                )
                        )
                );
    }

}
