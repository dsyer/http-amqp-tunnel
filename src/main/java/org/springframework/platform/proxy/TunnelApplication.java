package org.springframework.platform.proxy;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath:/application.xml")
public class TunnelApplication {

    public static void main(String[] args) {
        SpringApplication.run(TunnelApplication.class, args);
    }
    
    @Bean
    public Queue inputQueue() {
    	return new Queue("input");
    }
    
    @Bean
    public ServletRegistrationBean httpInboundGatewayServletRegistration() {
    	ServletRegistrationBean bean = new ServletRegistrationBean(httpInboundGatewayServlet(), "/input/*");
    	bean.setName("httpInboundGateway");
		return bean;
    }
    
    @Bean
    public HttpRequestHandlerServlet httpInboundGatewayServlet() {
    	return new HttpRequestHandlerServlet();
    }

}
