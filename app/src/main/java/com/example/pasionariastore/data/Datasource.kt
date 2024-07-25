package com.example.pasionariastore.data

import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithProductAndUnit
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.model.Unit

object Datasource {
    val apiUnits: List<Unit> = listOf(
        Unit(unitId = 1, name = "x50gr", value = 0.05),
        Unit(unitId = 2, name = "x100gr", value = 0.1),
        Unit(unitId = 3, name = "x250gr", value = 0.25),
        Unit(unitId = 4, name = "x500gr", value = 0.5),
        Unit(unitId = 5, name = "x750gr", value = 0.75),
        Unit(unitId = 6, name = "x1kg", value = 1.0),
        Unit(unitId = 7, name = "Unidad", value = 1.0, nameType = "Unidad"),
    )

    val apiProducts: List<ProductWithUnit> = listOf(
        ProductWithUnit(
            product = Product(
                name = "Aceitunas", description = "Libanti x 500g", priceList = 1595.0, unitId = 1
            ), unit = apiUnits.get(0)
        ),
        ProductWithUnit(
            product = Product(
                name = "Pera", description = "Libanti x 500g", priceList = 23.0, unitId = 2
            ), unit = apiUnits.get(1)
        ),
        ProductWithUnit(
            product = Product(
                name = "Manzana", description = "Libanti x 500g", priceList = 344.0, unitId = 3
            ), unit = apiUnits.get(2)
        ),
        ProductWithUnit(
            product = Product(
                name = "Carne", description = "Libanti x 500g", priceList = 500.0, unitId = 4
            ), unit = apiUnits.get(3)
        ),
        ProductWithUnit(
            product = Product(
                name = "Especie", description = "Libanti x 500g", priceList = 24500.0, unitId = 5
            ), unit = apiUnits.get(4)
        )
    )

    val apiCartProducts: List<ProductCartWithProductAndUnit> = listOf(
//        ProductCartWithProductAndUnit()
//        ProductCart(productWithUnit = apiProducts.get(0)),
//        ProductCart(productWithUnit = apiProducts.get(1)),
//        ProductCart(productWithUnit = apiProducts.get(2)),
//        ProductCart(productWithUnit = apiProducts.get(3)),
//        ProductCart(productWithUnit = apiProducts.get(4)),
    )
}