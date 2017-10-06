package _temp._mahout.basic;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.IOException;

/**
 * Created by fishsey on 2016/10/8.
 */
public class JdbcDataModel
{
    public static void main(String args[]) throws TasteException, IOException
    {
        //dataSource
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("10.1.15.111");
        dataSource.setUser("fishsey");
        dataSource.setPassword("0114");
        dataSource.setDatabaseName("mahout");

        //paras for dataSource
        dataSource.setCachePreparedStatements(true);
        dataSource.setCachePrepStmts(true);
        dataSource.setCacheResultSetMetadata(true);
        dataSource.setAlwaysSendSetIsolation(false);
        dataSource.setElideSetAutoCommits(true);


        DataModel data = new MySQLJDBCDataModel(dataSource, "slopeone", "userid", "itemId", "prefs", null);
        

    }

}
