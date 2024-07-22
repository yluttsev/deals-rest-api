package ru.luttsev.deals.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Свойства планировщика задач
 *
 * @author Yuri Luttsev
 */
@ConfigurationProperties(prefix = "scheduler")
@Data
public class SchedulerProperties {

    /**
     * Название группы задач
     */
    private String jobGroupName = "PERMANENT";

    /**
     * cron расписание
     */
    private String cron = "0 * * ? * * *";

}
