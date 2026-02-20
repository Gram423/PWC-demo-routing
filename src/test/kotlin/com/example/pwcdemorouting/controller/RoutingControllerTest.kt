package com.example.pwcdemorouting.controller


import com.example.pwcdemorouting.service.RoutingProvider
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest(RoutingController::class)
@ContextConfiguration(classes = [RoutingController::class])
class RoutingControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var routingProvider: RoutingProvider

    @Test
    @Throws(Exception::class)
    fun `given valid origin and destination when getRoute then should return route`() {
        val origin = "CZE"
        val destination = "ITA"
        val expectedRoute = mutableListOf<String?>("CZE", "AUT", "ITA")

        Mockito.`when`<MutableList<String?>?>(routingProvider.findRoute(origin, destination)).thenReturn(expectedRoute)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/routing/{origin}/{destination}", origin, destination)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route[0]").value("CZE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route[1]").value("AUT"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route[2]").value("ITA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route.length()").value(3))
    }

    @Test
    @Throws(Exception::class)
    fun `given state with no route available when getRoute then should return bad request`() {
        val origin = "USA"
        val destination = "CHN"

        Mockito.`when`<MutableList<String?>?>(routingProvider.findRoute(origin, destination)).thenReturn(null)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/routing/{origin}/{destination}", origin, destination)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }

    @Test
    @Throws(Exception::class)
    fun `given same origin and destination when getRoute then should return route with origin`() {
        val country = "DEU"
        val expectedRoute = mutableListOf<String?>("DEU")

        Mockito.`when`<MutableList<String?>?>(routingProvider.findRoute(country, country)).thenReturn(expectedRoute)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/routing/{origin}/{destination}", country, country)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route[0]").value("DEU"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route.length()").value(1))
    }

    @Test
    @Throws(Exception::class)
    fun `given invalid origin when getRoute then should return bad request`() {
        val origin = "INVALID"
        val destination = "ITA"

        Mockito.`when`<MutableList<String?>?>(
            routingProvider.findRoute(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(null)


        mockMvc.perform(
            MockMvcRequestBuilders.get("/routing/{origin}/{destination}", origin, destination)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }

    @Test
    @Throws(Exception::class)
    fun  `given direct neighbour country should return direct route`() {
        val origin = "DEU"
        val destination = "FRA"
        val expectedRoute = mutableListOf<String?>("DEU", "FRA")

        Mockito.`when`<MutableList<String?>?>(routingProvider.findRoute(origin, destination)).thenReturn(expectedRoute)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/routing/{origin}/{destination}", origin, destination)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route[0]").value("DEU"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route[1]").value("FRA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.route.length()").value(2))
    }
}