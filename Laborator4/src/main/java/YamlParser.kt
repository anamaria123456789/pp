package org.example

class YamlParser : Parser {
    override fun parse(text: String): Map<String, String> {
        val yaml = org.yaml.snakeyaml.Yaml()

        return yaml.load(text) as Map<String, String>
    }
}
