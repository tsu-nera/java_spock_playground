import sample.MessageManager
import sample.MockSample
import spock.lang.Specification

class MockSampleSpec extends Specification {

    def "呼び出し引数をチェック(Mocking)"() {
        setup:
        def sample = new MockSample()
        def mgr = Mock(MessageManager)
        sample.setManager(mgr)

        when:
        sample.sendMsg("hello")

        then:
        1 * mgr.send("hello")
    }

    def "戻り値を返す(Stubbing)" () {
        setup:
        def sample = new MockSample()
        def mgr = Mock(MessageManager)
        mgr.send2(_) >> 1
        sample.setManager(mgr)

        expect:
        sample.sendMsg2("hello") == 1
    }

    def "例外が発生しないことを確認する" () {
        setup:
        def sample = new MockSample()
        def mgr = Mock(MessageManager)
        sample.setManager(mgr)

        when:
        sample.sendMsg("hello")

        then:
        noExceptionThrown()
    }

    def "例外が発生したを確認する" () {
        setup:
        def sample = new MockSample()
        def mgr = Mock(MessageManager)
        mgr.send(_) >> {throw new IllegalArgumentException()}
        sample.setManager(mgr)

        when:
        sample.sendMsg("hoge")

        then:
        thrown(IllegalArgumentException)
    }
}
