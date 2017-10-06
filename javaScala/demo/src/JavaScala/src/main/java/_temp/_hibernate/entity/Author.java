package _temp._hibernate.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 7/27/17.
 */
public class Author
{
    private int id;
    private String name;

    private Page page;
    private Set<Page> pages =  new HashSet<>();

    public Set<Page> getPages()
    {
        return pages;
    }

    public void setPages(Set<Page> pages)
    {
        this.pages = pages;
    }

    public Page getPage()
    {
        return page;
    }
    public void setPage(Page page)
    {
        this.page = page;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}
