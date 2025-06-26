package fr.rirrsmo.getaway.checker.exception;

import lombok.Getter;

@Getter
public class AccessDenied extends RuntimeException {

    private final String user;
    private final String relation;
    private final String _object;

    public AccessDenied(String user, String relation, String _object) {
        super(String.format("Access denied for user \"%s\" with relation \"%s\" to object \"%s\".",
                user, relation, _object));
        this.user = user;
        this.relation = relation;
        this._object = _object;
    }
}
