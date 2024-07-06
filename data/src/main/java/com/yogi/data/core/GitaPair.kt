package com.yogi.data.core

import java.io.Serializable

class GitaPair<A, B> constructor(val first: A, val second: B) : Serializable {
    override fun toString(): String = "$first, $second"
}