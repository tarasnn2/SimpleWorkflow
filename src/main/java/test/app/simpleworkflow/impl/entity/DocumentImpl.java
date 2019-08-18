package test.app.simpleworkflow.impl.entity;

import test.app.simpleworkflow.core.entity.Actor;
import test.app.simpleworkflow.core.entity.Document;

import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentImpl implements Document {

    private UUID id;
    private UUID idParent;
    private String name;
    private Actor creator;
    private String creatorSign;
    private Actor acceptor;
    private String acceptorSign;
    private LocalDateTime create = LocalDateTime.now(); //аналог timestamp not null default CURRENT_TIMESTAMP

    private DocumentImpl(DocumentBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.creator = builder.creator;
        this.acceptor = builder.acceptor;
        this.idParent = builder.idParent;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public String getAcceptorSign() {
        return acceptorSign;
    }

    public void setAcceptorSign(String acceptorSign) {
        if (null != this.acceptorSign)
            throw new RuntimeException("Документ уже подписан второй стороной");
        this.acceptorSign = acceptorSign;
    }

    public String getCreatorSign() {
        return creatorSign;
    }

    public void setCreatorSign(String creatorSign) {
        if (null != this.creatorSign)
            throw new RuntimeException("Документ уже подписан первой стороной");
        this.creatorSign = creatorSign;
    }

    public UUID getIdParent() {
        return idParent;
    }

    public Actor getAcceptor() {
        return acceptor;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Actor getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return new StringBuilder(" id=").append(id).append(System.lineSeparator())
                .append(" idParent=").append(idParent).append(System.lineSeparator())
                .append(" name=").append(name).append(System.lineSeparator())
                .append(" creator=").append(creator.getName()).append(System.lineSeparator())
                .append(" creatorSign=").append(creatorSign).append(System.lineSeparator())
                .append(" acceptor=").append(acceptor.getName()).append(System.lineSeparator())
                .append(" acceptorSign=").append(acceptorSign).append(System.lineSeparator())
                .append(" create=").append(create).append(System.lineSeparator())
                .toString();
    }

    public static class DocumentBuilder {
        private UUID id;
        private UUID idParent;
        private String name;
        private Actor creator;
        private Actor acceptor;

        public static DocumentBuilder getBuilder() {
            return new DocumentBuilder();
        }

        public DocumentBuilder setAcceptor(Actor acceptor) {
            this.acceptor = acceptor;
            return this;
        }

        public DocumentBuilder setIdParent(UUID idParent) {
            this.idParent = idParent;
            return this;
        }

        public DocumentBuilder setCreator(Actor creator) {
            this.creator = creator;
            return this;
        }

        public DocumentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public DocumentBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public DocumentImpl build() {
            return new DocumentImpl(this);
        }
    }
}
