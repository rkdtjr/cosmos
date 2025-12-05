package kr.ac.kumoh.s20220744.cosmos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class CosmosApplication

fun main(args: Array<String>) {
	runApplication<CosmosApplication>(*args)
}
