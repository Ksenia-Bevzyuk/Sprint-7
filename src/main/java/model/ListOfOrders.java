package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListOfOrders {

    private String id;
    private String courierId;

    public ListOfOrders(String id, String courierId) {
        this.id = id;
        this.courierId = courierId;
    }

    public ListOfOrders() {
    }

    public String getId() {
        return id;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }
}