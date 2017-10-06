package _temp._mybatis.dao;

import _temp._mybatis.entity.PersonEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by root on 7/25/17.
 */

public interface PersonDao
{
    void insertPersons(List<PersonEntity> personEntitys);

    void insertPerson(PersonEntity personEntity);

    void updateNameById(@Param("name") String name, @Param("id") int id);

}
