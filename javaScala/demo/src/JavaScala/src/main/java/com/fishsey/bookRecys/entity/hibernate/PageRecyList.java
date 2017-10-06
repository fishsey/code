package com.fishsey.bookRecys.entity.hibernate;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by root on 6/27/17.
 */
@Component
public class PageRecyList implements Cloneable
{
    private int bookid;
    private Map<Integer, Float> recyBooks;//<similarity, recybookid>



    public int getBookid()
    {
        return bookid;
    }
    public void setBookid(int bookid)
    {
        this.bookid = bookid;
    }

    public Map<Integer, Float> getRecyBooks()
    {
        return recyBooks;
    }
    public void setRecyBooks(Map<Integer, Float> recyBooks)
    {
        this.recyBooks = recyBooks;
    }

    @Override
    public String toString()
    {
        return "PageRecyList{" +
                "bookid=" + bookid +
                ", recyBooks=" + recyBooks +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
