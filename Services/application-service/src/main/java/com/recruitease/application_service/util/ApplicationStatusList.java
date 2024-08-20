package com.recruitease.application_service.util;

//application status list
public class ApplicationStatusList {
    public static final String Under_Review = "underReview";
    public static final String Withdrawn = "withdrawn";
    public static final String Shortlisted = "shortlisted";
    public static final String Interview_Scheduled = "interviewScheduled";
    public static final String Interviewed = "interviewed";
    public static final String Offered = "offered";
    public static final String Hired = "hired";
    public static final String Rejected = "rejected";
    public static final String Archived = "archived";

    // Static method to check if status is not equal to any in the list
    public static boolean isStatusNotEqualToAny(String status) {
        return !status.equals(Under_Review) &&
                !status.equals(Withdrawn) &&
                !status.equals(Shortlisted) &&
                !status.equals(Interview_Scheduled) &&
                !status.equals(Interviewed) &&
                !status.equals(Offered) &&
                !status.equals(Hired) &&
                !status.equals(Rejected) &&
                !status.equals(Archived);
    }
}
