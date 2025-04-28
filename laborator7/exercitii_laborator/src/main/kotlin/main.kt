import chain.CEOHandler
import chain.ExecutiveHandler
import chain.HappyWorkerHandler
import chain.ManagerHandler
import factory.EliteFactory
import factory.FactoryProducer
import factory.HappyWorkerFactory

fun main(args: Array<String>) {
    TODO()
    var fp = FactoryProducer()
    var ef = EliteFactory()
    var hwf = HappyWorkerFactory()
// se creeaza 1xFactoryProducer, 1xEliteFactory,
   // 1xHappyWorkerFactory
//...
    var ch1 = CEOHandler()
    var ch2 = CEOHandler()
    var eh1 = ExecutiveHandler()
    var eh2 = ExecutiveHandler()
    var mh1 = ManagerHandler()
    var mh2 = ManagerHandler()
    var hwh1 = HappyWorkerHandler()
    var hwh2 = HappyWorkerHandler()
    

// crearea instantelor (prin intermediul celor 2 fabrici):
// 2xCEOHandler, 2xExecutiveHandler, 2xManagerHandler,
         //   2xHappyWorkerHandler
//...
// se construieste lantul (se verifica intai diagrama de obiecte
           // si se realizeaza legaturile)
//...
// se executa lantul utilizand atat mesaje de prioritate diferita,
    //cat si directii diferite in lant
//...
}