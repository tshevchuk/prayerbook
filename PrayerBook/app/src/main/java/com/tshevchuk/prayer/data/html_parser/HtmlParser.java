package com.tshevchuk.prayer.data.html_parser;

import android.support.v4.util.Pair;

/**
 * Created by taras on 04.04.16.
 */
public interface HtmlParser {
    Pair<CharSequence, Long> parseHtml(String htmlToParse);
}
