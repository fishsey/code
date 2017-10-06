package com.fishsey.bookRecys.tools;

import com.fishsey.bookRecys.entity.mybatis.NewsEntity;
import com.fishsey.bookRecys.entity.mybatis.NewsWordsEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 7/31/17.
 */
@Component
public class PersisTools
{
    static NewsWordsEntity newsKeyWordsEntity = new NewsWordsEntity();

    public PersisTools()
    {
    }

    public static NewsEntity persisToEntity(String line)
    {
        return generateNew(
                Arrays.asList(line.split("\t")).stream()
                        .peek(field -> field.replaceAll("_$t$_", "\t"))
                        .peek(field -> field.replaceAll("_$line$_", "\n"))
                        .collect(Collectors.toList())
                        .toArray()
        );

    }

    public static NewsEntity generateNew(Object... strings)
    {
        NewsEntity newsEntity = new NewsEntity();
        if (strings.length < 5)
            return null;
        for (int i = 0; i < strings.length; i++)
        {
            if (strings[i].equals("") || strings[i] == null)
                return null;
        }

        newsEntity.setUrl((String) strings[0]);
        newsEntity.setSource((String) strings[1]);
        newsEntity.setTime((String) strings[2]);
        newsEntity.setTitle((String) strings[3]);
        newsEntity.setText((String) strings[4]);

        return newsEntity;
    }


    public static NewsWordsEntity toNewsKeyWordsEntity(Integer id, List<String> keyWords)
    {
        NewsWordsEntity newsKeyWordsEntity = PersisTools.newsKeyWordsEntity.clone();
        newsKeyWordsEntity.setNewsId(id);
        newsKeyWordsEntity.setKeyWords(listToStr(keyWords));
        return newsKeyWordsEntity;

    }

    public static NewsWordsEntity toNewsKeyWordsEntity(Integer id, String keyWords)
    {
        NewsWordsEntity newsKeyWordsEntity = PersisTools.newsKeyWordsEntity.clone();
        newsKeyWordsEntity.setNewsId(id);
        newsKeyWordsEntity.setKeyWords(keyWords);
        return newsKeyWordsEntity;

    }

    public static String listToStr(List<String> keyWords)
    {
        StringBuffer sb = new StringBuffer();
        for (String keyWord : keyWords)
        {
            sb.append(keyWord);
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
