package Movo.demo.Security

import org.springframework.stereotype.Component
import java.io.File
import java.util.concurrent.ConcurrentHashMap

@Component
class FileTokenStorage {
    private val storage = ConcurrentHashMap<String, String>()
    private val file = File("refresh_tokens.txt")

    init {
        if (file.exists()) {
            file.readLines().forEach {
                val (id, token) = it.split(";", limit = 2)
                storage[id] = token
            }
        }
    }

    fun save(id: String, token: String) {
        storage[id] = token
        persist()
    }

    fun loadAll(): List<Pair<String, String>> {
        return storage.entries.map { it.toPair() }
    }

    fun remove(id: String) {
        storage.remove(id)
        persist()
    }

    private fun persist() {
        file.printWriter().use { out ->
            storage.forEach { (id, token) ->
                out.println("$id;$token")
            }
        }
    }
}
