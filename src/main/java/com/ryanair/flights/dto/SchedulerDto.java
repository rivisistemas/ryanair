package com.ryanair.flights.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "month",
    "days"
})
public class SchedulerDto implements Serializable
{

    @JsonProperty("month")
    private Integer month;
    @JsonProperty("days")
    private List<Day> days = null;
    private final static long serialVersionUID = 8245250224175968216L;

    private Integer year;
    
    @JsonProperty("month")
    public Integer getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(Integer month) {
        this.month = month;
    }

    public SchedulerDto withMonth(Integer month) {
        this.month = month;
        return this;
    }

    @JsonProperty("days")
    public List<Day> getDays() {
        return days;
    }

    @JsonProperty("days")
    public void setDays(List<Day> days) {
        this.days = days;
    }

    public SchedulerDto withDays(List<Day> days) {
        this.days = days;
        return this;
    }

    public Day getDayofMonth(Integer month, Integer day){
    	Day dayFound = null;
    	if(this.month.equals(month)){
			for (Day dayRec : days) {
				if(dayRec.getDay().equals(day)){
					dayFound = dayRec;
					break;
				}
			}			
		}
    	return dayFound;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
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
		SchedulerDto other = (SchedulerDto) obj;
		if (days == null) {
			if (other.days != null)
				return false;
		} else if (!days.equals(other.days))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Scheduler [month=" + month + ", days=" + days + "]";
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
