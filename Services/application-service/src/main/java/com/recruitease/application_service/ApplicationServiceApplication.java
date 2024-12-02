package com.recruitease.application_service;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class ApplicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper().registerModule(new RecordModule());
	}

	@Bean
	public CommandLineRunner createFunction(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				String createFunctionSQL = """
						CREATE OR REPLACE FUNCTION record_application_changes()
						RETURNS TRIGGER AS $$
						BEGIN
						    INSERT INTO history (application_id, status, updated_at)
						    VALUES (NEW.application_id, NEW.status, NOW());

						    RETURN NEW;
						END;
						$$ LANGUAGE plpgsql;
						""";

				jdbcTemplate.execute(createFunctionSQL);
				System.out.println("Trigger function `record_application_changes` created or updated successfully.");
			} catch (Exception e) {
				System.err.println("Error creating trigger function: " + e.getMessage());
			}
		};
	}

	@Bean
	public CommandLineRunner createTriggerRunner(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				String createTriggerSQL = """
						CREATE TRIGGER application_changes_trigger
						AFTER INSERT OR UPDATE OF status ON application
						FOR EACH ROW
						EXECUTE FUNCTION record_application_changes();
						""";

				jdbcTemplate.execute(createTriggerSQL);
				System.out.println("Trigger `application_changes_trigger` created or updated successfully.");
			} catch (Exception e) {
				System.err.println("Error creating trigger: " + e.getMessage());
			}
		};
	}
}
