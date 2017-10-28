import sample.DbAccessor
import sample.Pair
import spock.lang.Specification


class PairSpec extends Specification {
    Pair pair;

    def setup() {
        pair = new Pair();
    }

    def "get pair"() {
        expect:
        pair.getPairId(a) == b

        where:
        a | b
        1 | 2
        2 | 1
        3 | 4
        4 | 3
    }

    def "md1" () {
        setup:
        DbAccessor accessor = Mock()
        accessor.getStatus(_) >> "Normal"
        pair.setAccessor(accessor)

        expect:
        pair.isMD(1L, 2L) == Boolean.FALSE
    }

    def "md2" () {
        setup:
        DbAccessor accessor = Mock()
        accessor.getStatus(_) >> "Fault"
        pair.setAccessor(accessor)

        expect:
        pair.isMD(1L, 2L) == Boolean.TRUE
    }

    def "md3" () {
        setup:
        DbAccessor accessor = Mock()
        accessor.getStatus(1L) >> "Fault"
        accessor.getStatus(4L) >> "Fault"
        accessor.getStatus(5L) >> "Normal"
        accessor.getStatus(8L) >> "Normal"
        pair.setAccessor(accessor)

        expect:
        pair.isMD(2L, 4L) == Boolean.FALSE
    }

    def "md4" () {
        setup:
        DbAccessor accessor = Mock()
        accessor.getStatus(1L) >> "Fault"
        accessor.getStatus(4L) >> "Fault"
        accessor.getStatus(5L) >> "Normal"
        accessor.getStatus(8L) >> "Normal"
        pair.setAccessor(accessor)

        expect:
        pair.isMD(8L, 4L) == Boolean.TRUE
    }

    def "md5" () {
        setup:
        DbAccessor accessor = Mock()
        accessor.getStatus(1L) >> "Fault"
        accessor.getStatus(4L) >> "Normal"
        accessor.getStatus(5L) >> "Normal"
        accessor.getStatus(8L) >> "Fault"
        pair.setAccessor(accessor)

        expect:
        pair.isMD(4L, 4L) == Boolean.TRUE
    }

    def "md6" () {
        setup:
        DbAccessor accessor = Mock()
        accessor.getStatus(1L) >> "Normal"
        accessor.getStatus(4L) >> "Normal"
        accessor.getStatus(5L) >> "Normal"
        accessor.getStatus(8L) >> "Fault"
        pair.setAccessor(accessor)

        expect:
        pair.isMD(4L, 4L) == Boolean.FALSE
    }
}