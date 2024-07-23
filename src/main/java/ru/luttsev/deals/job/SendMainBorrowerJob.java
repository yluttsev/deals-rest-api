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

import java.util.List;

/**
 * Задача отправки сообщений в сервис контрагентов
 *
 * @author Yuri Luttsev
 */
@Component
@RequiredArgsConstructor
public class SendMainBorrowerJob implements Job {

    /**
     * {@link OutboxService Сервис для работы с outbox таблицей}
     */
    private final OutboxService outboxService;

    /**
     * {@link ContractorService Сервис для работы с внешним сервисом контрагентов}
     */
    private final ContractorService contractorService;

    /**
     * Выполняемая работа по отправке сообщений
     *
     * @param jobExecutionContext контекст выполнения задачи
     * @throws JobExecutionException ошибка выполнения задачи
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Outbox> errorMessages = outboxService.getErrorMessages();
        errorMessages.forEach(message -> {
            boolean result = contractorService.sendMainBorrower(message.getContractorId(), message.isMain());
            if (result) {
                outboxService.updateMessageStatus(message.getId().toString(), MessageStatus.SUCCESS.name());
            }
        });
    }

}
