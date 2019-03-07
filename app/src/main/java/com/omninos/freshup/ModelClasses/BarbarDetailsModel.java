package com.omninos.freshup.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarbarDetailsModel {
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

    public static class Details {

        @SerializedName("barberDeatils")
        @Expose
        private List<BarberDeatil> barberDeatils = null;
        @SerializedName("timeSlotDetails")
        @Expose
        private TimeSlotDetails timeSlotDetails;

        public List<BarberDeatil> getBarberDeatils() {
            return barberDeatils;
        }

        public void setBarberDeatils(List<BarberDeatil> barberDeatils) {
            this.barberDeatils = barberDeatils;
        }

        public TimeSlotDetails getTimeSlotDetails() {
            return timeSlotDetails;
        }

        public void setTimeSlotDetails(TimeSlotDetails timeSlotDetails) {
            this.timeSlotDetails = timeSlotDetails;
        }

    }

    public class BookingTime {

        @SerializedName("timeslot")
        @Expose
        private String timeslot;
        @SerializedName("apointmentDate")
        @Expose
        private String apointmentDate;

        public String getTimeslot() {
            return timeslot;
        }

        public void setTimeslot(String timeslot) {
            this.timeslot = timeslot;
        }

        public String getApointmentDate() {
            return apointmentDate;
        }

        public void setApointmentDate(String apointmentDate) {
            this.apointmentDate = apointmentDate;
        }

    }

    public class BarberDeatil {

        @SerializedName("startDate")
        @Expose
        private Object startDate;
        @SerializedName("endDate")
        @Expose
        private Object endDate;
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
        @SerializedName("bookingTime")
        @Expose
        private List<BookingTime> bookingTime = null;

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

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

        public List<BookingTime> getBookingTime() {
            return bookingTime;
        }

        public void setBookingTime(List<BookingTime> bookingTime) {
            this.bookingTime = bookingTime;
        }
    }

    public class TimeSlotDetails {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("slot_time")
        @Expose
        private String slotTime;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSlotTime() {
            return slotTime;
        }

        public void setSlotTime(String slotTime) {
            this.slotTime = slotTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }
}
