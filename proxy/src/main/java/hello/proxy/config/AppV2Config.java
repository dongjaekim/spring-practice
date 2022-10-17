package hello.proxy.config;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV2Config {

    @Bean
    public OrderControllerV2 OrderControllerV2() {
        return new OrderControllerV2(OrderServiceV2());
    }

    @Bean
    public OrderServiceV2 OrderServiceV2() {
        return new OrderServiceV2(OrderRepositoryV2());
    }

    @Bean
    public OrderRepositoryV2 OrderRepositoryV2() {
        return new OrderRepositoryV2();
    }
}
