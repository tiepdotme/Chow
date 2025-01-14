package io.julius.chow.domain.interactor.food

import io.julius.chow.domain.Result
import io.julius.chow.domain.interactor.Interactor
import io.julius.chow.domain.model.PlacedOrderModel
import io.julius.chow.domain.repository.ChowRepository
import javax.inject.Inject

class PlaceOrderInteractor @Inject constructor(private val chowRepository: ChowRepository) :
    Interactor<PlacedOrderModel, String>() {

    override suspend fun run(params: PlacedOrderModel): String {

        // Loop through all orders in this order placement and add the restaurant id to an array
        // so restaurants can query to see orders they need to handle
        params.orders.forEach {
            params.restaurantIds.add(it.restaurantId)
        }

        return when (val result = chowRepository.placeOrder(params)) {
            is Result.Success -> {
                result.data
            }

            is Result.Failure -> {
                result.exception.message
            }
        }
    }
}