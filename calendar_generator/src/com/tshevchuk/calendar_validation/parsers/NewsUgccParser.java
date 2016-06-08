package com.tshevchuk.calendar_validation.parsers;

import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by taras on 09.05.16.
 */
public class NewsUgccParser implements Parser{
    private static final String URL = "http://news.ugcc.ua/calendar/%d-%d/";


    @Override
    public List<DayInfo> parse(int year, int month) throws IOException {
        String url = String.format(Locale.US, URL, year, month);
        Document doc = Jsoup.connect(url).get();
        Elements calendarElements = doc.getElementsByClass("calendar");
        if (calendarElements.size() != 1) {
            throw new IllegalStateException("calendar size " + calendarElements.size() + " != 1");
        }
        Element calendarElement = calendarElements.first();

        Elements cols = calendarElement.getElementsByClass("col");

        List<DayInfo> days = new ArrayList<>();

        for (Element col : cols) {
            if (!col.getElementsByClass("inactive").isEmpty()) {
                continue;
            }
            DayInfo di = new DayInfo();
            if(col.hasClass("zagal")){
                di.setPist(DayInfo.PIST_ZAHALNYTSYA);
            } else if(col.hasClass("pist")){
                di.setPist(DayInfo.PIST_PIST);
            } else if(col.hasClass("spist")){
                di.setPist(DayInfo.PIST_STROHYY_PIST);
            } else {
                di.setPist(DayInfo.PIST_NONE);
            }
            Element date = col.getElementsByClass("date").first();
            di.setRedDay(date.hasClass("calendarred"));
            di.setDate(DateUtils.dateToDateStr(year, month, Integer.valueOf(date.text())));
            Element hday = col.getElementsByClass("hday").first();
            simplifyHtml(hday);
            di.setDay(hday.html().replaceAll("&nbsp;", " "));
            Element hperson = col.getElementsByClass("hperson").first();
            simplifyHtml(hperson);
            di.setPerson(hperson.html().replaceAll("&nbsp;", " "));
            days.add(di);
        }
        return days;
    }

    private void parseBibleQuote(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element el = doc.select("div.content > div.read").first();
        el.removeAttr("class");
        el.select("sup.virshid").removeAttr("class");
        System.out.println(el);
    }

    private void simplifyHtml(Element element) {
        Elements elements = element.getElementsByTag("span");
        for (Element el : elements) {
            if (el.hasClass("citalic")) {
                el.wrap("<i></i>");
            }
            if (el.hasClass("cbold")) {
                el.wrap("<b></b>");
            }
            if (el.hasClass("cred")) {
                el.wrap("<r></r>");
            }
            el.unwrap();
        }
    }

}
