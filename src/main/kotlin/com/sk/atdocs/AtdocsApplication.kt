package com.sk.atdocs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

//@EnableJpaAuditing
@SpringBootApplication
class AtdocsApplication

fun main(args: Array<String>) {

	runApplication<AtdocsApplication>(*args)
}
