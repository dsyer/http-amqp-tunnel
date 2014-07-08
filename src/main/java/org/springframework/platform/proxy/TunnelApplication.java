package org.springframework.platform.proxy;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${outboundQueue}")
	private String outboundQueue;
    @Value("${inboundQueue}")
	private String inboundQueue;
    
    @Bean
    public Queue requestQueue() {
    	return new Queue(outboundQueue, true, false, true);
    }
    
    @Bean
    public Queue targetQueue() {
    	return new Queue(inboundQueue, true, false, true);
    }
    
    @Bean
    public ServletRegistrationBean httpInboundGatewayServletRegistration() {
    	ServletRegistrationBean bean = new ServletRegistrationBean(httpInboundGatewayServlet(), "/tunnel/*");
    	bean.setName("httpInboundGateway");
		return bean;
    }
    
    @Bean
    public HttpRequestHandlerServlet httpInboundGatewayServlet() {
    	return new HttpRequestHandlerServlet();
    }

}
