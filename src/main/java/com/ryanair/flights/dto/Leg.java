
package com.ryanair.flights.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "departureAirport",
    "arrivalAirport",
    "departureDateTime",
    "arrivalDateTime"
})
public class Leg implements Serializable
{

    @JsonProperty("departureAirport")
    private String departureAirport;
    @JsonProperty("arrivalAirport")
    private String arrivalAirport;
    @JsonProperty("departureDateTime")
    private String departureDateTime;
    @JsonProperty("arrivalDateTime")
    private String arrivalDateTime;
    private final static long serialVersionUID = 4773369168562958204L;

    @JsonProperty("departureAirport")
    public String getDepartureAirport() {
        return departureAirport;
    }

    @JsonProperty("departureAirport")
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Leg withDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
        return this;
    }

    @JsonProperty("arrivalAirport")
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    @JsonProperty("arrivalAirport")
    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Leg withArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
        return this;
    }

    @JsonProperty("departureDateTime")
    public String getDepartureDateTime() {
        return departureDateTime;
    }

    @JsonProperty("departureDateTime")
    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public Leg withDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
        return this;
    }

    @JsonProperty("arrivalDateTime")
    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    @JsonProperty("arrivalDateTime")
    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public Leg withArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
        return this;
    }

}
