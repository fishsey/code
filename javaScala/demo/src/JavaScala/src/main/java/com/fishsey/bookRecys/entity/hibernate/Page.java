package com.fishsey.bookRecys.entity.hibernate;

import org.springframework.stereotype.Component;

/**
 * Created by root on 6/27/17.
 */
@Component
public class Page implements Cloneable
{
    //property
    private int bookid;

    private String url;
    private String title;
    private String author;
    private String picUrl;
    private String picPath;

    private String authorIntro;
    private String contentIntro;
    private String catalog;


    //one-to-one
    private PageTags tags;
    private PageRecyList recyList;



    public int getBookid()
    {
        return bookid;
    }

    public void setBookid(int bookid)
    {
        this.bookid = bookid;
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

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public String getPicPath()
    {
        return picPath;
    }

    public void setPicPath(String picPath)
    {
        this.picPath = picPath;
    }

    public String getAuthorIntro()
    {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro)
    {
        this.authorIntro = authorIntro;
    }

    public String getContentIntro()
    {
        return contentIntro;
    }

    public void setContentIntro(String contentIntro)
    {
        this.contentIntro = contentIntro;
    }

    public String getCatalog()
    {
        return catalog;
    }

    public void setCatalog(String catalog)
    {
        this.catalog = catalog;
    }

    public PageTags getTags()
    {
        return tags;
    }

    public void setTags(PageTags tags)
    {
        this.tags = tags;
    }

    public PageRecyList getRecyList()
    {
        return recyList;
    }

    public void setRecyList(PageRecyList recyList)
    {
        this.recyList = recyList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Page page = null;
        page  = (Page) super.clone();
        return page;
    }


}
