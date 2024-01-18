package com.omninos.freshup.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.omninos.freshup.ModelClasses.AppointmentModel;
import com.omninos.freshup.ModelClasses.GetHomeDataModel;
import com.omninos.freshup.ModelClasses.QueueModelClass;
import com.omninos.freshup.ModelClasses.SingleProductCategoryModel;

import java.util.List;

public class AppPreferences {

    String UserName;
    String UserEmail;
    String UserPass;
    String UserMobile;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }


    public  void saveLanguage(Activity activity,String id){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Lng",id);
        editor.commit();
    }

    public  String getLanguage(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        return sharedPreferences.getString("Lng","");
    }

    public  void saveUserId(Activity activity,String id){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("ID",id);
        editor.commit();
    }

    public  String getUserId(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        return sharedPreferences.getString("ID","");
    }

    public  String getUserId1(Context activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        return sharedPreferences.getString("ID","");
    }

    public  void saveUserIdTemp(Activity activity,String id){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile1",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("IDTemp",id);
        editor.commit();
    }

    public  String getUserIdTemp(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile1",0);
        return sharedPreferences.getString("IDTemp","");
    }

    public void TempClear(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile1",0);
        sharedPreferences.edit().clear().commit();
    }

    public  void saveToken(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Token","1");
        editor.commit();
    }

    public  String getToken(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        return sharedPreferences.getString("Token","");
    }

    public void Logout(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        sharedPreferences.edit().clear().commit();
    }

    public  void saveUserImagePath(Activity activity,String path){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Path",path);
        editor.commit();
    }

    public void SaveSocailType(Activity activity,String type){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Social",type);
        editor.commit();

    }
    public String GetSocialType(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        return sharedPreferences.getString("Social","");
    }

    public  String getUserImagePath(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("FreshUpFile",0);
        return sharedPreferences.getString("Path","");
    }
    private List<GetHomeDataModel.Detail> details = null;

    public List<GetHomeDataModel.Detail> getDetails() {
        return details;
    }

    public void setDetails(List<GetHomeDataModel.Detail> details) {
        this.details = details;
    }

    int ArrowMovement=0;

    public int getArrowMovement() {
        return ArrowMovement;
    }

    public void setArrowMovement(int arrowMovement) {
        ArrowMovement = arrowMovement;
    }

    String categoryId;

    String setcart;

    public String getSetcart() {
        return setcart;
    }

    public void setSetcart(String setcart) {
        this.setcart = setcart;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    private List<SingleProductCategoryModel.Product> product = null;

    public List<SingleProductCategoryModel.Product> getProduct() {
        return product;
    }

    public void setProduct(List<SingleProductCategoryModel.Product> product) {
        this.product = product;
    }

    String saveServiceName;

    public String getSaveServiceName() {
        return saveServiceName;
    }

    public void setSaveServiceName(String saveServiceName) {
        this.saveServiceName = saveServiceName;
    }

    List<String> itemsdataSubServices=null;

    List<String> servicesName=null;

    List<Double> priceList=null;

    public List<Double> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Double> priceList) {
        this.priceList = priceList;
    }

    public List<String> getServicesName() {
        return servicesName;
    }

    public void setServicesName(List<String> servicesName) {
        this.servicesName = servicesName;
    }



    public List<String> getItemsdataSubServices() {
        return itemsdataSubServices;
    }

    public void setItemsdataSubServices(List<String> itemsdataSubServices) {
        this.itemsdataSubServices = itemsdataSubServices;
    }



    String dateData;

    public String getDateData() {
        return dateData;
    }

    public void setDateData(String dateData) {
        this.dateData = dateData;
    }

    List<String> TimeSlote=null;

    public List<String> getTimeSlote() {
        return TimeSlote;
    }

    public void setTimeSlote(List<String> timeSlote) {
        TimeSlote = timeSlote;
    }

    String barbarName=null;
    String barbarId;

    public String getBarbarId() {
        return barbarId;
    }

    public void setBarbarId(String barbarId) {
        this.barbarId = barbarId;
    }

    public String getBarbarName() {
        return barbarName;
    }

    public void setBarbarName(String barbarName) {
        this.barbarName = barbarName;
    }

    int subCategorySize;
    int timesloteSize;

    public int getSubCategorySize() {
        return subCategorySize;
    }

    public void setSubCategorySize(int subCategorySize) {
        this.subCategorySize = subCategorySize;
    }

    public int getTimesloteSize() {
        return timesloteSize;
    }

    public void setTimesloteSize(int timesloteSize) {
        this.timesloteSize = timesloteSize;
    }

    String OTP;

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }


    String InstaUserName;
    String InstaUserPicture;
    String InstaId;

    public String getInstaId() {
        return InstaId;
    }

    public void setInstaId(String instaId) {
        InstaId = instaId;
    }

    public String getInstaUserName() {
        return InstaUserName;
    }

    public void setInstaUserName(String instaUserName) {
        InstaUserName = instaUserName;
    }

    public String getInstaUserPicture() {
        return InstaUserPicture;
    }

    public void setInstaUserPicture(String instaUserPicture) {
        InstaUserPicture = instaUserPicture;
    }

    List<AppointmentModel> appointmentModels=null;

    public List<AppointmentModel> getAppointmentModels() {
        return appointmentModels;
    }

    public void setAppointmentModels(List<AppointmentModel> appointmentModels) {
        this.appointmentModels = appointmentModels;
    }

    String ProductId,ProductAmount;

    public String getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(String productAmount) {
        ProductAmount = productAmount;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public List<String> multipleImages=null;

    public List<String> getMultipleImages() {
        return multipleImages;
    }

    public void setMultipleImages(List<String> multipleImages) {
        this.multipleImages = multipleImages;
    }

    public String selecteAppointmentDate;

    public String getSelecteAppointmentDate() {
        return selecteAppointmentDate;
    }

    public void setSelecteAppointmentDate(String selecteAppointmentDate) {
        this.selecteAppointmentDate = selecteAppointmentDate;
    }

    public List<QueueModelClass.BookingDetail> bookingDetailList=null;

    public List<QueueModelClass.BookingDetail> getBookingDetailList() {
        return bookingDetailList;
    }

    public void setBookingDetailList(List<QueueModelClass.BookingDetail> bookingDetailList) {
        this.bookingDetailList = bookingDetailList;
    }
}
