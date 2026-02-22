package com.example.mdp

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before


class Testing {

    class SimpleCalculator {
        fun add(a: Int, b: Int) = a + b
        fun subtract(a: Int, b: Int) = a - b
        fun multiply(a: Int, b: Int) = a * b
        fun divide(a: Int, b: Int): Int? {
            return if (b != 0) a / b else null
        }
    }

    data class User(
        val name: String,
        val age: Int,
        val email: String
    ) {
        fun isValid(): Boolean {
            return name.isNotBlank() && age > 0 && email.contains("@")
        }

        fun isAdult(): Boolean = age >= 18
    }

    class InputValidator {
        fun isValidUsername(username: String): Boolean {
            return username.length >= 3 && username.all { it.isLetterOrDigit() }
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 6 && password.any { it.isDigit() }
        }

        fun isValidEmail(email: String): Boolean {
            return email.contains("@") && email.contains(".")
        }
    }

    private lateinit var calculator: SimpleCalculator
    private lateinit var validator: InputValidator

    @Before
    fun setUp() {
        calculator = SimpleCalculator()
        validator = InputValidator()
    }

    @Test
    fun testCalculatorAdd() {
        assertEquals(5, calculator.add(2, 3))
        assertEquals(0, calculator.add(-1, 1))
        assertEquals(-5, calculator.add(-2, -3))
    }

    @Test
    fun testCalculatorSubtract() {
        assertEquals(2, calculator.subtract(5, 3))
        assertEquals(-2, calculator.subtract(3, 5))
        assertEquals(0, calculator.subtract(5, 5))
    }

    @Test
    fun testCalculatorMultiply() {
        assertEquals(6, calculator.multiply(2, 3))
        assertEquals(0, calculator.multiply(5, 0))
        assertEquals(-6, calculator.multiply(-2, 3))
    }

    @Test
    fun testCalculatorDivide() {
        assertEquals(2, calculator.divide(6, 3))
        assertEquals(3, calculator.divide(9, 3))
        assertNull(calculator.divide(5, 0))
    }

    @Test
    fun testUserValidation() {
        val validUser = User("John", 25, "john@email.com")
        assertTrue(validUser.isValid())

        val invalidUser1 = User("", 25, "john@email.com")
        assertFalse(invalidUser1.isValid())

        val invalidUser2 = User("John", 0, "john@email.com")
        assertFalse(invalidUser2.isValid())

        val invalidUser3 = User("John", 25, "invalid-email")
        assertFalse(invalidUser3.isValid())
    }

    @Test
    fun testUserAgeCheck() {
        val adult = User("John", 25, "john@email.com")
        assertTrue(adult.isAdult())

        val minor = User("John", 15, "john@email.com")
        assertFalse(minor.isAdult())

        val exactly18 = User("John", 18, "john@email.com")
        assertTrue(exactly18.isAdult())
    }

    @Test
    fun testUsernameValidation() {
        assertTrue(validator.isValidUsername("john123"))
        assertTrue(validator.isValidUsername("abc123"))

        assertFalse(validator.isValidUsername("jo"))
        assertFalse(validator.isValidUsername("john@123"))
        assertFalse(validator.isValidUsername(""))
    }

    @Test
    fun testPasswordValidation() {
        assertTrue(validator.isValidPassword("pass123"))
        assertTrue(validator.isValidPassword("abc123456"))

        assertFalse(validator.isValidPassword("pass"))
        assertFalse(validator.isValidPassword("password"))
        assertFalse(validator.isValidPassword("123"))
    }

    @Test
    fun testEmailValidation() {
        assertTrue(validator.isValidEmail("test@email.com"))
        assertTrue(validator.isValidEmail("user.name@domain.co.uk"))

        assertFalse(validator.isValidEmail("test@email"))
        assertFalse(validator.isValidEmail("testemail.com"))
        assertFalse(validator.isValidEmail(""))
    }

    @Test
    fun testStringOperations() {
        val text = "Hello World"

        assertTrue(text.contains("Hello"))
        assertFalse(text.contains("Goodbye"))
        assertEquals(11, text.length)
        assertTrue(text.startsWith("Hello"))
        assertTrue(text.endsWith("World"))
        assertEquals("HELLO WORLD", text.uppercase())
        assertEquals("hello world", text.lowercase())
    }

    @Test
    fun testListOperations() {
        val numbers = listOf(1, 2, 3, 4, 5)

        assertEquals(5, numbers.size)
        assertTrue(numbers.contains(3))
        assertFalse(numbers.contains(10))
        assertTrue(numbers.isNotEmpty())
        assertEquals(1, numbers.first())
        assertEquals(5, numbers.last())
        assertEquals(15, numbers.sum())
    }

    @Test
    fun testConditions() {
        val score = 85

        val grade = when {
            score >= 90 -> "A"
            score >= 80 -> "B"
            score >= 70 -> "C"
            score >= 60 -> "D"
            else -> "F"
        }

        assertEquals("B", grade)
        assertNotEquals("A", grade)
        assertNotEquals("F", grade)
    }

    @Test
    fun testNullSafety() {
        val nullableString: String? = null
        val nonNullString = "Hello"

        assertNull(nullableString)
        assertNotNull(nonNullString)
        assertEquals(null, nullableString?.length)
        assertEquals(5, nonNullString.length)
    }
}