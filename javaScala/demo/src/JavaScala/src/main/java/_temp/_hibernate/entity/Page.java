package _temp._hibernate.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 6/27/17.
 */
public class Page implements Cloneable
{
    private int bookid;
    private String url;
    private String title;

    private Author author;
    private Set<Author> authors = new HashSet<>();

    public Set<Author> getAuthors()
    {
        return authors;
    }

    public void setAuthors(Set<Author> authors)
    {
        this.authors = authors;
    }

    public Author getAuthor()
    {
        return author;
    }
    public void setAuthor(Author author)
    {
        this.author = author;
    }

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

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Page page = null;
        page  = (Page) super.clone();
        return page;
    }


}
