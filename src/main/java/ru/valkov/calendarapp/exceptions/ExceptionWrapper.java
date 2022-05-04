package ru.valkov.calendarapp.exceptions;

import org.springframework.http.ResponseEntity;

import java.util.function.*;

public class ExceptionWrapper {
    public static <T, R> ResponseEntity<R> wrap(Function<T, R> function, T arg) {
        try {
            return ResponseEntity.ok(function.apply(arg));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <T, K, R> ResponseEntity<R> wrap(BiFunction<T, K, R> biFunction, T arg1, K arg2) {
        try {
            return ResponseEntity.ok(biFunction.apply(arg1, arg2));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <T> ResponseEntity<T> wrap(Supplier<T> function) {
        try {
            return ResponseEntity.ok(function.get());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <T> ResponseEntity<Void> wrapWithoutResult(Consumer<T> function, T arg) {
        try {
            function.accept(arg);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public static  <T, R> ResponseEntity<Void> wrapWithoutResult(BiConsumer<T, R> function, T arg1, R arg2) {
        try {
            function.accept(arg1, arg2);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public static  <T, K, R> ResponseEntity<Void> wrapWithoutResult(TriConsumer<T, K, R> function, T arg1, K arg2, R arg3) {
        try {
            function.apply(arg1, arg2, arg3);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
