package com.xotril.vendacar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VendacarApplication

fun main(args: Array<String>) {
	runApplication<VendacarApplication>(*args)
}
