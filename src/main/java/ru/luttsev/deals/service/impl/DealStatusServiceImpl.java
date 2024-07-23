package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealStatusNotFoundException;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.entity.DealStatus;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.repository.DealStatusRepository;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.DealStatusService;
import ru.luttsev.deals.service.OutboxService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса для работы со статусами сделки
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class DealStatusServiceImpl implements DealStatusService {

    private final DealStatusRepository dealStatusRepository;

    private final DealService dealService;

    private final OutboxService outboxService;

    private final ContractorService contractorService;

    @Override
    public List<DealStatus> getAll() {
        return dealStatusRepository.findAll();
    }

    @Override
    public DealStatus getById(String id) {
        return dealStatusRepository.findById(id).orElseThrow(
                () -> new DealStatusNotFoundException(id)
        );
    }

    @Override
    public DealStatus save(DealStatus entity) {
        return dealStatusRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        dealStatusRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Deal updateStatus(String dealId, String statusId) {
        DealStatus status = this.getById(statusId);
        Deal deal = dealService.getById(UUID.fromString(dealId));

        Optional<DealContractor> contractor = dealService.getMainContractorByDealId(dealId);
        if (contractor.isPresent() && dealService.numberOfActiveDeals(contractor.get().getContractorId()) <= 1) {
            if (deal.getStatus().getId().equals("DRAFT") && status.getId().equals("ACTIVE")) {
                return sendMessageAndSaveStatus(contractor.get().getContractorId(), deal, status, true);
            } else if (deal.getStatus().getId().equals("ACTIVE") && status.getId().equals("CLOSED")) {
                return sendMessageAndSaveStatus(contractor.get().getContractorId(), deal, status, false);
            }
        }

        deal.setStatus(status);
        return dealService.save(deal);
    }

    private Deal sendMessageAndSaveStatus(String contractorId,
                                          Deal deal,
                                          DealStatus status,
                                          boolean isMain) {
        deal.setStatus(status);
        Deal savedDeal = dealService.save(deal);
        boolean sendMainBorrowerResult = contractorService.sendMainBorrower(contractorId, isMain);
        Outbox outboxMessage = Outbox.builder()
                .contractorId(contractorId)
                .isMain(isMain)
                .status(sendMainBorrowerResult ? MessageStatus.SUCCESS : MessageStatus.ERROR)
                .build();
        outboxService.save(outboxMessage);
        return savedDeal;
    }

}
