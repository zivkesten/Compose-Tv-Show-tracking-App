package com.zk.trackshows.extensions

inline fun <T:Any, R> whenNotNull(input: T?, callback: (T)->R): R? {
  return input?.let(callback)
}
