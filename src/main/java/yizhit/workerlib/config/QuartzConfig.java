package yizhit.workerlib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:quartz-config.xml")
public class QuartzConfig {
}
