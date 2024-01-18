package com.omninos.freshup.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueueModelClass {
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


    public class BookingDetail {

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
        @SerializedName("serviceTitle")
        @Expose
        private String serviceTitle;

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

        public String getServiceTitle() {
            return serviceTitle;
        }

        public void setServiceTitle(String serviceTitle) {
            this.serviceTitle = serviceTitle;
        }

    }


    public static class Detail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("bookingDetails")
        @Expose
        private List<BookingDetail> bookingDetails = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<BookingDetail> getBookingDetails() {
            return bookingDetails;
        }

        public void setBookingDetails(List<BookingDetail> bookingDetails) {
            this.bookingDetails = bookingDetails;
        }

    }
}
