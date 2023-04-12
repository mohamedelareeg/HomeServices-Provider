package com.rovaindu.serviesdashboard.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.rovaindu.serviesdashboard.retrofit.models.ServiesAgent;
import com.rovaindu.serviesdashboard.retrofit.models.ServiesUser;

import java.util.List;

public class UserResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cart_items_count")
    @Expose
    private Integer cartItemsCount;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("data")
    @Expose
    private ServiesAgent data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCartItemsCount() {
        return cartItemsCount;
    }

    public void setCartItemsCount(Integer cartItemsCount) {
        this.cartItemsCount = cartItemsCount;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public ServiesAgent getData() {
        return data;
    }

    public void setData(ServiesAgent data) {
        this.data = data;
    }


}
