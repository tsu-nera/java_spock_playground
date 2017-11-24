package sample;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;

import static org.junit.Assert.*;

public class Sample2ExecStage1Test {
    private Sample2ExecStage1 sample2ExecStage1;
    private static IDatabaseTester databaseTester;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester("org.postgresql.Driver",
                "jdbc:postgresql://localhost:5434/sampleDB",
                "postgres",
                "postgres",
                "public");
        sample2ExecStage1 = new Sample2ExecStage1();
        sample2ExecStage1.setId(1L);
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);
        databaseTester.onTearDown();
        System.out.println(testName.getMethodName());
    }

    @Test
    public void node2() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore2Node.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertFalse(sample2ExecStage1.isResult());
    }

    @Test
    public void node2PairDown() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore2NodePairDown.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertTrue(sample2ExecStage1.isResult());
    }

    @Test
    public void node2FlagOn() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore2NodeFlagOn.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertTrue(sample2ExecStage1.isResult());
    }

    @Test
    public void node3AllNormal() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore3NodeNormal.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertFalse(sample2ExecStage1.isResult());
    }

    @Test
    public void node3All2FaultNotMaster() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore3Node2FaultNotMaster.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertFalse(sample2ExecStage1.isResult());
    }

    @Test
    public void node3All2FaultMaster() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore3Node2FaultMaster.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertTrue(sample2ExecStage1.isResult());
    }

    @Test
    public void node4AllNormal() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore4NodeNormal.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertFalse(sample2ExecStage1.isResult());
    }

    @Test
    public void node4All2Fault() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore4Node2Fault.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertFalse(sample2ExecStage1.isResult());
    }

    @Test
    public void node4All2FaultNG() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/db/dbunit/PairBefore4Node2FaultMaster.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

        sample2ExecStage1.prepare();
        sample2ExecStage1.execute();
        assertTrue(sample2ExecStage1.isResult());
    }
}
