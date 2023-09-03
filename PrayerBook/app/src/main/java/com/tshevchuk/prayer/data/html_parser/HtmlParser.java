package com.tshevchuk.prayer.data.html_parser;

import androidx.core.util.Pair;

/**
 * Created by taras on 04.04.16.
 */
public interface HtmlParser {
    Pair<CharSequence, Integer> parseHtml(String htmlToParse);
}
