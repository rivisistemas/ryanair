/**
 * 
 */
package com.ryanair.flights.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.dto.Day;
import com.ryanair.flights.dto.Flight;
import com.ryanair.flights.dto.Interconecction;
import com.ryanair.flights.dto.Leg;
import com.ryanair.flights.dto.Route;
import com.ryanair.flights.dto.Scheduler;
import com.ryanair.flights.dto.SchedulerDto;
import com.ryanair.flights.exception.FlightsException;
import com.ryanair.flights.exception.FlightsException.FLIGHTS_CODE;
import com.ryanair.flights.exception.FlightsException.FLIGHTS_ERROR_MSG;
import com.ryanair.flights.service.FlightsService;
import com.ryanair.flights.utils.CommonsUtils;

/**
 * @author antonio.delrio
 *
 */
@Service
public class FlightsServiceImpl implements FlightsService{

	
	private static Logger LOG = LoggerFactory.getLogger(FlightsService.class);
	
	@Value("${ryanair.url.ws.routes}")
	private String urlRoutes;
	
	@Value("${ryanair.url.ws.scheduler}")
	private String urlScheduler;

	/***
	 * Servicio que devuelve las rutas disponibles
	 */
	@Override
	public List<Route> getRoutes() throws FlightsException {
		LOG.info("Enter into service to get routes");
		List<Route> routes = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			
			ResponseEntity<List<Route>> routeResponse =
			        restTemplate.exchange(urlRoutes, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Route>>() {});
			routes = routeResponse.getBody();
			
			LOG.info("Leave service to get routes with result");
			
		} catch (HttpClientErrorException e) {
			LOG.error("Leave error in rest call service to get routes with result");
			if(e.getRawStatusCode()!= FLIGHTS_CODE.OK.getErrorCode())
				return null;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new FlightsException(FLIGHTS_CODE.ERROR, e.getMessage());
		}
		
