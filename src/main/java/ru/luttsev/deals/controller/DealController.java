package ru.luttsev.deals.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.payload.deal.ChangeDealStatusPayload;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;
import ru.luttsev.deals.model.payload.deal.DealPagePayload;
import ru.luttsev.deals.model.payload.deal.DealPayload;
import ru.luttsev.deals.model.payload.deal.SaveDealPayload;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.DealStatusService;

import java.util.UUID;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    private final DealStatusService dealStatusService;

    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public DealPayload getDealById(@PathVariable("id") UUID dealId) {
        Deal deal = dealService.getById(dealId);
        return mapper.map(deal, DealPayload.class);
    }

    @PutMapping("/save")
    public DealPayload saveDeal(@RequestBody SaveDealPayload payload) {
        Deal deal = mapper.map(payload, Deal.class);
        Deal savedDeal = dealService.save(deal);
        return mapper.map(savedDeal, DealPayload.class);
    }

    @PatchMapping("/change/status")
    public DealPayload changeDealStatus(@RequestBody ChangeDealStatusPayload payload) {
        Deal deal = dealStatusService.updateStatus(payload.getDealId(), payload.getStatusId());
        return mapper.map(deal, DealPayload.class);
    }

    @PostMapping("/search")
    public DealPagePayload searchDeals(@RequestBody DealFiltersPayload filtersPayload,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "contentSize", defaultValue = "10") int contentSize) {
        return dealService.getByFilters(filtersPayload, page, contentSize);
    }

}
