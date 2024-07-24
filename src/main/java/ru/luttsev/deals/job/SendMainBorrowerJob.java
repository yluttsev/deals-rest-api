package ru.luttsev.deals.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import ru.luttsev.deals.service.OutboxService;

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
     * Выполняемая работа по отправке сообщений
     *
     * @param jobExecutionContext контекст выполнения задачи
     * @throws JobExecutionException ошибка выполнения задачи
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        outboxService.resendMessage();
    }

}
