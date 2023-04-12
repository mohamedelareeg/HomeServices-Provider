package com.rovaindu.serviesdashboard.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rovaindu.serviesdashboard.retrofit.models.ServiesOrder;

import java.util.List;

public class CurrentOrders {
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
    private List<java.lang.Object> errors = null;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<java.lang.Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("status_text")
        @Expose
        private String statusText;
        @SerializedName("orders")
        @Expose
        private List<ServiesOrder> orders = null;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public List<ServiesOrder> getOrders() {
            return orders;
        }

        public void setOrders(List<ServiesOrder> orders) {
            this.orders = orders;
        }

    }

}
