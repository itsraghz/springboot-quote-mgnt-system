package com.raghsonline.qms.web;

import com.raghsonline.qms.service.QuoteService;
import com.raghsonline.qms.util.QuoteUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    public String getNow() {
        return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss a z").format(new Date());
    }

    public String getNowStyled() {
        return "<u>" + getNow() + "</u>";
    }

    @GetMapping("/")
    public String hello() {
        System.out.println("Context '/' invoked");
        return "Hello from <b>" + this.getClass().getSimpleName() + "</b> @ " + getNowStyled();
    }

    @GetMapping("/rawQuotes")
    public List<String> getAllRawQuotes() {
        System.out.println("Context '/rawQuotes' invoked");
        return QuoteUtil.rawQuotesList;
    }

    @GetMapping("/quotes")
    public List<String> getAllQuotes() {
        System.out.println("Context '/quotes' invoked");
        return QuoteUtil.quotesList;
    }

    @GetMapping("/tags")
    public List<String> getAllTags() {
        System.out.println("Context '/tags' invoked");
        return QuoteUtil.tagsList.stream().sorted().collect(Collectors.toList());
    }

    @GetMapping("/quotes/{tag}")
    public List<String> getAllQuotesForTag(@PathVariable String tag) {
        System.out.println("Context '/quotes/{tag}' invoked for tag - [" + tag + "]");
        List<String> matchingQuotes = new ArrayList<>();
        if(QuoteUtil.tagsList.contains(tag)) {
            System.out.println("Matching quote(s) found!!");
            matchingQuotes = QuoteUtil.getTagQuotesListMap().get(tag.toLowerCase());
            System.out.println("matchingQuotes :: " + matchingQuotes);
            return matchingQuotes;
        } else {
            System.out.println("There are NO matching quotes found!!!");
            return Arrays.asList("No Quotes for the tag [" + tag + "]");
        }
    }

    @GetMapping("/anyDuplicateTags")
    public String findDuplicateTagsIfAny() {
        System.out.println("Context /anyDuplicateTags invoked");
        boolean isDuplicate = false; //QuoteUtil.areDuplicatesFound();
        return "There are " + (isDuplicate ? " <b>a few</b> " : " <u><i>no</i></u> ")
                + " duplicates found - as of " + getNowStyled() ;
    }
}
