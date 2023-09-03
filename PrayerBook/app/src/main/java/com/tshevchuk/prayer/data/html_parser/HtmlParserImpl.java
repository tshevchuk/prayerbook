package com.tshevchuk.prayer.data.html_parser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.util.Pair;
import android.text.Html;

import javax.inject.Inject;

/**
 * Created by taras on 04.04.16.
 */
public class HtmlParserImpl implements HtmlParser {
    private final Context context;

    @Inject
    public HtmlParserImpl(Context context) {
        this.context = context;
    }

    @Override
    public Pair<CharSequence, Integer> parseHtml(String htmlToParse) {
        ImageGetter imageGetter = new ImageGetter();
        CharSequence parsedHtml = Html.fromHtml(htmlToParse, imageGetter, null);
        int totalSize = (int) (imageGetter.totalSize + htmlToParse.length() * 2);
        return new Pair<>(parsedHtml, totalSize);
    }

    private class ImageGetter implements android.text.Html.ImageGetter {
        private long totalSize;

        public Drawable getDrawable(String source) {
            int id;

            id = context.getResources().getIdentifier("content_" + source, "drawable",
                    context.getPackageName());

            if (id == 0) {
                // the drawable resource wasn't found in our package, maybe it
                // is a stock android drawable?
                id = context.getResources().getIdentifier(source, "drawable",
                        "android");
            }

            if (id == 0) {
                // prevent a crash if the resource still can't be found
                return null;
            } else {
                Drawable d;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    d = context.getDrawable(id);
                } else {
                    //noinspection deprecation
                    d = context.getResources().getDrawable(id);
                }
                if (d != null) {
                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                    if (d instanceof BitmapDrawable) {
                        Bitmap bmp = ((BitmapDrawable) d).getBitmap();
                        if (bmp != null) {
                            totalSize += bmp.getByteCount();
                        }
                    }
                }
                return d;
            }
        }

    }
}
