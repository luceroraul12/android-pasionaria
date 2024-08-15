package com.luceroraul.pasionariastore.model

import androidx.room.Embedded
import androidx.room.Relation
import java.text.NumberFormat
import java.util.Currency

data class CartWithData(
    @Embedded val cart: Cart,
    @Relation(
        entity = ProductCart::class,
        parentColumn = "cart_id",
        entityColumn = "cart_id"
    )
    val productCartWithData: List<ProductCartWithData>
)

fun CartWithData.calculateTotalPriceLabel(): String {
    val products = this.productCartWithData ?: emptyList()
    var value = 0.0
    if (products.isNotEmpty()) {
        value = (products.map { p -> p.productCart.totalPrice }
            .reduce { acc, price -> acc + price })
    }

    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.setMaximumFractionDigits(2)
    format.currency = Currency.getInstance("ARS")

    return format.format(value)
}

fun CartWithData.toBackendData(): BackendCart {
    return BackendCart(
        cartId = this.cart.id,
        dateCreated = this.cart.dateCreated,
        products = this.productCartWithData.map(ProductCartWithData::toBackendData)
    )
}