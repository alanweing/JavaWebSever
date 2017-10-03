package jsonmodel;

import connection.INPERequest;
import xml.INPEXMLParser;

public class WeatherResponseModel {
    private final String
            weather_description,
            pressure,
            temperature,
            humidity,
            wind_direction,
            visibility,
            last_update,
            background;

    public WeatherResponseModel() {
        weather_description = INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.tempo_desc);
        pressure = INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.pressao);
        temperature = INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.temperatura);
        humidity = INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.umidade);
        wind_direction = INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.vento_dir);
        visibility = INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.visibilidade);
        last_update = INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.atualizacao);
        background = "404.jpg";
    }

    public String getWeather_description() {
        return weather_description;
    }

    public String getPressure() {
        return pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getLast_update() {
        return last_update;
    }

    public String getBackground() {
        return background;
    }
}
