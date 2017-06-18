/**
 * 
 */
package com.ryanair.flights.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ryanair.flights.dto.Interconecction;
import com.ryanair.flights.dto.Route;
import com.ryanair.flights.exception.FlightsException;
import com.ryanair.flights.exception.FlightsException.FLIGHTS_CODE;
import com.ryanair.flights.service.FlightsService;
import com.ryanair.flights.utils.CommonsUtils;

/**
 * @author antonio.delrio
 *
 */
@RestController
public class FlightsController {
	    
	private static Logger LOG = LoggerFactory.getLogger(FlightsController.class);
	
	@Autowired
	FlightsService flightService;
	
	/***
	 * Rest Controler atiende llamada de conexiones
	 * @param departure
	 * @param arrival
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return
	 */
	@RequestMapping(value = "/interconnections" ,params = {"departure", "arrival", "departureDateTime", "arrivalDateTime"}, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public String getInterconecctions(@RequestParam(value = "departure") String departure,
	    		@RequestParam(value = "arrival") String arrival,
	    		@RequestParam(value = "departureDateTime") String departureDateTime,
	    		@RequestParam(value = "arrivalDateTime") String arrivalDateTime) {
	        
	    	try {
	    		LocalDateTime departTime = CommonsUtils.stringToLocalDateTime(departureDateTime);
	    		LocalDateTime arrivalTime = CommonsUtils.stringToLocalDateTime(arrivalDateTime);
	    		
	    		//Compruebo que las fechas sean correctas
	    		LOG.info("check date interval");
	    		if(departTime.isAfter(arrivalTime) || departTime.isBefore(LocalDateTime.now()) || arrivalTime.isBefore(LocalDateTime.now())){
	    			throw new FlightsException(FLIGHTS_CODE.ERROR,"Date time interval is incorrect");
	    		}
	    		
	    		//Extraigo las rutas desde ws
	    		LOG.info("Extraigo rutas");
	    		List<Route> routes = flightService.getRoutes();
	    		
	    		//Llamo al servicio para calculo de interconexxones
	    		LOG.info("Extraigo conexiones");
	    		List<Interconecction> interconecctions = null;
	    		if(null!=routes && !routes.isEmpty()){
	    			interconecctions = flightService.getInterconnections(routes, departure, arrival, departureDateTime, arrivalDateTime);
	    		}else{
	    			LOG.info("No hay rutas disponibles");
	    			throw new FlightsException(FLIGHTS_CODE.ERROR,"No routes are available");
	    		}
	    		//devuelvo conexones
	    		LOG.info("Devuelvo conexiones");
	    		return new Gson().toJson(interconecctions);
										
	    						
				
			} catch (FlightsException e) {
				LOG.error(e.getMessage());
				return new Gson().toJson(e);
			}
	    	
	    	
	    }
	
}
