package ru.luttsev.deals.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;
import ru.luttsev.deals.model.payload.dealcontractor.SaveDealContractorPayload;
import ru.luttsev.deals.service.DealContractorService;

import java.util.UUID;

/**
 * Контроллер для работы с контрагентами сделки
 *
 * @author Yuri Luttsev
 */
@RestController
@RequestMapping("/deal-contractor")
@RequiredArgsConstructor
public class DealContractorController {

    /**
     * {@link DealContractorService Сервис для работы с контрагентами сделки}
     */
    private final DealContractorService dealContractorService;

    /**
     * {@link ModelMapper Маппер сущностей и DTO}
     */
    private final ModelMapper mapper;

    /**
     * Создает нового или обновляет существующего контрагента сделки
     *
     * @param payload {@link SaveDealContractorPayload DTO сохранения контрагента сделки}
     * @return {@link DealContractorPayload DTO} созданного или обновленного контрагента сделки
     */
    @PutMapping("/save")
    public DealContractorPayload saveDealContractor(@RequestBody SaveDealContractorPayload payload) {
        DealContractor dealContractor = mapper.map(payload, DealContractor.class);
        DealContractor savedDealContractor = dealContractorService.save(dealContractor);
        return mapper.map(savedDealContractor, DealContractorPayload.class);
    }

    /**
     * Удаляет контрагента сделки по ID
     *
     * @param dealContractorId ID контрагента сделки
     * @return {@link ResponseEntity} пустой ответ
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDealContractorById(@PathVariable("id") String dealContractorId) {
        dealContractorService.deleteById(UUID.fromString(dealContractorId));
        return ResponseEntity.ok().build();
    }

}
