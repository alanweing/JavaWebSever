package controllers;

import http.Context;

public interface IController {
    void handleConnection(final Context ctx);
}
