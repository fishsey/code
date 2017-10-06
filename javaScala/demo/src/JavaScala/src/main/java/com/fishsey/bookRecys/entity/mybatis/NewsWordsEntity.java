package com.fishsey.bookRecys.entity.mybatis;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by root on 8/4/17.
 */
@Component
public class NewsWordsEntity implements Cloneable, Serializable
{
    private int newsId;
    private String keyWords;

    public void setNewsId(int newsId)
    {
        this.newsId = newsId;
    }
    public void setKeyWords(String keyWords)
    {
        this.keyWords = keyWords;
    }

    public int getNewsId()
    {
        return newsId;
    }
    public String getKeyWords()
    {
        return keyWords;
    }



    public void clear()
    {
        this.newsId = -1;
        this.keyWords = "";
    }

    @Override
    public NewsWordsEntity clone()
    {
        NewsWordsEntity page = null;
        try
        {
            page = (NewsWordsEntity) super.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public String toString()
    {
        return "NewsKeyWordsEntity{" +
                "newsId=" + newsId +
                ", keyWords='" + keyWords + '\'' +
                '}';
    }
}
