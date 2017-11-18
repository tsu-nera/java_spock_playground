package sample;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DbUnitSampleTest {
    private static IDatabaseTester databaseTester;
    private DbUnitSample sample;

    @Before
    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester("org.postgresql.Driver",
                "jdbc:postgresql://localhost:5434/sampleDB",
                "postgres",
                "postgres",
                "public");

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/Before.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample = new DbUnitSample();
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);
        databaseTester.onTearDown();
    }

    @Test
    public void state更新() throws Exception {
        sample.execute();
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/After.xml"));
        ITable expectedTable = expectedDataSet.getTable("HOGE");

        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("HOGE");
        ITable sortedTable = new SortedTable(actualTable, new String[]{"id"});

        Assertion.assertEquals(expectedTable, sortedTable);
    }

    @Test
    public void state更新2() throws Exception {
        sample.execute();
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("HOGE");

        String actualState = "normal";
        for(int i=0; i < actualTable.getRowCount(); i++) {
            if (actualTable.getValue(i, "name").toString().equals("name#3")) {
                actualState = actualTable.getValue(i, "state").toString();
                break;
            }
        }
        assertThat("error", is(actualState));
    }
}
