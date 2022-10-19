package com.raghsonline.simpleqms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuoteMain implements CommandLineRunner {

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public void run(String... args) throws Exception {
        for(Quote quote : QuoteUtil.quoteList) {
            quote = quoteRepository.save(quote);
            System.out.println("Quote inserted : " + quote.toString());
        }
    }

    public static void main(String[] args) {
        System.out.println("QuoteMain - main() invoked");
        SpringApplication.run(QuoteMain.class, args);
    }
}