		return routes;
	}
	
	/**
	 * Servicio que devuelve el scheduler para las rutas en un inicio y fin para sendos aeropuertos
	 */
	@Override
	public List<Scheduler> getScheduler(String departure, String arrival, String departureDateTime, String arrivalDateTime) throws FlightsException {
		LOG.info("Enter into service to get scheduler");
		List<Scheduler> schedulerResult = null;
		try {
			LocalDateTime dateTimeDeparture = CommonsUtils.stringToLocalDateTime(departureDateTime);
			LocalDateTime dateTimeArraival = CommonsUtils.stringToLocalDateTime(arrivalDateTime);
			
			//Compruebo que el intervalo es correcto
			if(dateTimeDeparture.compareTo(LocalDateTime.now())<1 || dateTimeDeparture.compareTo(dateTimeArraival)>0){
				throw new FlightsException(FLIGHTS_CODE.ERROR, FLIGHTS_ERROR_MSG.DATES_INTERVAL_EXECUTION_ERROR.name());
			}
			LOG.info("Get interval date");
			List<String> yearsResult = CommonsUtils.getYearsInInterval(dateTimeDeparture, dateTimeArraival);
			List<String> monthsResult = CommonsUtils.getMonthsInInterval(dateTimeDeparture, dateTimeArraival);
			
			schedulerResult = new ArrayList<Scheduler>();
			LOG.debug("get WS scheduler");
				for (String year : yearsResult) {
					for (String month : monthsResult) {
						//Llamada al WS
						RestTemplate restTemplate = new RestTemplate();
						HttpHeaders headers = new HttpHeaders();
						headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
						HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
						String urlFinal = urlScheduler + departure+"/"+ arrival +"/years/"+ year + "/months/" + month;
						
						ResponseEntity<SchedulerDto> schedulerResponse =
						        restTemplate.exchange(urlFinal, HttpMethod.GET, entity, SchedulerDto.class);
						SchedulerDto scheduler = schedulerResponse.getBody();
						scheduler.setYear(Integer.valueOf(year));
						
						//Calculo las rutas que entren en el intervalo de fechas el resto las descarto
						LOG.debug("Calculate conections month = "+ month +" year = " + year);
						if(schedulerResponse.getStatusCodeValue() == FLIGHTS_CODE.OK.getErrorCode()){
							for (Day dayScheduler : scheduler.getDays()) {		
								/*if(//!scheduler.getMonth().equals(Integer.valueOf(month))||
									(scheduler.getMonth().equals(Integer.valueOf(month)) && dayScheduler.getDay().compareTo(dateTimeDeparture.getDayOfMonth())>=0))*/
									for (Flight flight : dayScheduler.getFlights()) {
										LocalDateTime flightDeparture = CommonsUtils.isoLocalDateTime(Integer.valueOf(year), scheduler.getMonth(), dayScheduler.getDay(), flight.getDepartureTime());
										
										LocalDateTime date1 = CommonsUtils.stringToLocalDateTime(departureDateTime);
										
										LocalDateTime flightArrival = CommonsUtils.isoLocalDateTime(Integer.valueOf(year), scheduler.getMonth(), dayScheduler.getDay(), flight.getArrivalTime());
										
										LocalDateTime date3 = CommonsUtils.stringToLocalDateTime(arrivalDateTime);
										
										if(flightDeparture.isAfter(date1) && flightArrival.isBefore(date3)){
											Scheduler schedulerRes = new Scheduler();
											schedulerRes.setDeparture(departure);
											schedulerRes.setArrival(arrival);
											schedulerRes.setDepartureTime(flightDeparture.toString());
											schedulerRes.setArrivalTime(flightArrival.toString());
											
											schedulerResult.add(schedulerRes);
										}
									}
							}
							
												
						}
					}
				}		
				LOG.info("Leave scheduler service");
		} catch (HttpClientErrorException e) {
			LOG.info("Scheduler not valid for moths and year");
			if(e.getRawStatusCode()!= FLIGHTS_CODE.OK.getErrorCode())
				return null;
		} catch (FlightsException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Scheduler service error" + e.getMessage());
			throw new FlightsException(FLIGHTS_CODE.ERROR, e.getMessage());
		}
		
		return schedulerResult;
	}
	
	/***
	 * Servicio que construye las interconexiones
	 */
	@Override
	public List<Interconecction> getInterconnections(List<Route> routes, String departure, String arrival, String departureDateTime, String arrivalDateTime) throws FlightsException {
		LOG.info("Enter into service to calculate conecttions");
		List<Route> routesFoundInit = new ArrayList<Route>();
		List<Route> routesFoundEnd = new ArrayList<Route>();

		List<Interconecction> routesDefinitive = new ArrayList<Interconecction>();

		List<Scheduler> schedulersInit = null;
		List<Scheduler> schedulersEnd = null;
		LOG.info("Calculate direct routes an routes with conection ");
		for (Route route : routes) {
			if(route.getAirportFrom().equals(departure) && route.getAirportTo().equals(arrival)){
				schedulersInit = this.getScheduler(route.getAirportFrom(), route.getAirportTo(), departureDateTime, arrivalDateTime);
				for (Scheduler schedulerInit  : schedulersInit ) {
					Interconecction interconnection = new Interconecction();
					mapLeg(interconnection, schedulerInit);
					interconnection.setStops(interconnection.getLegs().size()-1);
					routesDefinitive.add(interconnection);
				}

			}else if(route.getAirportFrom().equals(departure)){
				routesFoundInit.add(route);
			}else if(route.getAirportTo().equals(arrival)){
				routesFoundEnd.add(route);
			}
		}
		
		LOG.info("Intersect routes an routes with conection ");
		for (Route routeInit : routesFoundInit) {
			for (Route routeEnd : routesFoundEnd) {
				if(routeEnd.getAirportFrom().equals(routeInit.getAirportTo())){

					schedulersInit = this.getScheduler(routeInit.getAirportFrom(), routeInit.getAirportTo(), departureDateTime, arrivalDateTime);
					schedulersEnd = this.getScheduler(routeEnd.getAirportFrom(), routeEnd.getAirportTo(), departureDateTime, arrivalDateTime);

					//Obtengo los horarios de las rutas
					//Si son del mismo mes saco los dias y comparo dias
					//Comparo que la fecha de fin de la ruta inicial sea 2 horas mas 

					if(null!=schedulersInit && null!=schedulersEnd){
						for (Scheduler schedulerInit  : schedulersInit ) {
							for (Scheduler schedulerEnd : schedulersEnd) {
								Interconecction interconnection = new Interconecction();

								LocalDateTime flightDepartureEnd = CommonsUtils.stringToLocalDateTime(schedulerEnd.getDepartureTime());
								LocalDateTime flightArrivalInit = CommonsUtils.stringToLocalDateTime(schedulerInit.getArrivalTime());


								if(flightArrivalInit.plusHours(Flight.STOP_MINUMUN).isBefore(flightDepartureEnd)){
									mapLeg(interconnection, schedulerInit);
									mapLeg(interconnection, schedulerEnd);
									interconnection.setStops(interconnection.getLegs().size()-1);
									routesDefinitive.add(interconnection);
								}
							}
						}
					}
				}
			}
		}
		
		LOG.info("Leave service conections ");
		return routesDefinitive;
	}
	
	
	/**
	 * Metodo privado para mapeo de conexion			
	 * @param interconecction
	 * @param scheduler
	 */
	private void mapLeg(Interconecction interconecction, Scheduler scheduler) {
					
					if(null==interconecction.getLegs()){
						interconecction.setLegs(new ArrayList<Leg>());
					}
					
					List<Leg> legs = interconecction.getLegs();
					
					Leg step = new Leg();
					step.setDepartureAirport(scheduler.getDeparture());
					step.setDepartureDateTime(scheduler.getDepartureTime());
					step.setArrivalAirport(scheduler.getArrival());
					step.setArrivalDateTime(scheduler.getArrivalTime());

					legs.add(step);

					return;
				}
}
