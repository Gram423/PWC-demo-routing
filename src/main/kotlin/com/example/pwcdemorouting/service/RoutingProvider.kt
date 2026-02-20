package com.example.pwcdemorouting.service

import com.example.pwcdemorouting.model.Country
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class RoutingProvider @Autowired constructor(
    private val restTemplate: RestTemplate
) {

    private val objectMapper = ObjectMapper()

    private val countryBorders: MutableMap<String?, MutableList<String?>?> = HashMap()

    companion object {
        private const val COUNTRIES_URL = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json"
    }

    @PostConstruct
    fun loadCountriesData() {
        try {
            val jsonData: String? = restTemplate.getForObject(COUNTRIES_URL, String::class.java)
            val countries = objectMapper.readValue(
                jsonData,
                object : TypeReference<MutableList<Country>>() {}
            )

            for (country in countries) {
                countryBorders.put(
                    country.cca3,
                    if (country.borders != null) country.borders else ArrayList<String?>()
                )
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to load countries data", e)
        }
    }

    fun findRoute(origin: String, destination: String?): MutableList<String?>? {
        if (!countryBorders.containsKey(origin) || !countryBorders.containsKey(destination)) {
            return noCountryFound()
        }

        if (origin == destination) {
            return mutableListOf(origin)
        }

        // BFS to find shortest path
        val queue: Queue<String> = LinkedList()
        val parent: MutableMap<String?, String?> = HashMap<String?, String?>()
        val visited: MutableSet<String?> = HashSet<String?>()

        queue.offer(origin)
        visited.add(origin)
        parent.put(origin, null)

        while (!queue.isEmpty()) {
            val current = queue.poll()

            if (current == destination) {
                // Reconstruct path
                val path: MutableList<String?> = ArrayList<String?>()
                var node: String? = destination
                while (node != null) {
                    path.add(0, node)
                    node = parent.get(node)
                }
                return path
            }

            val neighbors = countryBorders.get(current)
            if (neighbors != null) {
                for (neighbor in neighbors) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor)
                        parent.put(neighbor, current)
                        queue.offer(neighbor)
                    }
                }
            }
        }

        return noPathFound()
    }

    private fun noPathFound(): Nothing? = null
    private fun noCountryFound(): Nothing? = null

}