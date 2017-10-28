package spock

import sample.Calculator
import sample.Job
import sample.SampleCalcJob
import spock.lang.Specification

class SampleCalcJobSpec extends Specification {
    SampleCalcJob job

    def setup() {
        job = new SampleCalcJob()
    }

    def "test mode off"() {
        when:
        job.prepare()
        job.execute()

        then:
        job.getResultA() == 3
        job.getResultB() == 7
        job.getResultC() == 11
    }

    def "test mode on"() {
        setup:
        job.setTestMode(true)

        def mockA = Mock(Calculator)
        def mockB = Mock(Calculator)
        def mockC = Mock(Calculator)
        def mockJob = Mock(Job)

        mockA.add(1,2) >> 1
        mockB.add(3,4) >> 2
        mockC.add(5,6) >> 3

        job.setInstanceA(mockA)
        job.setInstanceB(mockB)
        job.setInstanceC(mockC)
        job.setInstanceJob(mockJob)

        when:
        job.prepare()
        job.execute()

        then:
        job.getResultA() == 1
        job.getResultB() == 2
        job.getResultC() == 3

        1 * mockJob.prepare()
        1 * mockJob.execute()
    }

}
