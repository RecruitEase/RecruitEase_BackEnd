package com.recruitease.joblisting;

import com.recruitease.joblisting.model.Field;
import com.recruitease.joblisting.repository.FieldRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class JobListingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobListingApplication.class, args);
	}

		@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper().registerModule(new RecordModule());
	}


	@Bean
	public CommandLineRunner initializeFields(FieldRepository fieldRepository) {
		return args -> {
			List<Field> fields = Arrays.asList(
					new Field(1, "Account & Finance"),
					new Field(2, "Administration / Secretarial"),
					new Field(3, "Agriculture"),
					new Field(4, "Apparel"),
					new Field(5, "Architecture"),
					new Field(6, "Automobile"),
					new Field(7, "Banking and Financial Services"),
					new Field(8, "Beauty & Hairdressing"),
					new Field(9, "BPO/ KPO"),
					new Field(10, "Building & Construction"),
					new Field(11, "Business Management"),
					new Field(12, "Call Center"),
					new Field(13, "Charity / NGO"),
					new Field(14, "Customer Service"),
					new Field(15, "Delivery / Driving / Transport"),
					new Field(16, "Digital Marketing"),
					new Field(17, "Education / Higher Education"),
					new Field(18, "Electronics / Electrical"),
					new Field(19, "Engineering / Manufacturing"),
					new Field(20, "Environment/ Health & Safety"),
					new Field(21, "FMCG/ Food Industry"),
					new Field(23, "Government/ Public Sector"),
					new Field(24, "Hospital/ Nursing/ Healthcare"),
					new Field(25, "Hotel/ Hospitality/ Leisure"),
					new Field(26, "Human Resources / Recruitment"),
					new Field(27, "Insurance"),
					new Field(28, "Interior Design"),
					new Field(29, "Internship / Undergraduate"),
					new Field(30, "IT-HWare/ Networks/ Systems"),
					new Field(31, "IT-SWare / Internet"),
					new Field(32, "Legal / Law"),
					new Field(33, "Media/ Advertising/ Communication/ Design"),
					new Field(34, "Oil, Gas and Nuclear"),
					new Field(35, "Other"),
					new Field(36, "Pharmaceutical"),
					new Field(37, "Production & Operations"),
					new Field(38, "Project Management / Programme Management"),
					new Field(39, "Quality Assurance"),
					new Field(40, "Real Estate"),
					new Field(41, "Recoveries"),
					new Field(42, "Retail / Fashion"),
					new Field(43, "Sales / Marketing / New Business Development"),
					new Field(44, "School Leavers"),
					new Field(45, "Science / Research"),
					new Field(46, "Security/ Military"),
					new Field(47, "Senior Management / Directors"),
					new Field(48, "Sports/Fitness/Recreation"),
					new Field(49, "Startup/ Tech-startup"),
					new Field(50, "Supply Chain / Logistics / Procurement"),
					new Field(51, "Technical/ Mechanical"),
					new Field(52, "Telecommunications"),
					new Field(53, "Training and Development"),
					new Field(54, "Travel/Ticketing/Airline/Shipping")
			);

			fieldRepository.saveAll(fields);
			System.out.println("Fields initialized successfully.");
		};
	}

}
