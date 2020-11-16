package com.zk.trackshows.repository.network

import com.zk.trackshows.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val KEY = BuildConfig.API_KEY

class AuthInterceptor : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response =
		chain.proceed(
			chain.request().newBuilder()
				.url(
					chain.request().url.newBuilder()
						.addQueryParameter("api_key", KEY)
						.build()
				)
				.build()
		)
}
