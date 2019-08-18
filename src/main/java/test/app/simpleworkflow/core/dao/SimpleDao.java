package test.app.simpleworkflow.core.dao;

import test.app.simpleworkflow.core.action.Create;
import test.app.simpleworkflow.core.action.Delete;
import test.app.simpleworkflow.core.action.Read;
import test.app.simpleworkflow.core.action.UpdateDao;
import test.app.simpleworkflow.core.entity.Document;

import java.util.Map;

public interface SimpleDao<D extends Document, S extends Map> extends
        Create<D>,
        Read<D>,
        UpdateDao<D>,
        Delete<D>,
        HasStore<S> {
}