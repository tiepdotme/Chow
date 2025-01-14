package io.julius.chow.data.source.cache

import androidx.room.*
import io.julius.chow.data.model.*
import io.reactivex.Flowable

@Dao
interface AppDAO {

    @Query("SELECT * FROM User WHERE isCurrentUser = 1")
    fun getCurrentUser(): Flowable<UserEntity>

    @Query("SELECT * FROM User WHERE isCurrentUser = 1")
    fun fetchCurrentUser(): UserEntity

    @Query("SELECT * FROM User where id = :id")
    fun getUser(id: String): UserEntity

    @Query("SELECT * FROM Restaurants WHERE isCurrentRestaurant = 1")
    fun getCurrentRestaurant(): Flowable<RestaurantEntity>

    @Query("SELECT * FROM Restaurants WHERE isCurrentRestaurant = 1")
    fun fetchCurrentRestaurant(): RestaurantEntity

    @Query("SELECT * FROM Restaurants where id = :id")
    fun getRestaurant(id: String): RestaurantEntity

    @Query("SELECT * FROM Restaurants")
    fun getRestaurants(): Flowable<List<RestaurantEntity>>

    @Query("SELECT * FROM Food where restaurantId = :id")
    fun getRestaurantMenu(id: String): Flowable<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userEntity: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRestaurant(restaurantEntity: RestaurantEntity): Long

    @Query("SELECT * FROM Food WHERE category = :category")
    fun getMenu(category: String): Flowable<List<FoodEntity>>

    @Transaction
    fun saveFood(foodEntity: FoodEntity): Long {
        var id = insertFood(foodEntity)
        if (id == -1L) {
            id = updateFood(foodEntity).toLong()
        }
        return id
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFood(foodEntity: FoodEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateFood(foodEntity: FoodEntity): Int

    @Query("DELETE FROM User")
    fun deleteUser()

    @Query("DELETE FROM Restaurants")
    fun deleteRestaurants()

    @Query("SELECT * FROM Orders")
    fun getOrders(): Flowable<List<OrderEntity>>

    @Query("SELECT * FROM Orders WHERE restaurantId = :restaurantId")
    fun getRestaurantOrders(restaurantId: String): Flowable<List<OrderEntity>>

    @Query("SELECT * FROM Orders where id = :id")
    fun getOrder(id: String): OrderEntity

    @Transaction
    fun saveOrder(orderEntity: OrderEntity): Long {
        var id = insertOrder(orderEntity)
        if (id == -1L) {
            id = updateOrder(orderEntity).toLong()
        }
        return id
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrder(orderEntity: OrderEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateOrder(orderEntity: OrderEntity): Int

    @Delete
    fun deleteOrder(orderEntity: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun savePlacedOrder(placedOrderEntity: PlacedOrderEntity): Long
}