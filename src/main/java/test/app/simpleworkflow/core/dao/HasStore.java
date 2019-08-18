package test.app.simpleworkflow.core.dao;

import java.util.Map;

public interface HasStore<S extends Map> {
    S getStore();
}
