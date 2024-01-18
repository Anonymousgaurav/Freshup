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

    }public class TimeSlotDetails {

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
        @SerializedName("working_days")
        @Expose
        private String workingDays;
        @SerializedName("non_working_days")
        @Expose
        private String nonWorkingDays;
        @SerializedName("regularFirstShiftStartTime")
        @Expose
        private String regularFirstShiftStartTime;
        @SerializedName("regularFirstShiftEndTime")
        @Expose
        private String regularFirstShiftEndTime;
        @SerializedName("regularSecondShiftStartTime")
        @Expose
        private String regularSecondShiftStartTime;
        @SerializedName("regularSecondShiftEndTime")
        @Expose
        private String regularSecondShiftEndTime;
        @SerializedName("weekendFirstShiftStartTime")
        @Expose
        private String weekendFirstShiftStartTime;
        @SerializedName("weekendFirstShiftEndTime")
        @Expose
        private String weekendFirstShiftEndTime;
        @SerializedName("weekendSecondShiftStartTime")
        @Expose
        private String weekendSecondShiftStartTime;
        @SerializedName("weekendSecondShiftEndTime")
        @Expose
        private String weekendSecondShiftEndTime;
        @SerializedName("holiday_startDate")
        @Expose
        private String holidayStartDate;
        @SerializedName("holiday_endDate")
        @Expose
        private String holidayEndDate;
        @SerializedName("regular_times")
        @Expose
        private String regularTimes;
        @SerializedName("weekend_times")
        @Expose
        private String weekendTimes;
        @SerializedName("firstShiftStartTime")
        @Expose
        private String firstShiftStartTime;
        @SerializedName("firstShiftEndTime")
        @Expose
        private String firstShiftEndTime;
        @SerializedName("secondShifStartTime")
        @Expose
        private String secondShifStartTime;
        @SerializedName("secondShifEndTime")
        @Expose
        private String secondShifEndTime;

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

        public String getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(String workingDays) {
            this.workingDays = workingDays;
        }

        public String getNonWorkingDays() {
            return nonWorkingDays;
        }

        public void setNonWorkingDays(String nonWorkingDays) {
            this.nonWorkingDays = nonWorkingDays;
        }

        public String getRegularFirstShiftStartTime() {
            return regularFirstShiftStartTime;
        }

        public void setRegularFirstShiftStartTime(String regularFirstShiftStartTime) {
            this.regularFirstShiftStartTime = regularFirstShiftStartTime;
        }

        public String getRegularFirstShiftEndTime() {
            return regularFirstShiftEndTime;
        }

        public void setRegularFirstShiftEndTime(String regularFirstShiftEndTime) {
            this.regularFirstShiftEndTime = regularFirstShiftEndTime;
        }

        public String getRegularSecondShiftStartTime() {
            return regularSecondShiftStartTime;
        }

        public void setRegularSecondShiftStartTime(String regularSecondShiftStartTime) {
            this.regularSecondShiftStartTime = regularSecondShiftStartTime;
        }

        public String getRegularSecondShiftEndTime() {
            return regularSecondShiftEndTime;
        }

        public void setRegularSecondShiftEndTime(String regularSecondShiftEndTime) {
            this.regularSecondShiftEndTime = regularSecondShiftEndTime;
        }

        public String getWeekendFirstShiftStartTime() {
            return weekendFirstShiftStartTime;
        }

        public void setWeekendFirstShiftStartTime(String weekendFirstShiftStartTime) {
            this.weekendFirstShiftStartTime = weekendFirstShiftStartTime;
        }

        public String getWeekendFirstShiftEndTime() {
            return weekendFirstShiftEndTime;
        }

        public void setWeekendFirstShiftEndTime(String weekendFirstShiftEndTime) {
            this.weekendFirstShiftEndTime = weekendFirstShiftEndTime;
        }

        public String getWeekendSecondShiftStartTime() {
            return weekendSecondShiftStartTime;
        }

        public void setWeekendSecondShiftStartTime(String weekendSecondShiftStartTime) {
            this.weekendSecondShiftStartTime = weekendSecondShiftStartTime;
        }

        public String getWeekendSecondShiftEndTime() {
            return weekendSecondShiftEndTime;
        }

        public void setWeekendSecondShiftEndTime(String weekendSecondShiftEndTime) {
            this.weekendSecondShiftEndTime = weekendSecondShiftEndTime;
        }

        public String getHolidayStartDate() {
            return holidayStartDate;
        }

        public void setHolidayStartDate(String holidayStartDate) {
            this.holidayStartDate = holidayStartDate;
        }

        public String getHolidayEndDate() {
            return holidayEndDate;
        }

        public void setHolidayEndDate(String holidayEndDate) {
            this.holidayEndDate = holidayEndDate;
        }

        public String getRegularTimes() {
            return regularTimes;
        }

        public void setRegularTimes(String regularTimes) {
            this.regularTimes = regularTimes;
        }

        public String getWeekendTimes() {
            return weekendTimes;
        }

        public void setWeekendTimes(String weekendTimes) {
            this.weekendTimes = weekendTimes;
        }

        public String getFirstShiftStartTime() {
            return firstShiftStartTime;
        }

        public void setFirstShiftStartTime(String firstShiftStartTime) {
            this.firstShiftStartTime = firstShiftStartTime;
        }

        public String getFirstShiftEndTime() {
            return firstShiftEndTime;
        }

        public void setFirstShiftEndTime(String firstShiftEndTime) {
            this.firstShiftEndTime = firstShiftEndTime;
        }

        public String getSecondShifStartTime() {
            return secondShifStartTime;
        }

        public void setSecondShifStartTime(String secondShifStartTime) {
            this.secondShifStartTime = secondShifStartTime;
        }

        public String getSecondShifEndTime() {
            return secondShifEndTime;
        }

        public void setSecondShifEndTime(String secondShifEndTime) {
            this.secondShifEndTime = secondShifEndTime;
        }

    }
}
