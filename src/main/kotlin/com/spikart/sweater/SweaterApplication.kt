package com.spikart.sweater

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SweaterApplication

fun main(args: Array<String>) {
	runApplication<SweaterApplication>(*args)
}
