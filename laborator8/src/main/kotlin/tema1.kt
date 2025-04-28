

interface LogicGate {
    fun computeOutput(): Boolean
}

interface LogicGateImplementation {
    fun evaluate(inputs: List<Boolean>): Boolean
}

class ANDGateImpl : LogicGateImplementation {
    override fun evaluate(inputs: List<Boolean>): Boolean {
        return inputs.all { it }
    }
}

class ConcreteLogicGate(
    private val implementation: LogicGateImplementation,
    private val inputs: List<Boolean>
) : LogicGate {
    override fun computeOutput(): Boolean {
        val fsm = StateMachine(inputs)
        return implementation.evaluate(fsm.getStableInputs())
    }
}



interface GateBuilder {
    fun addInput(input: Boolean): GateBuilder
    fun build(): LogicGate
}

class ANDGateBuilder(private val implementation: LogicGateImplementation) : GateBuilder {
    private val inputs = mutableListOf<Boolean>()

    override fun addInput(input: Boolean): GateBuilder {
        inputs.add(input)
        return this
    }

    override fun build(): LogicGate {
        return ConcreteLogicGate(implementation, inputs)
    }
}

class GateDirector(private val builder: GateBuilder) {
    fun construct(inputs: List<Boolean>): LogicGate {
        inputs.forEach { builder.addInput(it) }
        return builder.build()
    }
}



class StateMachine(private val rawInputs: List<Boolean>) {
    enum class State {
        START, CHECKING, STABLE
    }

    private var state = State.START
    private val stableInputs = mutableListOf<Boolean>()

    fun getStableInputs(): List<Boolean> {
        for (input in rawInputs) {
            when (state) {
                State.START -> {
                    state = State.CHECKING
                    stableInputs.add(input)
                }
                State.CHECKING -> {

                    state = State.STABLE
                    stableInputs.add(input)
                }
                State.STABLE -> {
                    stableInputs.add(input)
                }
            }
        }
        return stableInputs
    }
}


fun main() {
    val inputSets = listOf(
        listOf(true, true),
        listOf(true, true, false),
        listOf(true, true, true, true),
        listOf(true, true, true, true, true, true, true, false)
    )

    inputSets.forEachIndexed { index, inputs ->
        val builder = ANDGateBuilder(ANDGateImpl())
        val director = GateDirector(builder)
        val gate = director.construct(inputs)
        println("Rezultatul porții AND cu ${inputs.size} intrări (${inputs.joinToString()}): ${gate.computeOutput()}")
    }
}
