package com.core.config;


import com.core.domain.User;
import com.core.domain.cq.UserQuery;
import com.core.infrastructure.UserRepository;
import com.core.util.BloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Slf4j
@Configuration
public class BloomFilterConfig {

    private final UserRepository userRepository;

    public BloomFilterConfig(UserRepository userRepository) {
        this.userRepository = userRepository;

    }
    @Bean
    public BloomFilter bloomFilter() {
        log.info("初始化bloomFilter");

        BloomFilter bloomFilter = new BloomFilter(1000000,4);
        List<User> users = userRepository.findList(new UserQuery(null));
        for (User user: users
        ) {
            bloomFilter.add(user.getName());
        }
        log.info("bloomFilter加载完成");
        return bloomFilter;
    }
}
