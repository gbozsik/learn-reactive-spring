package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("exception occured")))
//                .concatWith(Flux.just("After error"))  // won`t get it after exception
                .log();

        stringFlux.subscribe(System.out::println,
                exception -> System.err.println(exception),
                () -> System.out.println("completed"));
    }

    @Test
    public void fluxTestElementsWithoutError() {
        Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("SpringBoot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTestElementsWithError1() {
        Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("exception occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("SpringBoot")
                .expectNext("Reactive Spring")
//                .expectError(RuntimeException.class)
                .expectErrorMessage("exception occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsCountWithError() {
        Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("exception occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("exception occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsWithError() {
        Flux<String> stringFlux = Flux.just("Spring", "SpringBoot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("exception occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "SpringBoot", "Reactive Spring")
                .expectErrorMessage("exception occurred")
                .verify();
    }

    @Test
    public void monoTest() {
        Mono<String> stringMono = Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_withError() {
        StepVerifier.create(Mono.error(new RuntimeException("exception occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
