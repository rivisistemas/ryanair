/**
 * 
 */
package com.ryanair.flights.dto;

/**
 * @author antonio.delrio
 *
 */

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"airportFrom",
"airportTo",
"connectingAirport",
"newRoute",
"seasonalRoute"
})
public class Route implements Serializable
{

@JsonProperty("airportFrom")
private String airportFrom;
@JsonProperty("airportTo")
private String airportTo;
@JsonProperty("connectingAirport")
private String connectingAirport;
@JsonProperty("newRoute")
private Boolean newRoute;
@JsonProperty("seasonalRoute")
private Boolean seasonalRoute;

private final static long serialVersionUID = -5633935130890126874L;

@JsonProperty("airportFrom")
public String getAirportFrom() {
return airportFrom;
}

@JsonProperty("airportFrom")
public void setAirportFrom(String airportFrom) {
this.airportFrom = airportFrom;
}

public Route withAirportFrom(String airportFrom) {
this.airportFrom = airportFrom;
return this;
}

@JsonProperty("airportTo")
public String getAirportTo() {
return airportTo;
}

@JsonProperty("airportTo")
public void setAirportTo(String airportTo) {
this.airportTo = airportTo;
}

public Route withAirportTo(String airportTo) {
this.airportTo = airportTo;
return this;
}

@JsonProperty("connectingAirport")
public String getConnectingAirport() {
return connectingAirport;
}

@JsonProperty("connectingAirport")
public void setConnectingAirport(String connectingAirport) {
this.connectingAirport = connectingAirport;
}

public Route withConnectingAirport(String connectingAirport) {
this.connectingAirport = connectingAirport;
return this;
}

@JsonProperty("newRoute")
public Boolean getNewRoute() {
return newRoute;
}

@JsonProperty("newRoute")
public void setNewRoute(Boolean newRoute) {
this.newRoute = newRoute;
}

public Route withNewRoute(Boolean newRoute) {
this.newRoute = newRoute;
return this;
}

@JsonProperty("seasonalRoute")
public Boolean getSeasonalRoute() {
return seasonalRoute;
}

@JsonProperty("seasonalRoute")
public void setSeasonalRoute(Boolean seasonalRoute) {
this.seasonalRoute = seasonalRoute;
}

public Route withSeasonalRoute(Boolean seasonalRoute) {
this.seasonalRoute = seasonalRoute;
return this;
}

}