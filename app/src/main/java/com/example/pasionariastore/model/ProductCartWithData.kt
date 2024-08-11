package com.example.pasionariastore.model

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

fun ProductCartWithData.getCopyWithBackendId(backendCartProduct: BackendCartProduct): ProductCartWithData {
    return this.copy(productCart = productCart.copy(backendProductCartId = backendCartProduct.backendCartProductId))
}