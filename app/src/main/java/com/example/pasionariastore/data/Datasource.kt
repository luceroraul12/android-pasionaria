package com.example.pasionariastore.data

import com.example.pasionariastore.model.AmountCart
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.Unit

object Datasource {
    val apiUnits: List<Unit> = listOf(
        Unit(name = "x50gr", value = 0.05),
        Unit(name = "x100gr", value = 0.1),
        Unit(name = "x250gr", value = 0.25),
        Unit(name = "x500gr", value = 0.5),
        Unit(name = "x750gr", value = 0.75),
        Unit(name = "x1kg", value = 1.0),
        Unit(name = "Unidad", value = 1.0),
    )

    val apiProducts: List<Product> = listOf(
        Product(name = "Aceitunas", description = "Libanti x 500g", priceList = 1595.0, unitId = apiUnits.get(0)),
        Product(name = "mix ensaladas", priceList = 2654.0, unitId = apiUnits.get(0)),
        Product(name = "Burro", priceList = 8213.84, unitId = apiUnits.get(0)),
        Product(name = "canabis", description = "THC-CBD", priceList = 10000.0, unitId = apiUnits.get(0)),
        Product(name = "sopa juliana", priceList = 11430.0, unitId = apiUnits.get(0)),
        Product(name = "Baharat", priceList = 15231.4, unitId = apiUnits.get(0)),
        Product(name = "Hipericon", description = "Hierba de San Juan", priceList = 28021.61, unitId = apiUnits.get(0)),
        Product(name = "maiz blanco ", description = "pisado partido", priceList = 2418.95, unitId = apiUnits.get(0)),
        Product(name = "Hamamelis", priceList = 78149.19, unitId = apiUnits.get(0)),
        Product(name = "Pasta de mani", description = "cacao x370g", priceList = 2030.0, unitId = apiUnits.get(0)),
        Product(name = "Fresno", priceList = 2895.0, unitId = apiUnits.get(0)),
        Product(name = "Tomillo", priceList = 7249.04, unitId = apiUnits.get(0)),
        Product(name = "Repelente natural", description = "x 60cc", priceList = 2300.0, unitId = apiUnits.get(0)),
        Product(name = "Zuccinis", description = "Orgánicos", priceList = 2070.0, unitId = apiUnits.get(0)),
        )

    val apiCartProducts: List<ProductCart> = listOf(
        ProductCart(product = apiProducts.get(0), AmountCart()),
        ProductCart(product = apiProducts.get(1), AmountCart()),
        ProductCart(product = apiProducts.get(2), AmountCart()),
        ProductCart(product = apiProducts.get(3), AmountCart()),
        ProductCart(product = apiProducts.get(4), AmountCart()),
    )
}