package com.meteorology.weatherforecast

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class StubBuilder {


    companion object {
        private const val CONTENT_TYPE = "Content-Type"

        fun stubFor2XX(
            param: String,
            pathUrl: String,
            response: String,
            statusCode: Int
        ): StubMapping? {
            return WireMock.stubFor(
                WireMock.get(
                    WireMock.urlPathMatching(pathUrl)
                ).withQueryParam(
                    "city_name", WireMock.containing(param)
                )
                    .willReturn(
                        WireMock.aResponse()
                            .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                            .withBody(response)
                            .withStatus(statusCode)
                    )
            )
        }

        fun stubForWithError5XX(
            param: String,
            pathUrl: String,
            statusCode: Int
        ): StubMapping? {
            return WireMock.stubFor(
                WireMock.get(
                    WireMock.urlPathMatching(pathUrl)
                ).withQueryParam(
                    "city_name", WireMock.containing(param)
                ).willReturn(
                    WireMock.aResponse().withStatus(statusCode)
                )
            )
        }

        fun stubForWithError4XX(
            param: String,
            pathUrl: String,
            statusCode: Int
        ): StubMapping? {
            return WireMock.stubFor(
                WireMock.get(
                    WireMock.urlPathMatching(pathUrl)
                ).withQueryParam(
                    "city_name", WireMock.containing(param)
                ).willReturn(
                    WireMock.aResponse().withStatus(statusCode)
                )
            )
        }
    }
}