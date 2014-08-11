package controllers;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result rootOptions() {
        return noContent();
    }

    public static Result options(String url) {
        return noContent();
    }

}
