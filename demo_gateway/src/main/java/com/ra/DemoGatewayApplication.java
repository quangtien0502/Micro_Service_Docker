package com.ra;

import com.ra.config.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoGatewayApplication {
	@Autowired
	private AuthenticationFilter authenticationFilter;

	public static void main(String[] args) {
		SpringApplication.run(DemoGatewayApplication.class, args);
	}
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder){
		return builder
				.routes()
				.route(r->r.path("/student/**").filters(f->f.filters(authenticationFilter)).uri("http://localhost:8082/"))
				.route(r->r.path("/v1/permit/auth/**").uri("http://localhost:8011/"))
				.route(r->r.path("/v1/admin/product/**","/v1/user/product/**","/v1/user/product","/v1/admin/product","/v1/user/categories/**","/v1/admin/categories/**","/v1/admin/categories/","/v1/user/categories/").filters(f->f.filters(authenticationFilter)).uri("http://localhost:8012/"))
				.route(r->r.path("/v1/admin/order","/v1/user/order","/v1/admin/shopping-cart","/v1/user/shopping-cart").filters(f->f.filters(authenticationFilter)).uri("http://localhost:8013/"))
				.build();
	}

}
