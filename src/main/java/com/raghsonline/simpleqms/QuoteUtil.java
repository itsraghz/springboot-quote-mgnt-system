package com.raghsonline.simpleqms;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class QuoteUtil {

    public static List<Quote> quoteList = new ArrayList<>();

    static {
        QuoteUtil.quoteList = transformQuoteList(com.raghsonline.qms.util.QuoteUtil.quotesList);
    }

    public static List<Quote> transformQuoteList(List<String> quotesStrList) {
        Quote quote = null;
        for(String str : quotesStrList) {
            quote = new Quote();
            quote.setQuote(str);
            quoteList.add(quote);
        }
        return quoteList;
    }
}

