package sample;

public class SampleJob implements Job {
    @Override
    public void prepare() {
        System.out.println("prepare called");
    }

    @Override
    public void execute() {
        System.out.println("execute called");
    }

    @Override
    public void revert() {
        System.out.println("revert called");
    }
}
