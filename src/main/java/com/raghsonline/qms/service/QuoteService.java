package com.raghsonline.qms.service;

import com.raghsonline.qms.model.Quote;

import java.util.List;

public interface QuoteService {

    public Quote getQuoteById(long id);

    public List<Quote> getAllQuotes();

    public Quote insert(Quote quote);

    public Quote update(Quote quote);

    public void delete(long id);
}
