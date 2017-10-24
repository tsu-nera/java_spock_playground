package sample;

import lombok.Getter;
import lombok.Setter;

public class SampleCalcJob implements Job {
    private @Setter Calculator instanceA;
    private @Setter Calculator instanceB;
    private @Setter Calculator instanceC;

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
        }
    }

    @Override
    public void execute() {
        resultA = instanceA.add(1,2);
        resultB = instanceB.add(3,4);
        resultC = instanceC.add(5,6);
    }

    @Override
    public void revert() {
    }
}
