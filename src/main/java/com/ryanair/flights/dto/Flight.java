package com.ryanair.flights.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ryanair.flights.utils.CommonsUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "number",
    "departureTime",
    "arrivalTime"
})
public class Flight implements Serializable, Comparable<Flight>
{

    @JsonProperty("number")
    private String number;
    @JsonProperty("departureTime")
    private String departureTime;
    @JsonProperty("arrivalTime")
    private String arrivalTime;
    private final static long serialVersionUID = -1561777498894849277L;
	public static final long STOP_MINUMUN = 2;

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    public Flight withNumber(String number) {
        this.number = number;
        return this;
    }

    @JsonProperty("departureTime")
    public String getDepartureTime() {
        return departureTime;
    }

    @JsonProperty("departureTime")
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Flight withDepartureTime(String departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    @JsonProperty("arrivalTime")
    public String getArrivalTime() {
        return arrivalTime;
    }

    @JsonProperty("arrivalTime")
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Flight withArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivalTime == null) ? 0 : arrivalTime.hashCode());
		result = prime * result + ((departureTime == null) ? 0 : departureTime.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (arrivalTime == null) {
			if (other.arrivalTime != null)
				return false;
		} else if (!arrivalTime.equals(other.arrivalTime))
			return false;
		if (departureTime == null) {
			if (other.departureTime != null)
				return false;
		} else if (!departureTime.equals(other.departureTime))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Flight [number=" + number + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + "]";
	}

	@Override
	public int compareTo(Flight o) {
		//Comparator 2h o mas difference 1 
		//Comparator 2h 0
		//Comparator -2h -1
        
		LocalTime meInit = CommonsUtils.stringToLocalTime(this.getDepartureTime());
		LocalTime meEnd = CommonsUtils.stringToLocalTime(this.getArrivalTime());
		
		LocalTime newInit = CommonsUtils.stringToLocalTime(o.getDepartureTime());
		LocalTime newEnd = CommonsUtils.stringToLocalTime(o.getArrivalTime());
		
		return (meEnd.plusHours(STOP_MINUMUN)).compareTo(newInit);
		
	}

    
}
