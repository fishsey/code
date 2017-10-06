package com.fishsey.bookRecys.tools.jsonParse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 7/1/17.
 */

@Component
public class JsonReaderImpl
{
    ArrayList<JSONObject> nodes = new ArrayList<>();

    public List<JSONObject> jsonParse(String fileName)
    {
        if (nodes.size() != 0)
            nodes.clear();

        JSONReader jsonReader = null;
        JSONObject page;
        try
        {
            jsonReader = new JSONReader(new FileReader(new File(fileName)));
            jsonReader.startArray();

            while (jsonReader.hasNext())
            {
                page = (JSONObject) jsonReader.readObject();
                nodes.add(page);
            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }finally
        {
            jsonReader.endArray();
            jsonReader.close();
        }

        return this.nodes;
    }

}
