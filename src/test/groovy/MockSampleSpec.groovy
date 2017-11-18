import sample.Calculator
import sample.MessageManager
import sample.MockSample
import spock.lang.Specification

class MockSampleSpec extends Specification {
    MockSample sample
    MessageManager mgr

    def setup() {
        sample = new MockSample()
        mgr = Mock(MessageManager)
    }

    def "呼び出し引数をチェック(Mocking)"() {
        setup:
        sample.setMgr(mgr);

        when:
        sample.sendMsg("hello")
        sample.sendMsg("hello")

        then:
        2 * mgr.send("hello")
    }

    def "戻り値を返す(Stubbing)" () {
        setup:
        mgr.send2(_) >> 1
        sample.setMgr(mgr)

        expect:
        sample.sendMsg2("hello") == 1
    }

    def "例外が発生しないことを確認する" () {
        setup:
        sample.setMgr(mgr)

        when:
        sample.sendMsg("hello")

        then:
        noExceptionThrown()
    }

    def "例外が発生したことを確認する" () {
        setup:
        mgr.send(_) >> {throw new IllegalArgumentException()}
        sample.setMgr(mgr)

        when:
        sample.sendMsg("hoge")

        then:
        thrown(IllegalArgumentException)
    }

    def "interfaceなしでMockをつくる" () {
        setup:
        def calc = Mock(Calculator)
        calc.add(_, _) >> 4

        expect:
        calc.add(1,2) == 4
    }
}
