package com.gandor.mobile_locator.layers.data.managers

object GlobalErrorManager: Thread.UncaughtExceptionHandler {
    override fun uncaughtException(p0: Thread, p1: Throwable) {
//        when(p1) {
//            is ComposableException -> {
//                p1.showDialog()
//                println("GEO TEST | Caught!!!")
//                println("GEO TEST | p0.isAlive: ${p0.isAlive}")
//            }
//        }
    }
}