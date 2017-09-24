package spock

import sample.Calculator
import spock.lang.Specification

class CalculatorSpec extends Specification {
    def calc

    def setup() {
        calc = new Calculator()
    }

    def cleanup() {
        calc = null
    }

    def '足し算1'() {
        expect:
        calc.add(1,1) == 2
    }

    def '足し算2'() {
        expect:
        calc.add(1,2) == 3
    }

    def "足し算:データ駆動テスト"() {
        expect:
        calc.add(a, b) == c

        where:
        a|b|c
        1|1|2
        2|3|5
        3|4|7
    }
}
