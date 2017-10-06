package com.fishsey.bookRecys.recysAndSearch.impl.nlk;

import org.lionsoul.jcseg.tokenizer.core.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by root on 8/2/17.
 */
@Component
//@Scope("prototype")
public class AnaylyzerImpl
{
    private String configFile = "/root/AAA/code/idea/demos/bookRecys/src/main/resources/jcseg.properties";
    private  int pattern = JcsegTaskConfig.COMPLEX_MODE;

    public  ArrayList<String> anaylyzerWords(Reader strReader)
    {
        JcsegTaskConfig config = new JcsegTaskConfig(configFile);
        ADictionary dic = DictionaryFactory.createDefaultDictionary(config);

        ArrayList<String> list = new ArrayList<String>();
        try
        {
            ISegment seg = SegmentFactory.createJcseg(pattern, new Object[]{config, dic});

            seg.reset(strReader);

            IWord word = null;
            while ((word = seg.next()) != null)
            {
                String w = new String(word.getValue());
                w.intern();
                if (w.length() > 1)
                {
                    list.add(w);
                }
            }
        } catch (JcsegException e1)
        {
            e1.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public  ArrayList<String> anaylyzerWords(String... str)
    {
        StringBuilder sb = new StringBuilder();
        for (String s : str)
        {
            sb.append(s);
            sb.append(" ");
        }

        StringReader reader = null;
        try
        {
            reader = new StringReader(sb.toString());
            return anaylyzerWords(reader);
        } finally
        {
            reader.close();
        }
    }

}
