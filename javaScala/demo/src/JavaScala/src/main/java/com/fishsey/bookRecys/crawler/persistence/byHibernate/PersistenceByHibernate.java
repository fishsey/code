package com.fishsey.bookRecys.crawler.persistence.byHibernate;

import com.fishsey.bookRecys.dao.hibernate.impl.BaseDaoImpl;
import com.fishsey.bookRecys.entity.hibernate.Page;
import com.fishsey.bookRecys.entity.hibernate.PageRecyList;
import com.fishsey.bookRecys.entity.hibernate.PageTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by root on 7/1/17.
 */

@Service
public class PersistenceByHibernate
{
    @Autowired
    public Page page;
    @Autowired
    public PageRecyList pageRecyList;
    @Autowired
    public PageTags pageTags;

    @Autowired
    private BaseDaoImpl baseDaoIml;


    public BaseDaoImpl getBaseDaoIml()
    {
        return baseDaoIml;
    }

    public String PicAbs;
    public String getPicAbs()
    {
        return PicAbs;
    }
    public void setPicAbs(String picAbs)
    {
        PicAbs = picAbs;
    }

    public HashSet<Integer> primaryKey = new HashSet<>();


    public Page getPage()
    {
        return page;
    }
    public void setPage(Page page)
    {
        this.page = page;
    }

    public PageRecyList getPageRecyList()
    {
        return pageRecyList;
    }
    public void setPageRecyList(PageRecyList pageRecyList)
    {
        this.pageRecyList = pageRecyList;
    }

    public PageTags getPageTags()
    {
        return pageTags;
    }
    public void setPageTags(PageTags pageTags)
    {
        this.pageTags = pageTags;
    }


    public PersistenceByHibernate()
    {
    }


    public void recysList(String key, String value)
    {
        if (value != null && !value.trim().equals(""))
        {
            String[] lst = value.trim().split("[ ]+");
            HashMap<Integer, Float> recymap = new HashMap<>();
            for (String s : lst)
            {
                recymap.put(Integer.parseInt(s), 1.0f);
            }
            pageRecyList.setRecyBooks(recymap);
        }
        else
        {
            pageRecyList.setRecyBooks(new HashMap<>());
        }

    }

    public void author(String key, String value)
    {
        page.setAuthor(value.trim());
    }

    public void catalog(String key, String value)
    {
        page.setCatalog(value.trim());
    }

    public void authorIntro(String key, String value)
    {
        page.setAuthorIntro(value.trim());
    }

    public void title(String key, String value)
    {
        page.setTitle(value.trim());
    }

    public void url(String key, String value)
    {
        page.setUrl(value.trim());
    }

    public void tags(String key, String value)
    {
        if (value != null && !value.equals(""))
        {
            String[] lst = value.split("[ ]+");
            ArrayList<String> tags = new ArrayList<>();
            for (String s : lst)
            {
                tags.add(s);
            }
            pageTags.setMarktags(tags);
        }
        else
        {
            pageTags.setMarktags(new ArrayList<>());
        }
    }

    public void contentIntro(String key, String value)
    {
        page.setContentIntro(value.trim());
    }

    public void picPath(String key, String value)
    {
        if (value != null && !value.equals(""))
        {
            page.setPicPath(this.PicAbs+value.trim());
        }
        else
        {
            page.setTitle(value.trim());
        }
    }

    public void picUrl(String key, String value)
    {
        page.setPicUrl(value.trim().split("\"")[1]);
    }

    public void Id(String key, String value)
    {
        int bookid = 0; //default is 0
        if (value != null && !value.equals(""))
        {
            bookid = Integer.parseInt(value.trim());
        }
        pageRecyList.setBookid(bookid);
        pageTags.setBookid(bookid);
        page.setBookid(bookid);

    }



    public void flush()
    {
        baseDaoIml.flush();
    }


    @Transactional(readOnly = false)
    public void last() throws CloneNotSupportedException
    {
        if (!primaryKey.contains(page.getBookid()))
        {
            page.setRecyList((PageRecyList) pageRecyList.clone());
            page.setTags((PageTags) pageTags.clone());
            baseDaoIml.save(page.clone());
            primaryKey.add(page.getBookid());
        }

    }

}
