package com.recruitease.ticket_service;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class TicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper().registerModule(new RecordModule());
	}

}

