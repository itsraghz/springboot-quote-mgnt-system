package com.raghsonline.qms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class QuoteUtil {

    private static final Logger logger = LoggerFactory.getLogger(QuoteUtil.class);

    public static final List<String> quotesList = IOUtil.loadQuotes();

    public static final List<String> rawQuotesList = IOUtil.loadQuotes();

    public static final List<String> tagsList = new ArrayList<>();

    private static Map<String, List<String>> quotesTagsListMap = new HashMap<>();

    private static Map<String, List<String>> tagQuotesListMap = new HashMap<>();

    private static boolean canStripBulletsAtBegin = true;

    private static boolean canStripTagsInQuotes = true;

    public static List<String> stripTextAtBeginList = Arrays.asList("* ");

    private QuoteUtil() {}

    static {
        getStats();
    }

    public static String getNow() {
        return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss a z").format(new Date());
    }

    public static void testFormattingLines() {
        var strToTest = "stack";
        System.out.printf("|%s|%n", strToTest);
        System.out.printf("|%10s|%n", strToTest);
        System.out.printf("|%-10s|%n", strToTest);
        System.out.printf("|%-10s|%n", strToTest);
    }

    public static void printFormattedMsg(String msg, String value) {
        System.out.printf("| %-50s | ", msg);
        System.out.printf("%-30s | %n", value);
    }

    public static void printFormattedTag(String value) {
        System.out.printf(" %-12s  | ", value);    }

    public static void getStats() {

        logger.info("========================================================================================");
        System.out.printf("| %-30s ", "");
        System.out.printf("%-52s |%n", "Quotes - Statistics");
        logger.info("========================================================================================");

        printFormattedMsg("Present Date and Time ", getNow());

        logger.info("----------------------------------------------------------------------------------------");

        long count = quotesList.size();
        printFormattedMsg("Total # of entries in the file ", ""+count);

        List<String> commentedEntriesList = quotesList.stream().filter(x -> x.startsWith("#")).collect(Collectors.toList());
        List<String> validQuotesList = quotesList.stream().filter(x -> !x.startsWith("#")).collect(Collectors.toList());
        List<String> validQuotesWithTagsList = quotesList.stream().filter(x -> !x.startsWith("#")).filter(x -> x.contains("#")).collect(Collectors.toList());

        long commentsCount = commentedEntriesList.size();
        printFormattedMsg("Total # of Entries which are Comments ", ""+commentsCount);

        long validQuotesCount = validQuotesList.size();
        printFormattedMsg("Total # of Entries which are Non-Comments ", ""+validQuotesCount);

        long validQuotesWithTagsCount = validQuotesList.size();
        printFormattedMsg("Total # of Valid Entries with Tags ", ""+validQuotesWithTagsCount);

        long quotesWithoutTagsCount = quotesList.stream().filter(x -> !x.startsWith("#")).filter(x -> !x.contains("#")).count();
        long quotesWithTagsCount = quotesList.stream().filter(x -> !x.startsWith("#")).filter(x -> x.contains("#")).count();
        printFormattedMsg("Count of Valid Quotes (Non Comments) w/o Tags", ""+quotesWithoutTagsCount);
        printFormattedMsg("Count of Valid Quotes (Non Comments) with Tags ", ""+quotesWithTagsCount);
        logger.info("----------------------------------------------------------------------------------------");


        logger.info("");
        logger.info("Commented entries in the file");
        logger.info("-----------------------------");
        quotesList.stream().filter(x -> x.startsWith("#")).forEach(System.out::println);
        logger.info("");

        validQuotesWithTagsList.forEach(QuoteUtil::separateTags);
        //validQuotesList.forEach(QuoteUtil::separateTags);

        //printQuoteStats();

        printStats(getQuotesTagsListMap(), "Statistics - Quote Vs Tags ", "Quote", "Tags");
        printStats(getTagQuotesListMap(), "Statistics - Tag Vs Quotes ", "Tag", "Quotes");

        printTags();
    }

    public static void separateTags(String quote) {
        var st = new StringTokenizer(quote, " ");
        List<String> tagsList = new ArrayList<>();

        String word;

        while(st.hasMoreTokens()) {
            word = st.nextToken();
            if(word.startsWith("#")) {
                tagsList.add(word);

                // Add it after adding the tag to the tagList, for not breaking it with further cleansing ops
                //word = word.toLowerCase();

                // remove the "#" key in front of each tag, only for the global list
                word = word.substring(1);

                addTagsToList(word);
            }
        }

        System.out.print("Tags Count :: " + tagsList.size() + " || Tags : [ ");
        tagsList.stream().sorted().forEach(QuoteUtil::printFormattedTag);
        //tagsList.stream().sorted().map(x-> x.substring(1)).collect(Collectors.joining(", "));
        logger.info("]");

        if(isCanStripBulletsAtBegin()) {
            //quote = cleanseQuote(quote, stripTextAtBeginList);
            quote = cleanseQuote(quote);
        }

        if(isCanStripTagsInQuotes()) {
            quote = cleanseQuote(quote, tagsList);
        }

        bindQuotesWithTags(quote, tagsList);
    }

    public static String cleanseQuote(String quote) {
        if(null==quote || quote.trim().length()<=0) {
            return quote;
        }

        if(isCanStripTagsInQuotes()) {
            for(String item : stripTextAtBeginList) {
                int len = item.length();
                int index = quote.indexOf(item);
                if(index!=-1) {
                    quote = quote.substring(len);
                }
            }
        }
        return quote;
    }

    public static String cleanseQuote(String quote, List<String> itemsToRemove) {
        if(null==quote || quote.trim().length()<=0) {
            return quote;
        }

        var isDebug = quote.startsWith("Pray not because you need something but because you have");

        var quoteBfr = new StringBuilder(quote);

        if(isDebug) {
            logger.info(String.format("..... [DEBUG] quote : [%s]", quote));
            logger.info(String.format("..... [DEBUG] length : %d", quote.length()));
        }

        for(String item : itemsToRemove) {
            int len = item.length();
            int index = quoteBfr.indexOf(item);
            if(isDebug) logger.info(".......... [DEBUG] item=" + item + ", len=" + len + ", index=" + index);
            if(index!=-1) {
                quoteBfr.delete(index, index + len);
                if(isDebug)  logger.info(String.format("..... [DEBUG] quote After deletion : [%s]", quoteBfr));
            }
        }

        return quoteBfr.toString().trim();
    }

    public static void bindQuotesWithTags(String quote, List<String> tagsList) {

        //quotesTagsListMap
        //tagQuotesMap

        // Add the tagsList to the quote in the map
        getQuotesTagsListMap().put(quote, tagsList);

        // Also, add the quote to each of the tag for retrieval
        tagsList.forEach(x -> addQuoteToTag(x, quote));
    }

    public static void addQuoteToTag(String tag, String quote) {

        if(tag.startsWith("#")) tag = tag.substring(1);

        List<String> quoteList = getTagQuotesListMap().get(tag);

        if(null==quoteList || quoteList.isEmpty()) {
            quoteList = new ArrayList<>();
        }

        quoteList.add(quote);

        getTagQuotesListMap().put(tag, quoteList);
    }

    public static void addTagsToList(String tag) {
        if(null==tag || tag.trim().length()<=0) return;

        /*if(!tagsList.contains(tag.toLowerCase())) {
            tagsList.add(tag.to ());
        }*/

        if(!tagsList.contains(tag)) {
            tagsList.add(tag);
        }
    }

    public static void printQuoteStats() {

        Set<String> keys = getQuotesTagsListMap().keySet();
        Iterator<String> iterator = keys.iterator();

        String quote;
        List<String> tagsList;

        logger.info("========================================================================================");
        System.out.printf("| %-30s ", "");
        System.out.printf("%-52s |%n", "Statistics - Quote Vs Tags ");
        logger.info("========================================================================================");

        printFormattedMsg("Present Date and Time ", getNow());

        logger.info("----------------------------------------------------------------------------------------");

        while(iterator.hasNext()) {
            quote = iterator.next();
            tagsList = getQuotesTagsListMap().get(quote);
            logger.info("Quote : " + quote + " | Tags #  : " + tagsList.size());
        }

        logger.info("----------------------------------------------------------------------------------------");
    }

    public static void printTags() {
        for(String tag: tagsList) {
            logger.info(tag);
        }
    }

    public static void printStats(Map<String, List<String>> mapToPrint, String headerTxt, String keyTxt, String valueTxt) {

        Set<String> keys = mapToPrint.keySet();
        Iterator<String> iterator = keys.iterator();

        String key;
        List<String> valueList;

        logger.info("========================================================================================");
        System.out.printf("| %-30s ", "");
        System.out.printf("%-52s |%n", headerTxt);
        logger.info("========================================================================================");

        printFormattedMsg("Present Date and Time ", getNow());

        logger.info("----------------------------------------------------------------------------------------");

        while(iterator.hasNext()) {
            key = iterator.next();
            valueList = mapToPrint.get(key);
            logger.info(keyTxt + " : " + key + " | " + valueTxt + " # : " + valueList.size());
        }

        logger.info("----------------------------------------------------------------------------------------");
    }

    public static Map<String, List<String>> getQuotesTagsListMap() {
        return quotesTagsListMap;
    }

    public static void setQuotesTagsListMap(Map<String, List<String>> quotesTagsListMap) {
        QuoteUtil.quotesTagsListMap = quotesTagsListMap;
    }

    public static Map<String, List<String>> getTagQuotesListMap() {
        return tagQuotesListMap;
    }

    public static void setTagQuotesListMap(Map<String, List<String>> tagQuotesListMap) {
        QuoteUtil.tagQuotesListMap = tagQuotesListMap;
    }

    public static boolean isCanStripBulletsAtBegin() {
        return canStripBulletsAtBegin;
    }

    public static void setCanStripBulletsAtBegin(boolean canStripBulletsAtBegin) {
        QuoteUtil.canStripBulletsAtBegin = canStripBulletsAtBegin;
    }

    public static boolean isCanStripTagsInQuotes() {
        return canStripTagsInQuotes;
    }

    public static void setCanStripTagsInQuotes(boolean canStripTagsInQuotes) {
        QuoteUtil.canStripTagsInQuotes = canStripTagsInQuotes;
    }
}
