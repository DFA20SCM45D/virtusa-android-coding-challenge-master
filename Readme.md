## Instruction to complete the project

NetworkApi has fetchOrders, fetchOrderById, discount API with support for Retrofit, RxJava and Coroutines.

1. Call fetchOrders (any of Retrofit/RxJava/Coroutine)
2. For each order id in OrdersResponse#list, call fetchOrderById by passing in the order id
3. Display the list of DeliveryItem returned by fetchOrderById in the ItemAdapter 
