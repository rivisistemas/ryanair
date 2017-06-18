/**
 * 
 */
package com.ryanair.flights.service;

import java.util.List;

import com.ryanair.flights.dto.Interconecction;
import com.ryanair.flights.dto.Route;
import com.ryanair.flights.dto.Scheduler;
import com.ryanair.flights.exception.FlightsException;

/**
 * @author antonio.delrio
 *
 */
public interface FlightsService {
	public List<Route> getRoutes() throws FlightsException;

	public List<Scheduler> getScheduler(String departure, String arraival, String departureDateTime, String arrivalDateTime) throws FlightsException;

	public List<Interconecction> getInterconnections(List<Route> routes, String departure, String arrival,
			String departureDateTime, String arrivalDateTime) throws FlightsException;
}
