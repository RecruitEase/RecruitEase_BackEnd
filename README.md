# RecruitEase_BackEnd

**Job Recruitment & Applicant Tracking System**

- **Technologies:** Microservices Architecture, Spring Boot, Next.js, PostgreSQL, Tailwind CSS  
- Comprehensive recruitment platform designed to streamline the hiring process for employers and enhance the job search experience for candidates.
- Features include **CV management and CV creation**, **application management**, and **vacancy recommendations**.
- Full **Applicant Tracking System (ATS)** with application recommendations, vacancy management, shortlisting, interview scheduling, and interview streaming.
- **Real-time chat functionality** and **secure payment handling**.
- Fully distributed **microservices-based system** including API Gateway, Service Discovery, and supporting backend services.


group-id: com.recruitease
description: Authentication Service for RecruitEase
Application-service:
- port 8200

Auth-service:
- port 9898

API-gateway:
- port 8222

Job-service:
- port 8100

Config-Server :
- Centralized configurations for the services
- port 8888

Eureka Discovery Server:
- port 8761
