package com.example.pwcdemorouting.controller

import com.example.pwcdemorouting.RouteResponse
import com.example.pwcdemorouting.service.RoutingProvider
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(RoutingController::class.java)

    @GetMapping("/{origin}/{destination}")
    fun getRoute(
        @PathVariable origin: String,
        @PathVariable destination: String?
    ): ResponseEntity<RouteResponse> {
        logger.info("API called: GET /routing/{}/{}", origin, destination)
        val foundRoute = routingProvider.findRoute(origin, destination)

        if (isRouteNotFound(foundRoute)) {
            return ResponseEntity.badRequest().build()
        }

        logger.info("Route found from {} to {}: {}", origin, destination, foundRoute)
        return ResponseEntity.ok(RouteResponse(foundRoute))
    }

    private fun isRouteNotFound(route: MutableList<String?>?): Boolean = route == null
}