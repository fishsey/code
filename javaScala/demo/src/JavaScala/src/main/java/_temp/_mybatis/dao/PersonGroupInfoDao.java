package _temp._mybatis.dao;

import _temp._mybatis.entity.PersonGroupInfoEntity;

import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by root on 7/25/17.
 */
public interface PersonGroupInfoDao
{
    void  insertPersonGroups(List<PersonGroupInfoEntity> entitys);

    void  insertPersnGroup(PersonGroupInfoEntity entity);
}
