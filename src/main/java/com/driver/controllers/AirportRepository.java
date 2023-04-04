package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.*;

@Repository
public class AirportRepository {


    public HashMap<String, Airport> airportMap = new HashMap<>();

    public HashMap<Integer, Flight> flightMap = new HashMap<>();

    public HashMap<Integer, List<Integer>> flightToPassengerMap = new HashMap<>();

    public HashMap<Integer, Passenger> passengerMap = new HashMap<>();

    public String addAirport( Airport airport){

        //Simply add airport details to your database
        //Return a String message "SUCCESS"
        airportMap.put(airport.getAirportName(),airport);

        return "SUCCESS";
    }

    public String getLargestAirportName(){

        //Largest airport is in terms of terminals 3 terminal airport is larger than 2 terminal airport
        //Incase of a tie return the Lexicographically smallest airportName

        String answer = "";
        int terminals = 0;
        for(Airport airport : airportMap.values()){

            if(airport.getNoOfTerminals()>terminals){
                answer = airport.getAirportName();
                terminals = airport.getNoOfTerminals();
            }else if(airport.getNoOfTerminals()==terminals){
                if(airport.getAirportName().compareTo(answer)<0){
                    answer = airport.getAirportName();
                }
            }
        }
        return answer;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){

        //Find the duration by finding the shortest flight that connects these 2 cities directly
        //If there is no direct flight between 2 cities return -1.

        double distance = 1000000000;

        for(Flight flight:flightMap.values()){
            if((flight.getFromCity().equals(fromCity))&&(flight.getToCity().equals(toCity))){
                distance = Math.min(distance,flight.getDuration());
            }
        }

        if(distance==1000000000){
            return -1;
        }
        return distance;

    }


    public int getNumberOfPeopleOn(Date date, String airportName){

        //Calculate the total number of people who have flights on that day on a particular airport
        //This includes both the people who have come for a flight and who have landed on an airport after their flight

        int count = 0;

        return count;
    }

    public int calculateFlightFare(Integer flightId){

        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be jsut checking price

        int noOfPeopleBooked = flightToPassengerMap.get(flightId).size();
        return noOfPeopleBooked*50 + 3000;

    }


    public String bookATicket(Integer flightId,Integer passengerId){

        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"


        if(Objects.nonNull(flightToPassengerMap.get(flightId)) &&(flightToPassengerMap.get(flightId).size()<flightMap.get(flightId).getMaxCapacity())){


            List<Integer> passengers =  flightToPassengerMap.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }

            passengers.add(passengerId);
            flightToPassengerMap.put(flightId,passengers);
            return "SUCCESS";
        }
        else if(Objects.isNull(flightToPassengerMap.get(flightId))){
            flightToPassengerMap.put(flightId,new ArrayList<>());
            List<Integer> passengers =  flightToPassengerMap.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }

            passengers.add(passengerId);
            flightToPassengerMap.put(flightId,passengers);
            return "SUCCESS";

        }
        return "FAILURE";
    }


    public String cancelATicket(Integer flightId,Integer passengerId){

        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId

        List<Integer> passengers = flightToPassengerMap.get(flightId);
        if(passengers == null){
            return "FAILURE";
        }


        if(passengers.contains(passengerId)){
            passengers.remove(passengerId);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){

        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        HashMap<Integer,List<Integer>> passengerToFlightDb = new HashMap<>();
        //We have a list from passenger To flights database:-
        int count = 0;
//
        return count;
    }
    public String addFlight( Flight flight){

        //Return a "SUCCESS" message string after adding a flight.
        flightMap.put(flight.getFlightId(),flight);
        return "SUCCESS";
    }

    public String getAirportNameFromFlightId(Integer flightId){

        //We need to get the starting aiport from where the flight will be taking off
        //return null incase the flightId is invalid or you are not able to find the airportName

        if(flightMap.containsKey(flightId)){
            City city = flightMap.get(flightId).getFromCity();
            for(Airport airport:airportMap.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId){

        int noOfPeopleBooked = flightToPassengerMap.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
    }

    public String addPassenger(Passenger passenger){

        passengerMap.put(passenger.getPassengerId(),passenger);
        return "SUCCESS";
    }

}
