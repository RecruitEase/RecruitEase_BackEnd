package com.recruitease.joblisting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "job")
public class Job {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "title", nullable = false, length = 100)
        private String title;

        @Column(name = "type", length = 50)
        private JobType type;

        @Column(name = "locations", length = 100)
        private Location location;

        @Column(name = "field", length = 50)
        private String field;

        @Column(name = "experience_level", length = 50)
        private ExperienceLevel experienceLevel;

        @Column(name = "educational_level", length = 50)
        private EducationLevel educationalLevel;

        @Column(name = "description", columnDefinition = "TEXT")
        private String description;

        @Column(name = "overview", columnDefinition = "TEXT")
        private String overview;

        @Column(name = "deadline")
        private LocalDate deadline;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", length = 20)
        private JobStatus status;

        @Column(name = "recruiter_id", nullable = false, length = 50)
        private String recruiterId;

        @Column(name = "image_url", length = 100)
        private String imageUrl;



        @Column(name = "industry", length = 50)
        private String industry;




        public enum JobStatus {
                OPEN,
                CLOSED,
                IN_PROGRESS,
                CANCELLED
        }

        public enum JobType {
                FULL_TIME,
                PART_TIME,
                CONTRACT,
                OTHER
        }

        public enum Location {
                Work_From_Home,
                Islandwide,
                Ampara,
                Anuradhapura,
                Badulla,
                Batticaloa,
                Colombo,
                Galle,
                Gampaha,
                Hambantota,
                Jaffna,
                Kalutara,
                Kandy,
                Kegalle,
                Kilinochchi,
                Kurunegala,
                Mannar,
                Matale,
                Matara,
                Monaragala,
                Mullaitivu,
                Nuwara_Eliya,
                Polonnaruwa,
                Puttalam,
                Ratnapura,
                Trincomalee,
                Vavuniya

        }

        public enum ExperienceLevel {
                ENTRY_LEVEL,
               _6_Months,
                _1_2_Years,
                _3_5_Years,
                _6_10_Years,
                _10__Years
        }

        public enum EducationLevel {
             ORDINARY_LEVEL,
                ADVANCED_LEVEL,
                CERTIFICATE,
                DIPLOMA,
                HND,
                DEGREE,
                MASTERS,
                PHD,
        }

}
