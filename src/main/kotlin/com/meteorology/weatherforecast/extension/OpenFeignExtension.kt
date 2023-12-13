package com.meteorology.weatherforecast.extension

import feign.Response

fun Response.is4xxHttpStatus(): Boolean {
    return this.status() in 400..499
}