package sample;

public class MockSample {
    private MessageManager mgr;

    public void setMgr(MessageManager mgr) {
        this.mgr = mgr;
    }

    public MockSample() {
        mgr = new MessageManagerImpl();
    }

    public void sendMsg(String msg) {
        mgr.send(msg);
    }

    public int sendMsg2(String msg) {
        return mgr.send2(msg);
    }

    public void sendMsg3(String msg) {
        MessageManager mgr2 = new MessageManagerImpl();
       mgr2.send(msg);
    }

    public static int sendMsg4(String msg) {
        return MessageManagerStatic.send(msg);
    }

    private void sendMsg5(String msg) {
        mgr.send(msg);
    }
}
