package com.ljt.study.lang.jdk9;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author LiJingTang
 * @date 2025-06-25 11:00
 */
public class FlowTest {

    @Test
    void flow() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new Flow.Subscriber<>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("onSubscribe");
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println(item);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        publisher.consume(System.out::println);
        publisher.submit("hello world");
    }


}
