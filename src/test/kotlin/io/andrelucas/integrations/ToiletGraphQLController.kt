package io.andrelucas.integrations

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*

class ToiletGraphQLController : FunSpec({
    test("should return a list of toilets near a given geolocation") {
        testApplication {
            client.post("/graphql") {
                setBody("""
                    {
                        "query": "query { toiletsNearby(currentCoordination: { latitude: 1.0, longitude: 1.0 }) { name } }"
                    }
                    """.trimIndent())
            }.apply {
                status shouldBe HttpStatusCode.OK
                println(bodyAsText())
                bodyAsText() shouldBe """
                    {
                      "data" : {
                        "toiletsNearby" : [ {
                          "name" : "Toilet 1"
                        }, {
                          "name" : "Toilet 2"
                        }, {
                          "name" : "Toilet 3"
                        } ]
                      }
                    }
                    """.trimIndent()
            }
        }
    }
    test("should return a list of toilets nearly a given geolocation") {
        testApplication {
            client.post("/graphql") {
                setBody("""
                    {
                        "query": "query { toiletsNearby(currentCoordination: { latitude: 1.0, longitude: 1.0 }) { name } }"
                    }
                    """.trimIndent())
            }.apply {
                status shouldBe HttpStatusCode.OK
                println(bodyAsText())
                bodyAsText() shouldBe """
                    {
                      "data" : {
                        "toiletsNearby" : [ {
                          "name" : "Toilet 1"
                        }, {
                          "name" : "Toilet 2"
                        }, {
                          "name" : "Toilet 3"
                        } ]
                      }
                    }
                    """.trimIndent()
            }
        }
    }
})