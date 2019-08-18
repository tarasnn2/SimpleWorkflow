package test.app.simpleworkflow.impl.entity;

import test.app.simpleworkflow.core.entity.Actor;

import java.util.UUID;

public class ActorImpl implements Actor {

    private UUID id;
    private String name;

    public ActorImpl(Actor actor) {
        id = actor.getId();
        name = actor.getName();
    }

    public ActorImpl(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }


    @Override
    public String getName() {
        return name;
    }

}