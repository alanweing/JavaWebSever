package controllers;

import com.google.gson.Gson;
import http.Context;
import jsonmodel.WeatherResponseModel;

public class WeatherRESTController extends Controller implements IController {
    @Override
    public void handleConnection(Context ctx) {
        final Gson gson = new Gson();
        final String jsonResponse = gson.toJson(new WeatherResponseModel());
        ctx.getResponse().getHeader().setContentType("application/json");
        ctx.getResponse().send(jsonResponse.getBytes());
    }
}
