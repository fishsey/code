package com.fishsey.bookRecys.dao.mybatis.impl;


import com.fishsey.bookRecys.entity.mybatis.NewsEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 6/6/17.
 */
public interface NewsDao extends Serializable
{
    void batchInsert(List<NewsEntity> list);

    List<NewsEntity> selectList(int start, int end);

    NewsEntity select(int id);

}
