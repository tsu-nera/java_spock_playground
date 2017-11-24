package sample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MockSample.class, MessageManagerStatic.class})
public class MockSampleTest {
    MockSample sample;

    @Before
    public void setup(){
        sample = new MockSample();
    }

    @Test
    public void 呼び出し引数をチェック() {
        MessageManager spy = PowerMockito.spy(new MessageManagerImpl());

        sample.setMgr(spy);
        sample.sendMsg("Hello");
        sample.sendMsg("Hello");

        Mockito.verify(spy, times(2)).send("Hello");
    }

    @Test
    public void 戻り値を返す() {
        MessageManager spy = PowerMockito.spy(new MessageManagerImpl());
        when(spy.send2("Hello")).thenReturn(1);
        sample.setMgr(spy);

        int ret = sample.sendMsg2("Hello");

        Mockito.verify(spy, times(1)).send2("Hello");
        assertThat(1, is(ret));
    }

    @Test
    public void メソッド内で生成されるインスタンスをモックに置き換える() throws Exception {
        // https://github.com/powermock/powermock/wiki/MockConstructor
        MessageManagerImpl mock = PowerMockito.mock(MessageManagerImpl.class);
        PowerMockito.whenNew(MessageManagerImpl.class).withNoArguments().thenReturn(mock);

        sample.sendMsg3("Hello");

        Mockito.verify(mock).send("Hello");
    }

    @Test
    public void staticなメソッドをテストする() throws Exception {
        // https://github.com/powermock/powermock/wiki/Mockito#mocking-static-method
        PowerMockito.mockStatic(MessageManagerStatic.class);
        Mockito.when(MessageManagerStatic.send(anyObject())).thenReturn(1);
        PowerMockito.doNothing().when(MessageManagerStatic.class, "send2", anyString());

        int ret = sample.sendMsg4("Hello");

        assertThat(1, is(ret));
    }

    @Test
    public void privateなメソッドをテストする() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockSample sample = new MockSample();

        Method method = MockSample.class.getDeclaredMethod("sendMsg5", String.class);
        method.setAccessible(true);

        method.invoke(sample, "Hello");
    }

    @Test
    public void 例外が発生しないことを確認する() {
        try {
            sample.sendMsg("Hello");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void 例外が発生したこと確認する() throws Exception {
        MessageManagerImpl mock = PowerMockito.mock(MessageManagerImpl.class);
        PowerMockito.doThrow(new IllegalStateException()).when(mock).send("Hello");
        PowerMockito.whenNew(MessageManagerImpl.class).withNoArguments().thenReturn(mock);

        sample.sendMsg3("Hello");
    }
}
