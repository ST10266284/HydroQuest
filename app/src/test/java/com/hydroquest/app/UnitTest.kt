package com.hydroquest.app

import com.hydroquest.app.model.LogIntakeRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTest {

    @Test
    fun logIntakeRequest_initializesCorrectly() {
        val request = LogIntakeRequest("user123", 250)
        assertEquals("user123", request.userId)
        assertEquals(250, request.amount)
    }
}
