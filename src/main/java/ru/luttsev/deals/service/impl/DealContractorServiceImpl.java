package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealContractorNotFoundException;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.repository.DealContractorRepository;
import ru.luttsev.deals.service.DealContractorService;
import ru.luttsev.deals.service.OutboxService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealContractorServiceImpl implements DealContractorService {

    private final DealContractorRepository dealContractorRepository;

    private final ContractorService contractorService;

    private final OutboxService outboxService;

    @Override
    public List<DealContractor> getAll() {
        return dealContractorRepository.findAll();
    }

    @Override
    public DealContractor getById(UUID id) {
        return dealContractorRepository.findById(id).orElseThrow(
                () -> new DealContractorNotFoundException(id)
        );
    }

    @Override
    public DealContractor save(DealContractor entity) {
        return dealContractorRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        DealContractor dealContractor = this.getById(id);
        if (dealContractor.isMain()) {
            boolean sendMainBorrowerResult = contractorService.sendMainBorrower(dealContractor.getContractorId(), false);
            Outbox message = Outbox.builder()
                    .contractorId(dealContractor.getContractorId())
                    .isMain(false)
                    .status(sendMainBorrowerResult ? MessageStatus.SUCCESS : MessageStatus.ERROR)
                    .build();
            outboxService.save(message);
        }
        dealContractor.setActive(false);
        this.save(dealContractor);
    }

}
