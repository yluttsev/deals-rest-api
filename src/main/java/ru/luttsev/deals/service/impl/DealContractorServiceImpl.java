package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * Реализация сервиса для работы с контрагентами сделки
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
@PreAuthorize("!hasRole('ADMIN')")
public class DealContractorServiceImpl implements DealContractorService {

    private final DealContractorRepository dealContractorRepository;

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
    @PreAuthorize("hasAnyRole('DEAL_SUPERUSER', 'SUPERUSER')")
    public DealContractor save(DealContractor entity) {
        return dealContractorRepository.save(entity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('DEAL_SUPERUSER', 'SUPERUSER')")
    public void deleteById(UUID id) {
        DealContractor dealContractor = this.getById(id);
        if (dealContractor.isMain()) {
            Outbox lastOutboxMessage = outboxService.getLastOutboxMessage(dealContractor.getContractorId());
            if (lastOutboxMessage != null) {
                lastOutboxMessage.setMain(false);
            } else {
                lastOutboxMessage = Outbox.builder()
                        .contractorId(dealContractor.getContractorId())
                        .isMain(false)
                        .status(MessageStatus.CREATED)
                        .build();
            }
            outboxService.save(lastOutboxMessage);
        }
        dealContractor.setActive(false);
        this.save(dealContractor);
    }

}
