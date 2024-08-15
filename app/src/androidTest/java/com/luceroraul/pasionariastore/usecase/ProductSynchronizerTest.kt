package com.luceroraul.pasionariastore.usecase

import com.luceroraul.pasionariastore.model.BackendLookup
import com.luceroraul.pasionariastore.repository.BackendRepositoryFake
import com.luceroraul.pasionariastore.repository.ProductRepositoryFake
import org.junit.Assert
import org.junit.Test

class ProductSynchronizerTest {

    val syncronizer: ProductSynchronizer =
        ProductSynchronizer(BackendRepositoryFake(), ProductRepositoryFake())

    @Test
    fun unitType_1U_success() {
        val unit = BackendLookup(
            code = "MEDIDAS_VENTAS_1U",
            description = "Unidades"
        )
        val result = syncronizer.getNameType(unit)

        Assert.assertEquals("Unidades", result)
    }

    @Test
    fun unitType_1K_success() {
        val unit = BackendLookup(
            code = "MEDIDAS_VENTAS_1000G",
            description = "x1kg"
        )
        val result = syncronizer.getNameType(unit)

        Assert.assertEquals("Kilogramos", result)
    }

    @Test
    fun unitType_50G_success() {
        val unit = BackendLookup(
            code = "MEDIDAS_VENTAS_50G",
            description = "x50gr"
        )
        val result = syncronizer.getNameType(unit)

        Assert.assertEquals("Gramos", result)
    }

    @Test
    fun unitType_100G_success() {
        val unit = BackendLookup(
            code = "MEDIDAS_VENTAS_100G",
            description = "x100gr"
        )
        val result = syncronizer.getNameType(unit)

        Assert.assertEquals("Gramos", result)
    }

    @Test
    fun unitType_100G_fail() {
        val unit = BackendLookup(
            code = "MEDIDAS_VENTAS_1U",
            description = "Unidades"
        )
        val result = syncronizer.getNameType(unit)

        Assert.assertNotEquals("Gramos", result)
    }
}