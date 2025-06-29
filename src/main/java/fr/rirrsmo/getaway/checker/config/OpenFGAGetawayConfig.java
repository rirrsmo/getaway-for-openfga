package fr.rirrsmo.getaway.checker.config;

import dev.openfga.sdk.api.client.OpenFgaClient;
import fr.rirrsmo.getaway.checker.aspect.OpenFGACheckerAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class OpenFGAGetawayConfig {

    @Bean
    public OpenFGACheckerAspect openFGACheckerAspect(OpenFgaClient openFgaClient) {
        return new OpenFGACheckerAspect(openFgaClient);
    }
}
