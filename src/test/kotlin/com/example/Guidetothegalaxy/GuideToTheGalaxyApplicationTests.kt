package com.example.Guidetothegalaxy

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import org.junit.jupiter.api.Assertions.assertEquals

@SpringBootTest
class GuideToTheGalaxyApplicationTests {

	val mapOfSymbol = mapOf("I" to 1, "V" to 5, "X" to 10, "L" to 50, "C" to 100, "D" to 500, "M" to 1000)
	val nameToSymbol = mutableMapOf<String, String>()
	val productToCredits = mutableMapOf<String, Float>()
	final val fileName = "/Users/zeyang.li/Documents/Kotlin_test/Guide-to-the-galaxy/src/test/kotlin/com/example/Guidetothegalaxy/beforeEach.txt"
	val lines: MutableList<String> = File(fileName).readLines() as MutableList<String>

	@Test
	fun `how much is symbol`() {
		lines.add("how much is pish tegj glob glob ?")
		val result = Main().parseToResult(lines, productToCredits, nameToSymbol, mapOfSymbol)
		assertEquals("pish tegj glob glob is 42", result[0])
	}

	@Test
	fun `how many Credits`() {
		lines.add("how many Credits is glob prok Silver ?")
		lines.add("how many Credits is glob prok Gold ?")
		lines.add("how many Credits is glob prok Iron ?")
		val result = Main().parseToResult(lines, productToCredits, nameToSymbol, mapOfSymbol)
		assertEquals("glob prok Silver is 68 Credits", result[0])
		assertEquals("glob prok Gold is 57800 Credits", result[1])
		assertEquals("glob prok Iron is 782 Credits", result[2])
	}

	@Test
	fun `return one sentence when input illegal`() {
		lines.add("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?")
		val result = Main().parseToResult(lines, productToCredits, nameToSymbol, mapOfSymbol)
		assertEquals("I have no idea what you are talking about", result[0])
	}

}
