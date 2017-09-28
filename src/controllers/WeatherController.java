package controllers;

import connection.INPERequest;
import files.FileCache;
import http.Context;
import util.AWEngine;
import util.KeyNotDefinedException;
import xml.INPEXMLParser;

import java.util.HashMap;

public class WeatherController extends Controller implements IController {
    @Override
    public void handleConnection(Context ctx) {
//        sendPage(ctx, "views/weather.html");
        final HashMap<String, String> map = new HashMap<>();
        String[] page = null;
        map.put("weatherDescription", INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.tempo_desc));
        map.put("pressure", INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.pressao));
        map.put("temperature", INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.temperatura));
        map.put("humidity", INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.umidade));
        map.put("windDirection", INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.vento_dir));
        map.put("visibility", INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.visibilidade));
        map.put("lastUpdate", INPERequest.getInstance().getParser().getValue(INPEXMLParser.CHILD.atualizacao));
        map.put("background", "404.jpg");
        try {
            page = AWEngine.parseFile(FileCache.getFile("views/weather.html"), map);
        } catch (KeyNotDefinedException e) {
            e.printStackTrace();
        }
        if (page == null)
            ctx.getResponse().send500();
        ctx.getResponse().send(page);
    }
}
