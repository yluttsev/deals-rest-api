package ru.luttsev.deals.config;

import lombok.RequiredArgsConstructor;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.luttsev.deals.job.SendMainBorrowerJob;

@Configuration
@EnableConfigurationProperties(SchedulerProperties.class)
@RequiredArgsConstructor
public class SendMainBorrowerJobConfig {

    private final SchedulerProperties schedulerProperties;

    @Bean
    public JobDetail mainBorrowerJob() {
        return JobBuilder.newJob(SendMainBorrowerJob.class)
                .withIdentity("mainBorrowerJob", schedulerProperties.getJobGroupName())
                .storeDurably()
                .requestRecovery(true)
                .build();
    }

    @Bean
    public Trigger mainBorrowerTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(mainBorrowerJob())
                .withIdentity("mainBorrowerJobTrigger", schedulerProperties.getJobGroupName())
                .withSchedule(CronScheduleBuilder.cronSchedule(schedulerProperties.getCron()))
                .build();
    }

}
