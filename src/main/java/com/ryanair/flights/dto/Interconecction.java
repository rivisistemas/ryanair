
package com.ryanair.flights.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "stops",
    "legs"
})
public class Interconecction implements Serializable
{

    @JsonProperty("stops")
    private Integer stops;
    @JsonProperty("legs")
    private List<Leg> legs = null;
    private final static long serialVersionUID = 891345373318441837L;

    @JsonProperty("stops")
    public Integer getStops() {
        return stops;
    }

    @JsonProperty("stops")
    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public Interconecction withStops(Integer stops) {
        this.stops = stops;
        return this;
    }

    @JsonProperty("legs")
    public List<Leg> getLegs() {
        return legs;
    }

    @JsonProperty("legs")
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public Interconecction withLegs(List<Leg> legs) {
        this.legs = legs;
        return this;
    }

}
