package com.recruitease.joblisting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;


@Document(value = "job")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Job {

        @Id
        private String id;
        private String title;
        private String description;
        private Date deadline;
        private String status;
        private String recruiterId;
        private String location;
        private String industry;
        private String type;
        private String level;


}
