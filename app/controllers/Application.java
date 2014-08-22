package controllers;

import play.mvc.*;

public class Application extends Controller {

    public static Result rootOptions() {
        return noContent();
    }

    public static Result options(String url) {
        return noContent();
    }

}
