package com.spikart.sweater

import com.spikart.sweater.domain.Role
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SweaterApplicationTests {

	@Test
	fun contextLoads() {

		val roles = Role.values().toSet()
		val newRoles = listOf("ADMIN", "USER")

		val selected = roles.filter {role: Role ->
			val predicate = role.name in newRoles
			predicate
		}
		assert(selected.size == 2)
	}

}
