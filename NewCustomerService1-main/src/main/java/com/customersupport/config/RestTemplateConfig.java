package com.customersupport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    
    @Bean
    public RestTemplate restTemplate(AppProperties props) {
        int ct = props.getPolicy().getConnectTimeoutMs();
        int rt = props.getPolicy().getReadTimeoutMs();
        
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(
            new SimpleClientHttpRequestFactory() {{
                setConnectTimeout(ct);
                setReadTimeout(rt);
            }}
        );
        
        return new RestTemplate(factory);
    }
}