package com.omninos.freshup.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingServicesModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public class Details {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("subSubService_id")
        @Expose
        private String subSubServiceId;
        @SerializedName("barber_id")
        @Expose
        private String barberId;
        @SerializedName("apointmentDate")
        @Expose
        private String apointmentDate;
        @SerializedName("timeslot")
        @Expose
        private String timeslot;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSubSubServiceId() {
            return subSubServiceId;
        }

        public void setSubSubServiceId(String subSubServiceId) {
            this.subSubServiceId = subSubServiceId;
        }

        public String getBarberId() {
            return barberId;
        }

        public void setBarberId(String barberId) {
            this.barberId = barberId;
        }

        public String getApointmentDate() {
            return apointmentDate;
        }

        public void setApointmentDate(String apointmentDate) {
            this.apointmentDate = apointmentDate;
        }

        public String getTimeslot() {
            return timeslot;
        }

        public void setTimeslot(String timeslot) {
            this.timeslot = timeslot;
        }

    }
}
