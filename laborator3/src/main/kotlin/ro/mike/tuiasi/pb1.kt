import org.jsoup.Jsoup
import java.io.IOException

// ADT pentru un item RSS
data class RSSItem(
    var title: String = "",
    var link: String = "",
    var description: String = "",
    var pubDate: String = ""
)

// ADT pentru întregul feed RSS
class RSSFeed {
    var title: String = ""
    var link: String = ""
    var description: String = ""
    var pubDate: String = ""
    val items = mutableListOf<RSSItem>()


    fun addItem(item: RSSItem) {
        items.add(item)
    }
}

fun main() {
    val rssUrl = "http://rss.cnn.com/rss/edition_world.rss" // sau alt feed
    val feed = RSSFeed()

    try {
        val doc = Jsoup.connect(rssUrl).get()

        feed.title = doc.select("channel > title").first()?.text() ?: ""
        feed.link = doc.select("channel > link").first()?.text() ?: ""
        feed.description = doc.select("channel > description").first()?.text() ?: ""

        doc.select("item").forEach { element ->
            val item = RSSItem().apply {
                title = element.select("title").text()
                link = element.select("link").text()
                description = element.select("description").text()
                pubDate = element.select("pubDate").text()
            }
            feed.addItem(item)
        }

        feed.items.forEach {
            println("Title: ${it.title}")
            println("Link: ${it.link}")
            println("---------------------")
        }

    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: Exception) {
        println("Eroare neașteptată: ${e.message}")
    }
}