package sample;

import java.sql.SQLException;

public class Sample2Exec {
    Job stage1;
    Job lib1;
    Job stage2;
    Job lib2;

    public Sample2Exec(Long id, Long fac, Long type) {
        stage1 = new Sample2ExecStage1();
    }

    public void prepare(Long arg1, String arg2) throws SQLException {
        stage1.prepare();
        lib1.prepare();
        stage2.prepare();
        lib2.prepare();
    }

    public void execute() throws SQLException {
        stage1.execute();
        lib1.execute();
        stage2.execute();
        lib2.execute();
    }

    public void revert() {
    }
}
