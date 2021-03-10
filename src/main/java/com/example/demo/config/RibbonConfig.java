package com.example.demo.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {

    @Bean
    @ConditionalOnMissingBean
    public IRule ribbonRule() {
        System.out.println("-------ribbon轮询策略------");
        return new RoundRobinRule();
    }

}
