package com.openweathermap.controllers;


public class TemperatureController {
    public static long toCelsius(Double input) {
        return Math.round(input - 273.15);
    }
}
