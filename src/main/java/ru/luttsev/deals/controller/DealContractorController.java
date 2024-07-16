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
import ru.luttsev.deals.model.payload.dealcontractor.SaveDealPayload;
import ru.luttsev.deals.service.DealContractorService;

import java.util.UUID;

@RestController
@RequestMapping("/deal-contractor")
@RequiredArgsConstructor
public class DealContractorController {

    private final DealContractorService dealContractorService;

    private final ModelMapper mapper;

    @PutMapping("/save")
    public DealContractorPayload saveDealContractor(@RequestBody SaveDealPayload payload) {
        DealContractor dealContractor = mapper.map(payload, DealContractor.class);
        DealContractor savedDealContractor = dealContractorService.save(dealContractor);
        return mapper.map(savedDealContractor, DealContractorPayload.class);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDealContractorById(@PathVariable("id") String dealContractorId) {
        dealContractorService.deleteById(UUID.fromString(dealContractorId));
        return ResponseEntity.ok().build();
    }

}
