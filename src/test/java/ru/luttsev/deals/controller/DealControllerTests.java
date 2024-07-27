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
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealStatus;
import ru.luttsev.deals.model.payload.deal.ChangeDealStatusPayload;
import ru.luttsev.deals.model.payload.deal.DealPayload;
import ru.luttsev.deals.model.payload.deal.SaveDealPayload;
import ru.luttsev.deals.model.payload.dealstatus.DealStatusPayload;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.DealStatusService;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {DealController.class})
@Import(TestApplicationConfig.class)
@AutoConfigureMockMvc
class DealControllerTests {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DealService dealService;

    @MockBean
    private DealStatusService dealStatusService;

    private Deal createDeal(String description, UUID id) {
        return Deal.builder()
                .id(id)
                .description(description)
                .build();
    }

    @Test
    @DisplayName("Получение сделки по ID")
    void testGetDealById() throws Exception {
        Deal deal = createDeal("Test description", UUID.randomUUID());
        when(dealService.getById(deal.getId())).thenReturn(deal);

        mvc.perform(get("/deal/{id}", deal.getId())
                ).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        modelMapper.map(deal, DealPayload.class)
                                )
                        )
                );
    }

    @Test
    @DisplayName("Сохранение сделки")
    void testSaveDeal() throws Exception {
        SaveDealPayload saveDealPayload = SaveDealPayload.builder()
                .id(UUID.randomUUID().toString())
                .description("Test description")
                .agreementNumber("12345678")
                .status(DealStatusPayload.builder()
                        .id("DRAFT")
                        .name("Черновик")
                        .build())
                .build();
        Deal deal = modelMapper.map(saveDealPayload, Deal.class);
        when(dealService.save(deal)).thenReturn(deal);

        mvc.perform(put("/deal/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveDealPayload))
                ).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        modelMapper.map(deal, DealPayload.class)
                                )
                        )
                );
    }

    @Test
    @DisplayName("Назначение статуса сделки")
    void testChangeStatusToDeal() throws Exception {
        Deal deal = createDeal("Test description", UUID.randomUUID());
        DealStatus dealStatus = DealStatus.builder()
                .id("DRAFT")
                .name("Черновик")
                .build();
        deal.setStatus(dealStatus);
        when(dealStatusService.updateStatus(deal.getId().toString(), dealStatus.getId())).thenReturn(deal);

        mvc.perform(patch("/deal/change/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        ChangeDealStatusPayload.builder()
                                                .dealId(deal.getId().toString())
                                                .statusId(dealStatus.getId())
                                                .build()
                                )
                        )
                ).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        modelMapper.map(deal, DealPayload.class)
                                )
                        )
                );
    }

    

}
