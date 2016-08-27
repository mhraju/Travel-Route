package com.bus.bdtravelroute.mhraju.travelroute;

import android.os.Bundle;

/**
 * Created by supto on 7/16/2016.
 */

//TODO sobgula getter setter use korte hobe

public class Route {
    private int id;
    private String vehicleType;
    private String startingPoint;
    private String endingPoint;
    private String ticketPrice;
    private String via;
    private String comment;
    private String userName;

    public Route() {
    }

    public Route(String vehicleType, String startingPoint, String endingPoint, String ticketPrice, String via, String comment, String userName) {
        setVehicleType(vehicleType);
        setStartingPoint(startingPoint);
        setEndingPoint(endingPoint);
        setTicketPrice(ticketPrice);
        setVia(via);
        setComment(comment);
        setUserName(userName);

//        this.vehicleType = vehicleType;
//        this.startingPoint = startingPoint;
//        this.endingPoint = endingPoint;
//        this.ticketPrice = ticketPrice;
//        this.via = via;
//        this.comment = comment;
//        this.userName = userName;
    }


    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
