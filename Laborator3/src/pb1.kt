import org.jsoup.Jsoup

data class RSSFeed(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String?,
    val items: List<FeedItem>
)

data class FeedItem(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String?
)

fun main() {
    val rssUrl = "http://rss.cnn.com/rss/edition.rss"

    try {
        // Conectare și parsare RSS cu user agent
        val doc = Jsoup.connect(rssUrl)
            .userAgent("Mozilla/5.0")
            .get()

        // Extragere informații canal
        val channel = doc.selectFirst("channel") ?: throw Exception("Canal negăsit")

        val feed = RSSFeed(
            title = channel.selectFirst("title")?.text() ?: "",
            link = channel.selectFirst("link")?.text() ?: "",
            description = channel.selectFirst("description")?.text() ?: "",
            pubDate = channel.selectFirst("pubDate")?.text(),
            items = channel.select("item").map { item ->
                FeedItem(
                    title = item.selectFirst("title")?.text() ?: "",
                    link = item.selectFirst("link")?.text() ?: "",
                    description = item.selectFirst("description")?.text() ?: "",
                    pubDate = item.selectFirst("pubDate")?.text()
                )
            }
        )

        // Afișare rezultate
        println("Feed: ${feed.title}\n")
        feed.items.forEachIndexed { index, item ->
            println("Item ${index + 1}:")
            println("Titlu: ${item.title}")
            println("Link: ${item.link}\n")
        }
    } catch (e: Exception) {
        println("Eroare: ${e.message}")
    }
}