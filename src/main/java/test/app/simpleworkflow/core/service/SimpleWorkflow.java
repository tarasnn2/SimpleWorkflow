package test.app.simpleworkflow.core.service;

import test.app.simpleworkflow.core.action.*;
import test.app.simpleworkflow.core.entity.Actor;
import test.app.simpleworkflow.core.entity.Document;

public interface SimpleWorkflow<D extends Document, S extends SignSide, A extends Actor> extends
        Create<D>,
        Read<D>,
        UpdateService<D>,
        Delete<D>,
        Sign<D, S, A> {
}