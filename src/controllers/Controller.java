package controllers;

import files.FileCache;
import files.FileManager;
import http.Context;

import java.io.File;

class Controller {
    protected static void sendPage(final Context ctx, final String pathToPage) {
        final File file = FileManager.getFile(pathToPage);
        if (file != null)
            ctx.getResponse().send(FileCache.getFile(pathToPage));
        else
            ctx.getResponse().send404();
    }
}
