import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking {
    val queue = Channel<Int>(capacity = 4)

    // Populăm coada cu 4 valori diferite
    val values = listOf(10, 100, 250, 1000)
    values.forEach { queue.send(it) }
    queue.close()

    // Lansăm 4 corutine pentru a prelua câte un n și a calcula suma
    repeat(4) { index ->
        launch {
            val n = queue.receive()
            val sum = (0..n).sum()
            println("Corutina $index: suma pentru n=$n este $sum")
        }
    }
}
