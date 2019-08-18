package test.app.simpleworkflow.core.action;

import test.app.simpleworkflow.core.entity.Actor;
import test.app.simpleworkflow.core.entity.Document;
import test.app.simpleworkflow.core.service.SignSide;

public interface Sign<D extends Document, S extends SignSide, A extends Actor> {
    D sign(D document, A actor, S side);
}
