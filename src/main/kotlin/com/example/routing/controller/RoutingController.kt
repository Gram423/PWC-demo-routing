package com.example.routing.controller

import com.example.routing.RouteResponse
import com.example.routing.service.RoutingProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author martingraca on 19.02.2026
 */
@RestController
@RequestMapping("/routing")
class RoutingController @Autowired constructor(private val routingProvider: RoutingProvider) {

    @GetMapping("/{origin}/{destination}")
    fun getRoute(
        @PathVariable origin: String,
        @PathVariable destination: String?
    ): ResponseEntity<RouteResponse> {
        val foundRoute = routingProvider.findRoute(origin, destination)

        if (isRouteNotFound(foundRoute)) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(RouteResponse(foundRoute))
    }

    private fun isRouteNotFound(route: MutableList<String?>?): Boolean = route == null
}