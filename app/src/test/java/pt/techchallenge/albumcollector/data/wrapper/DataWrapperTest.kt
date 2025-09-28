package pt.techchallenge.albumcollector.data.wrapper

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class DataWrapperTest {

    @Test
    fun `data wrapper creation with default parameters should succeed`() {
        val dataWrapper = DataWrapper<String, Boolean, Exception>()

        assertEquals(null, dataWrapper.data)
        assertEquals(null, dataWrapper.isLoading)
        assertEquals(null, dataWrapper.error)
    }

    @Test
    fun `data wrapper creation with all parameters should succeed`() {
        val exception = Exception("error")
        val dataWrapper = DataWrapper(data = "data", isLoading = true, error = exception)

        assertEquals("data", dataWrapper.data)
        assertEquals(true, dataWrapper.isLoading)
        assertEquals(exception, dataWrapper.error)
    }

    @Test
    fun `data wrapper creation with data only should succeed`() {
        val dataWrapper = DataWrapper<String, Boolean, Exception>(data = "data")

        assertEquals("data", dataWrapper.data)
        assertEquals(null, dataWrapper.isLoading)
        assertEquals(null, dataWrapper.error)
    }

    @Test
    fun `data wrapper creation with isLoading only should succeed`() {
        val dataWrapper = DataWrapper<String, Boolean, Exception>(isLoading = true)

        assertEquals(null, dataWrapper.data)
        assertEquals(true, dataWrapper.isLoading)
        assertEquals(null, dataWrapper.error)
    }

    @Test
    fun `data wrapper creation with error only should succeed`() {
        val exception = Exception("error")
        val dataWrapper = DataWrapper<String, Boolean, Exception>(error = exception)

        assertEquals(null, dataWrapper.data)
        assertEquals(null, dataWrapper.isLoading)
        assertEquals(exception, dataWrapper.error)
    }

    @Test
    fun `data wrapper creation with null values should succeed`() {
        val wrapper = DataWrapper<String, Boolean, Exception>()

        assertNull(wrapper.data)
        assertNull(wrapper.isLoading)
        assertNull(wrapper.error)
    }

    @Test
    fun `data wrapper toString should contain all properties`() {
        val exception = Exception("error")
        val dataWrapper = DataWrapper(data = "data", isLoading = true, error = exception)

        assertEquals("DataWrapper(data=data, isLoading=true, error=$exception)", dataWrapper.toString())
    }
}