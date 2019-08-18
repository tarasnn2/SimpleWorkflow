package test.app.simpleworkflow.impl.security;

import test.app.simpleworkflow.core.entity.Actor;

public class SessionHelper {

    //эмуляция Session хранилища
    private static ThreadLocal<Actor> session = new ThreadLocal();

    public static Actor getCurrentUser() {
        return session.get();
    }

    public static void setCurrentUser(Actor actor) {
        session.remove();
        session.set(actor);
    }
}