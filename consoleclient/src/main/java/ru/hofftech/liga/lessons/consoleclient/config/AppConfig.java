package ru.hofftech.liga.lessons.consoleclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.hofftech.liga.lessons.consoleclient.service.BillingClient;
import ru.hofftech.liga.lessons.consoleclient.service.ParcelLoaderClient;

@Slf4j
@Configuration
public class AppConfig {
    @Value("${services.url.parcels-loader}")
    private String parcelsLoaderUrl;

    @Value("${services.url.billing}")
    private String billingUrl;

    @Bean
    public ParcelLoaderClient parcelLoaderClientService() {
        var client = RestClient.builder()
                .baseUrl(parcelsLoaderUrl)
                .build();

        var adapter = RestClientAdapter.create(client);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return httpServiceProxyFactory.createClient(ParcelLoaderClient.class);
    }

    @Bean
    public BillingClient billingClientService() {
        var client = RestClient.builder()
                .baseUrl(billingUrl)
                .build();

        var adapter = RestClientAdapter.create(client);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return httpServiceProxyFactory.createClient(BillingClient.class);
    }
}
