package sample;

import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

public class SampleCalcJob implements Job {
    private @Setter Calculator instanceA;
    private @Setter Calculator instanceB;
    private @Setter Calculator instanceC;
    private @Setter Job instanceJob;

    private @Getter int resultA;
    private @Getter int resultB;
    private @Getter int resultC;

    private @Setter boolean testMode;

    public SampleCalcJob() {
        testMode = false;
    }

    @Override
    public void prepare() {
        if (!testMode) {
            instanceA = new Calculator();
            instanceB = new Calculator();
            instanceC = new Calculator();
            instanceJob = new SampleJob();
        }
    }

    @Override
    public void execute() throws SQLException {
        resultA = instanceA.add(1,2);
        resultB = instanceB.add(3,4);
        resultC = instanceC.add(5,6);

        instanceJob.prepare();
        instanceJob.execute();
    }

    @Override
    public void revert() {
    }
}
