package com.datasrc.processor;

import com.datasrc.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Service
public class DataProcessorStringReactor implements DataProcessor<Flux<StringValue>> {
    private static final Logger log = LoggerFactory.getLogger(DataProcessorStringReactor.class);

    @Override
    public Flux<StringValue> process(Flux<StringValue> dataflow) {
        log.info("processor");

        Scheduler timer = Schedulers.newParallel("processor-thread", 2);

        var dataSeq = dataflow
                .delayElements(Duration.ofSeconds(2), timer)
                .map(val -> new StringValue(val.value().toUpperCase()))
                .doOnNext(val -> log.info("out val:{}", val));

        log.info("processor method finished");
        return dataSeq;
    }
}
