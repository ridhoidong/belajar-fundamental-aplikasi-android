package com.application.idong.aplikasigithubuser3.api

import android.util.Log
import com.application.idong.aplikasigithubuser3.BuildConfig
import okhttp3.*

/**
 * Created by ridhopratama on 17,October,2020
 */

class InterceptorApi : Interceptor, Authenticator {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request?.newBuilder()
                        ?.addHeader("Content-Type", "application/json")
                        ?.addHeader("Authorization", BuildConfig.TOKEN_GITHUB)
                        ?.build()

        return chain.proceed(request)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request.newBuilder()
            .header("Authorization", BuildConfig.TOKEN_GITHUB)
            .build()
    }
}