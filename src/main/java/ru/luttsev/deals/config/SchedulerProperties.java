package ru.luttsev.deals.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scheduler")
@Data
public class SchedulerProperties {

    private String jobGroupName = "PERMANENT";

    private String cron = "0 * * ? * * *";

}
