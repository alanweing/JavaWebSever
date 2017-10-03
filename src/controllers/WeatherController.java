package controllers;

import connection.INPERequest;
import files.FileCache;
import http.Context;
import jsonmodel.WeatherResponseModel;
import util.AWEngine;
import util.Debug;
import util.KeyNotDefinedException;
import xml.INPEXMLParser;

import java.util.HashMap;

public class WeatherController extends Controller implements IController {
    @Override
    public void handleConnection(Context ctx) {
        final HashMap<String, String> map = new HashMap<>();
        String[] page = null;
        if (INPERequest.getInstance().getParser() == null) {
            // could'nt make a request to the INPEServer
            ctx.getResponse().send500();
            return;
        }
        final WeatherResponseModel responseModel = new WeatherResponseModel();
        map.put("weatherDescription", responseModel.getWeather_description());
        map.put("pressure", responseModel.getPressure());
        map.put("temperature", responseModel.getTemperature());
        map.put("humidity", responseModel.getHumidity());
        map.put("windDirection", responseModel.getWind_direction());
        map.put("visibility", responseModel.getVisibility());
        map.put("lastUpdate", responseModel.getLast_update());
        try {
            page = AWEngine.parseFile(FileCache.getFile("views/weather.html"), map);
        } catch (KeyNotDefinedException e) {
            Debug.log(e);
        }
        if (page == null)
            ctx.getResponse().send500();
        else
            ctx.getResponse().send(page);
    }
}
