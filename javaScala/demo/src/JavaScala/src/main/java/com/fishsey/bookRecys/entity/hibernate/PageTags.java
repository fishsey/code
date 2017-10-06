package com.fishsey.bookRecys.entity.hibernate;


import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by root on 6/27/17.
 */
@Component
public class PageTags implements Cloneable
{
    private int bookid;
    private List<String> marktags;


    public int getBookid()
    {
        return bookid;
    }
    public void setBookid(int bookid)
    {
        this.bookid = bookid;
    }


    public List<String> getMarktags()
    {
        return marktags;
    }
    public void setMarktags(List<String> marktags)
    {
        this.marktags = marktags;
    }


    @Override
    public String toString()
    {
        return "PageTags{" +
                "bookid=" + bookid +
                ", marktags=" + marktags +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
