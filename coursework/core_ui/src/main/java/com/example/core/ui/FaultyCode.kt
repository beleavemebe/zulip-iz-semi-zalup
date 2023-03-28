package com.example.core.ui

import kotlin.random.Random

fun throwRandomly() = if (Random.nextInt(100) > 70) error("Oopsy!") else Unit
