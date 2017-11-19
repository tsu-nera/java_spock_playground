package sample;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Sample2Job.class})
public class Sample2JobTest {
    Sample2Job sample;
    private static IDatabaseTester databaseTester;

    @Before
    public void setUp() throws Exception {
        sample = new Sample2Job();

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
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);
        databaseTester.onTearDown();
    }

    @Test
    public void success() throws Exception {
        sample.setId(2L);

        Sample2Exec mock = PowerMockito.mock(Sample2Exec.class);
        PowerMockito.whenNew(Sample2Exec.class)
                .withArguments(2L, 0L, 0L)
                .thenReturn(mock);

        sample.prepare();
        sample.execute();

        Mockito.verify(mock, Mockito.times(1)).prepare();
        Mockito.verify(mock, Mockito.times(1)).execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidId1() throws Exception {
        sample.prepare();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidId2() throws Exception {
        sample.setId(0L);
        sample.prepare();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidId3() throws Exception {
        sample.setId(17L);
        sample.prepare();
    }
}