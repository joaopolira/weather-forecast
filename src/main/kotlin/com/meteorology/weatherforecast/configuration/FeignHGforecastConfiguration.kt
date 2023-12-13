package com.meteorology.weatherforecast.configuration

import com.meteorology.weatherforecast.extension.is4xxHttpStatus
import feign.FeignException
import feign.RetryableException
import feign.Retryer
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean


class FeignHGforecastConfiguration {

    @Bean
    fun errorDecode(): ErrorDecoder {
        return ErrorDecoder { methodKey, response ->
            val exception = FeignException.errorStatus(methodKey, response)

            if (response.is4xxHttpStatus()) {
                return@ErrorDecoder exception
            }

            return@ErrorDecoder RetryableException(
                response.status(),
                exception.message,
                response.request().httpMethod(),
                exception,
                null,
                response.request()
            )
        }
    }

    @Bean
    fun retryerDefault(): Retryer.Default {
        return Retryer.Default(100L, 500L, 3)
    }
}
