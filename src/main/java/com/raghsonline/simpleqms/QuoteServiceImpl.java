package com.raghsonline.simpleqms;

import org.springframework.beans.factory.annotation.Autowired;

public class QuoteServiceImpl implements  QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public Quote save(Quote quote) {
        return quoteRepository.save(quote);
    }
}
