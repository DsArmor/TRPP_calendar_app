package ru.valkov.calendarapp.exceptions;

public interface TriConsumer<A,B,C> {
    void apply(A a, B b, C c);
}
