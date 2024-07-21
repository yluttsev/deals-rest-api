package ru.luttsev.deals.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.service.OutboxService;
import ru.luttsev.deals.service.impl.ContractorService;

@Component
@RequiredArgsConstructor
public class SendMainBorrowerJob implements Job {

    private final OutboxService outboxService;

    private final ContractorService contractorService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Outbox lastOutbox = outboxService.getLastMessage();
        if (lastOutbox.getStatus().equals(MessageStatus.ERROR)) {
            contractorService.sendMainBorrower(lastOutbox.getContractorId(), lastOutbox.isMain());
        }
    }

}
