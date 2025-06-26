package fr.rirrsmo.getaway.checker.annotation;

public @interface OpenFGACheck {

    String user();

    String relation();

    String _object();

    // TODO Conditions and contextual tuples
}
