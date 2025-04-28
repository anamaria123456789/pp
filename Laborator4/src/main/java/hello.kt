package org.example

fun main(args: Array<String>) {
    // JSON
    val crawlerJsonObject = Crawler("https://dog.ceo/api/breeds/image/random")
    val crawlerJsonArray = Crawler("https://jsonplaceholder.typicode.com/posts")

    crawlerJsonArray.processContent("json")
    println()
    crawlerJsonObject.processContent("json")

    // XML
    val crawlerXmlObject = Crawler("https://www.w3schools.com/xml/simple.xml")
    println()
    crawlerXmlObject.processContent("xml")

    // YAML
    val crawlerYamlObject = Crawler("https://api.github.com/repos/octocat/Hello-World")
    println()
    crawlerYamlObject.processContent("yaml")
}
