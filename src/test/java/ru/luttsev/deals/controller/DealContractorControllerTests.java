package ru.luttsev.deals.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;
import ru.luttsev.deals.model.payload.dealcontractor.SaveDealContractorPayload;
import ru.luttsev.deals.service.DealContractorService;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(roles = {"USER", "SUPERUSER"})
@AutoConfigureMockMvc
class DealContractorControllerTests {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DealContractorService dealContractorService;

    private DealContractor createDealContractor(String name, UUID id) {
        return DealContractor.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Test
    @DisplayName("Сохранение контрагента в сделке")
    void testCreateDealContractor() throws Exception {
        DealContractor contractor = createDealContractor("Contractor1", UUID.randomUUID());
        SaveDealContractorPayload saveContractorPayload = modelMapper.map(contractor, SaveDealContractorPayload.class);
        when(dealContractorService.save(contractor)).thenReturn(contractor);

        mvc.perform(put("/deal-contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveContractorPayload)))
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
    @DisplayName("Удаление контрагента в сделке")
    void testDeleteDealContractor() throws Exception {
        DealContractor contractor = createDealContractor("Contractor2", UUID.randomUUID());
        doNothing().when(dealContractorService).deleteById(contractor.getId());

        mvc.perform(delete("/deal-contractor/delete/{id}", contractor.getId()))
                .andDo(print())
                .andExpect(
                        status().isOk()
                );
    }

}
