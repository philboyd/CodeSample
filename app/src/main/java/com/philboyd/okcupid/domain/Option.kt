package com.philboyd.okcupid.domain

sealed class Option<out A : Any> {

    object None : Option<Nothing>()
    data class Some<out A : Any>(val value: A) : Option<A>()

    inline fun <B : Any> map(mapper: (A) -> B): Option<B> = flatMap { Some(mapper(it)) }

    inline fun <B : Any> flatMap(mapper: (A) -> Option<B>): Option<B> = when (this) {
        is None -> this
        is Some -> mapper(value)
    }

    inline fun filter(predicate: (A) -> Boolean): Option<A> =
        flatMap { if (predicate(it)) Some(it) else None }

    fun isSome(): Boolean = this is Some

    fun isNone(): Boolean = this is None
}

fun <A : Any> A?.toSome(): Option.Some<A>? = if (this == null) null else Option.Some(this)

fun <A : Any> A?.toOption(): Option<A> = if (this == null) Option.None else Option.Some(this)

fun <A : Any> List<A>.toOption(): Option<List<A>> = if (isNotEmpty()) Option.Some(this) else Option.None

fun <A : Any> Set<A>.toOption(): Option<Set<A>> = if (isNotEmpty()) Option.Some(this) else Option.None

fun <A : Any> Option<A>.withDefault(default: A): A = when (this) {
    is Option.None -> default
    is Option.Some -> value
}

fun <A : Any> Option<A>.orNull(): A? = when (this) {
    is Option.None -> null
    is Option.Some -> value
}
