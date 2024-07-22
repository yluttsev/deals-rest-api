package ru.luttsev.deals.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

/**
 * Конфигурация планировщика задач
 *
 * @author Yuri Luttsev
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

    /**
     * Свойства планировщика
     */
    private final SchedulerProperties schedulerProperties;

    /**
     * Объект планировщика задач
     *
     * @param triggers             триггеры планировщика
     * @param jobDetails           информация о задаче
     * @param schedulerFactoryBean бин фабрики планировщиков
     * @return объект планировщика задач
     * @throws SchedulerException ошибка планировщика задач
     */
    @Bean
    public Scheduler scheduler(List<Trigger> triggers,
                               List<JobDetail> jobDetails,
                               SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        revalidateJobs(jobDetails, scheduler);
        rescheduleTriggers(triggers, scheduler);
        scheduler.start();
        return scheduler;
    }

    /**
     * Перепланировка триггеров в планировщике задач
     *
     * @param triggers  триггеры планировщика
     * @param scheduler планировщик задач
     */
    private void rescheduleTriggers(List<Trigger> triggers, Scheduler scheduler) {
        triggers.forEach(
                trigger -> {
                    try {
                        if (scheduler.checkExists(trigger.getKey())) {
                            scheduler.scheduleJob(trigger);
                        } else {
                            scheduler.rescheduleJob(trigger.getKey(), trigger);
                        }
                    } catch (SchedulerException e) {
                        log.error("Reschedule triggers error. {}", e.getMessage());
                    }
                }
        );
    }

    /**
     * Проверка невалидных задач в планировщике
     *
     * @param jobDetails информация о задаче
     * @param scheduler  планировщик задач
     */
    private void revalidateJobs(List<JobDetail> jobDetails, Scheduler scheduler) {
        List<JobKey> jobKeys = jobDetails.stream().map(JobDetail::getKey).toList();
        try {
            scheduler.getJobKeys(GroupMatcher.jobGroupEquals(schedulerProperties.getJobGroupName())).forEach(jobKey -> {
                if (!jobKeys.contains(jobKey)) {
                    try {
                        scheduler.deleteJob(jobKey);
                    } catch (SchedulerException e) {
                        log.error("Delete job error. {}", e.getMessage());
                    }
                }
            });
        } catch (SchedulerException e) {
            log.error("Revalidate jobs error. {}", e.getMessage());
        }
    }

}
