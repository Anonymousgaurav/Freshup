package com.omninos.freshup.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentModel {


    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

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

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public static class Detail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("serviceTransactionsId")
        @Expose
        private String serviceTransactionsId;
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
        @SerializedName("paymentStatus")
        @Expose
        private String paymentStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("userStatus")
        @Expose
        private String userStatus;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("barberName")
        @Expose
        private String barberName;
        @SerializedName("upcomingPastApointment")
        @Expose
        private Integer upcomingPastApointment;
        @SerializedName("paymentMethod")
        @Expose
        private String paymentMethod;
        @SerializedName("subSubService_title")
        @Expose
        private List<SubSubServiceTitle> subSubServiceTitle = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getServiceTransactionsId() {
            return serviceTransactionsId;
        }

        public void setServiceTransactionsId(String serviceTransactionsId) {
            this.serviceTransactionsId = serviceTransactionsId;
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

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBarberName() {
            return barberName;
        }

        public void setBarberName(String barberName) {
            this.barberName = barberName;
        }

        public Integer getUpcomingPastApointment() {
            return upcomingPastApointment;
        }

        public void setUpcomingPastApointment(Integer upcomingPastApointment) {
            this.upcomingPastApointment = upcomingPastApointment;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public List<SubSubServiceTitle> getSubSubServiceTitle() {
            return subSubServiceTitle;
        }

        public void setSubSubServiceTitle(List<SubSubServiceTitle> subSubServiceTitle) {
            this.subSubServiceTitle = subSubServiceTitle;
        }

    }
    public class SubSubServiceTitle {

        @SerializedName("title")
        @Expose
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
