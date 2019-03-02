package org.gavrilov.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.gavrilov.domain")
@EnableJpaRepositories(basePackages = "org.gavrilov.repository")
@ComponentScan(basePackages = "org.gavrilov")
@Import({CacheConfiguration.class, LoggingAspectConfiguration.class})
public class CoreConfiguration {
}
