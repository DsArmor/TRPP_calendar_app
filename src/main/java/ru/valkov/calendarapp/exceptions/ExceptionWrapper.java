package ru.valkov.calendarapp.exceptions;

import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionWrapper {
    public static <T, R> ResponseEntity<R> wrap(Function<T, R> function, T arg) {
        System.out.println("There post");
        try {
            return ResponseEntity.ok(function.apply(arg));
        } catch (NotFoundException e) {
            System.out.println("Not Found " + e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("internal exception");
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <T> ResponseEntity<T> wrap(Supplier<T> function) {
        System.out.println("There");
        try {
            return ResponseEntity.ok(function.get());
        } catch (NotFoundException e) {
            System.out.println("Not Found " + e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("internal exception");
            return ResponseEntity.internalServerError().build();
        }
    }

    public static  <T> ResponseEntity<Void> wrapWithoutResult(Consumer<T> function, T arg) {
        try {
            function.accept(arg);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
