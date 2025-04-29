import chain.CEOHandler
import chain.ExecutiveHandler
import chain.HappyWorkerHandler
import chain.ManagerHandler
import factory.EliteFactory
import factory.FactoryProducer
import factory.HappyWorkerFactory

fun main(args: Array<String>) {

    var fp = FactoryProducer()
    var ef = EliteFactory()
    var hwf = HappyWorkerFactory()

    var ch1 = CEOHandler()
    var ch2 = CEOHandler()
    var eh1 = ExecutiveHandler()
    var eh2 = ExecutiveHandler()
    var mh1 = ManagerHandler()
    var mh2 = ManagerHandler()
    var hwh1 = HappyWorkerHandler()
    var hwh2 = HappyWorkerHandler()
    


}