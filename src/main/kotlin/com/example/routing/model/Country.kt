package com.example.routing.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Country {
    @JsonProperty("cca3")
    var cca3: String? = null

    @JsonProperty("borders")
    var borders: MutableList<String?>? = null

    constructor()

    constructor(cca3: String?, borders: MutableList<String?>?) {
        this.cca3 = cca3
        this.borders = borders
    }
}