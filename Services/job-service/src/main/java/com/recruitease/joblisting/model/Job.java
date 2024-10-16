package com.recruitease.joblisting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "job")
public class Job {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String jobId;

        @Column(name = "title", nullable = false, length = 100)
        private String title;

        @Column(name = "type", length = 50)
        private JobType type;

        @Column(name = "location", length = 100)
        private Location location;

        @ManyToMany
        @JoinTable(
                name = "job_fields",
                joinColumns = @JoinColumn(name = "job_id"),
                inverseJoinColumns = @JoinColumn(name = "field_key")
        )
        private Set<Field> fields;
//const fieldValues = [
//        {key: 1, label: "Account & Finance"},
//        {key: 2, label: "Administration / Secretarial"},
//        {key: 3, label: "Agriculture"},
//        {key: 4, label: "Apparel"},
//        {key: 5, label: "Architecture"},
//        {key: 6, label: "Automobile"},
//        {key: 7, label: "Banking and Financial Services"},
//        {key: 8, label: "Beauty & Hairdressing"},
//        {key: 9, label: "BPO/ KPO"},
//        {key: 10, label: "Building & Construction"},
//        {key: 11, label: "Business Management"},
//        {key: 12, label: "Call Center"},
//        {key: 13, label: "Charity / NGO"},
//        {key: 14, label: "Customer Service"},
//        {key: 15, label: "Delivery / Driving / Transport"},
//        {key: 16, label: "Digital Marketing"},
//        {key: 17, label: "Education / Higher Education"},
//        {key: 18, label: "Electronics / Electrical"},
//        {key: 19, label: "Engineering / Manufacturing"},
//        {key: 20, label: "Environment/ Health & Safety"},
//        {key: 21, label: "FMCG/ Food Industry"},
//        {key: 23, label: "Government/ Public Sector"},
//        {key: 24, label: "Hospital/ Nursing/ Healthcare"},
//        {key: 25, label: "Hotel/ Hospitality/ Leisure"},
//        {key: 26, label: "Human Resources / Recruitment"},
//        {key: 27, label: "Insurance"},
//        {key: 28, label: "Interior Design"},
//        {key: 29, label: "Internship / Undergraduate"},
//        {key: 30, label: "IT-HWare/ Networks/ Systems"},
//        {key: 31, label: "IT-SWare / Internet"},
//        {key: 32, label: "Legal / Law"},
//        {key: 33, label: "Media/ Advertising/ Communication/ Design"},
//        {key: 34, label: "Oil, Gas and Nuclear"},
//        {key: 35, label: "Other"},
//        {key: 36, label: "Pharmaceutical"},
//        {key: 37, label: "Production & Operations"},
//        {key: 38, label: "Project Management / Programme Management"},
//        {key: 39, label: "Quality Assurance"},
//        {key: 40, label: "Real Estate"},
//        {key: 41, label: "Recoveries"},
//        {key: 42, label: "Retail / Fashion"},
//        {key: 43, label: "Sales / Marketing / New Business Development"},
//        {key: 44, label: "School Leavers"},
//        {key: 45, label: "Science / Research"},
//        {key: 46, label: "Security/ Military"},
//        {key: 47, label: "Senior Management / Directors"},
//        {key: 48, label: "Sports/Fitness/Recreation"},
//        {key: 49, label: "Startup/ Tech-startup"},
//        {key: 50, label: "Supply Chain / Logistics / Procurement"},
//        {key: 51, label: "Technical/ Mechanical"},
//        {key: 52, label: "Telecommunications"},
//        {key: 53, label: "Training and Development"},
//        {key: 54, label: "Travel/Ticketing/Airline/Shipping"},
//                ];


        @Column(name = "experience_level", length = 50)
        private Integer experienceLevel;
//        {key: 1, label: "Entry Level"},
//        {key: 2, label: "6 Months"},
//        {key: 3, label: "1-2 Years"},
//        {key: 4, label: "2-3 Years"},
//        {key: 5, label: "3-5 Years"},
//        {key: 6, label: "5+ Years"},

        @Column(name = "education_level", length = 50)
        private Integer educationLevel;
//        {key: 1, label: "Ordinary Level"},
//        {key: 2, label: "Advanced Level"},
//        {key: 3, label: "Bachelor’s Degree"},
//        {key: 4, label: "Masters Degree"},
//        {key: 5, label: "PhD"},
//        {key: 6, label: "Diploma/HND"},
//        {key: 7, label: "Certificate"},

        @Column(name = "description", columnDefinition = "TEXT")
        private String description;

        @Column(name = "overview", columnDefinition = "TEXT")
        private String overview;

        @Column(name = "deadline")
        private LocalDate deadline;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", length = 20)
        private JobStatus status;

        @Column(name = "recruiter_id", nullable = false)
        private String recruiterId;

        @Column(name = "image_url", length = 100)
        private String imageUrl;


        public enum JobStatus {
                FILLED,
                LIVE,
                UNPUBLISHED,
                ARCHIVED
        }

        public enum JobType {
                full_time,
                part_time,
                contract,
                other
        }

        public enum Location {
                work_from_home,
                island_wide,
                ampara,
                anuradhapura,
                badulla,
                batticaloa,
                colombo,
                galle,
                gampaha,
                hambantota,
                jaffna,
                kalutara,
                kandy,
                kegalle,
                kilinochchi,
                kurunegala,
                mannar,
                matale,
                matara,
                monaragala,
                mullativu,
                nuwara_eliya,
                polonnaruwa,
                puttalam,
                ratnapura,
                trincomalee,
                vavuniya,
        }

        public void updateObject(Job srcObj){

                Optional.ofNullable(srcObj.getDeadline()).ifPresent(this::setDeadline);
                Optional.ofNullable(srcObj.getTitle()).ifPresent(this::setTitle);
                Optional.ofNullable(srcObj.getType()).ifPresent(this::setType);
                Optional.ofNullable(srcObj.getLocation()).ifPresent(this::setLocation);
                Optional.ofNullable(srcObj.getFields()).ifPresent(this::setFields);
                Optional.ofNullable(srcObj.getExperienceLevel()).ifPresent(this::setExperienceLevel);
                Optional.ofNullable(srcObj.getEducationLevel()).ifPresent(this::setEducationLevel);
                Optional.ofNullable(srcObj.getDescription()).ifPresent(this::setDescription);
                Optional.ofNullable(srcObj.getOverview()).ifPresent(this::setOverview);
                Optional.ofNullable(srcObj.getStatus()).ifPresent(this::setStatus);
                Optional.ofNullable(srcObj.getRecruiterId()).ifPresent(this::setRecruiterId);
                Optional.ofNullable(srcObj.getImageUrl()).ifPresent(this::setImageUrl);

        }
}
