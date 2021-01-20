package com.philboyd.okcupid.domain.core

sealed class RemoteError : Exception()

object SyncError : RemoteError()
data class HttpError(val code: Int, override val message: String?) : RemoteError()
data class ParsingError(val error: Throwable) : RemoteError()
