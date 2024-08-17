package com.luceroraul.pasionariastore.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductCartWithData(
    @Embedded val productCart: ProductCart,
    @Relation(
        entity = Product::class,
        parentColumn = "product_id",
        entityColumn = "product_id"
    )
    val productWithUnit: ProductWithUnit
)

fun ProductCartWithData.toBackendData(): BackendCartProduct {
    return BackendCartProduct(
        productId = this.productCart.productId,
        price = this.productCart.totalPrice,
        quantity = this.productCart.quantity.toDouble(),
        cartProductId = this.productCart.productCartId
    )
}

fun ProductCartWithData.getCartProductWithTotalPriceUpdate(): ProductCart {
    this.productWithUnit.let {
        val priceList = it.product.priceList
        val quantity = this.productCart.quantity.toDouble()
        val isUnit = it.unit.value == 1.0
        var newPrice = priceList * quantity
        if (!isUnit)
            newPrice = newPrice / 1000
        return this.productCart.copy(totalPrice = newPrice)
    }
}