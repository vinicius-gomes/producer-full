package com.b2wdigital.lets.juvenal.producer.job;

import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.service.DictionaryService;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DictionaryJob {
    @Inject
    DictionaryService dictionaryService;

    @Scheduled(every = "5m", identity = "dictionary-job")
    public void updateAllDictionaries() throws PayloadValidationException {
        dictionaryService.updateDictionaries();
    }
}
