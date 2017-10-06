package com.fishsey.bookRecys.entity.mybatis;

import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by root on 7/31/17.
 */

@Component
public class NewsEntity implements Cloneable, Serializable
{
    private int newsId;

    private String url;
    private String source;
    private String time;
    private String title;
    private String text;

    public int getNewsId()
    {
        return newsId;
    }

    public void setNewsId(int newsId)
    {
        this.newsId = newsId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    @Override
    public NewsEntity clone()
    {
        NewsEntity page = null;
        try
        {
            page  = (NewsEntity) super.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return page;
    }

    @Test
    public void test_()
    {
        LocalDateTime ldt = LocalDateTime.parse("2016-10-1020:30:32", DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss"));

        System.out.println(ldt.getDayOfMonth());
    }
}
