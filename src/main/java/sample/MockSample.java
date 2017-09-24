package sample;

public class MockSample {
    private MessageManager mgr;

    public void setManager(MessageManager mgr) {
        this.mgr = mgr;
    }

    public void sendMsg(String msg) {
        mgr.send(msg);
    }

    public int sendMsg2(String msg) {
        return mgr.send2(msg);
    }
}
