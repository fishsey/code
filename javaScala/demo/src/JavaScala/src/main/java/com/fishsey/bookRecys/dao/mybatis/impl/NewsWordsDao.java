package com.fishsey.bookRecys.dao.mybatis.impl;


import com.fishsey.bookRecys.entity.mybatis.NewsWordsEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 6/6/17.
 */
public interface NewsWordsDao extends Serializable
{
    void batchInsertData(List<NewsWordsEntity> lst);

    void batchInsertTest(List<NewsWordsEntity> lst);

    void batchInsertTrain(List<NewsWordsEntity> lst);
}
