package com.raghsonline.qms.service;

import com.raghsonline.qms.model.Quote;
import com.raghsonline.qms.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService{

    private QuoteRepository repository;

    @Autowired
    public void setRepository(QuoteRepository quoteRepository) {
        this.repository = quoteRepository;
    }

    @Override
    public Quote getQuoteById(long id) {
        return this.repository.findById(id).get();
    }

    @Override
    public List<Quote> getAllQuotes() {
        return this.repository.findAll();
    }

    @Override
    public Quote insert(Quote quote) {
        return this.repository.save(quote);
    }

    @Override
    public Quote update(Quote quote) {

        Quote quoteDB = this.repository.findById(quote.getId()).get();

        return this.repository.save(quote);
    }

    @Override
    public void delete(long id) {
        this.repository.deleteById(id);
    }
}
