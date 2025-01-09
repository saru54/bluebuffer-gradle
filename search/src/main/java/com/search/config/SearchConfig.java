package com.search.config;


import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class SearchConfig {
    @Value("${meilisearch.masterkey}")
    private String masterKey;
    @Value("${meilisearch.host}")
    private String host;
    @Bean
    public Client meiliSearchClient(){
        log.info("初始化搜索client");
        return  new Client(new Config(host,masterKey));
    }


}
