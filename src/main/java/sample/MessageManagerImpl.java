package sample;

public class MessageManagerImpl implements MessageManager {
    @Override
    public void send(String msg) {
        System.out.println(msg);
    }

    @Override
    public int send2(String msg) {
        System.out.println(msg);
        return 0;
    }
}
