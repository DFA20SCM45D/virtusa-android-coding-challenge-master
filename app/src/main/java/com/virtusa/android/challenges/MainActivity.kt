package com.virtusa.android.challenges

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.virtusa.android.challenges.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    private lateinit var itemList: List<DeliveryItem>            //to store list of delivery items
    private lateinit var itemListForView: MutableList<DeliveryItem> // to add list of delivery items using a loop for each order id and display all data together

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var api1 = NetworkService()         //creating NetworkService() object to call using RetroFit
        val call = api1.api.fetchOrders()

        //calling fetchOrders and storing data in a list named OrderList, OrderList contains order id in Long datatype
        call.enqueue(object: Callback<OrdersResponse> {
            override fun onResponse(call : Call<OrdersResponse>, response: Response<OrdersResponse>) {
                if(response.isSuccessful) {

                    var OrderList: List<Long>
                    OrderList = response.body()!!.orders
                    Log.d(TAG, "onResponse: order list ${OrderList.get(0)}")

                     itemList = emptyList()
                    itemListForView = emptyList<DeliveryItem>().toMutableList()
                   // itemListForView.clear() //if app is supporting orientation rotation, need to clear the list to remove duplication

                    var api111 = NetworkService() //another NetworkService object to call fetchOrderById

                    //For  loop is used to get delivery items by all Order ID present in the list
                    //Delivery items for particular order id can be fetched as well without using a loop
                       //for example,use can manually add the ID to UI and that can be applied to fetchOrderById(user input)

                    for(i in OrderList.indices) {
                        val call1 = api111.api.fetchOrderById(OrderList.get(i))

                        call1.enqueue(object : Callback<OrderResponse> {
                            override fun onResponse(
                                call: Call<OrderResponse>,
                                response1: Response<OrderResponse>
                            ) {
                                Log.d(TAG, "Response 1........... ${response1.body()!!.items}")
                                itemList = response1.body()!!.items  //storing delivery items in list as per order id
                                itemListForView.addAll(itemList)     //adding delivery items to the list and appending the list for each order id

                                adapter.update(itemListForView)       //updating the adapter
                                adapter.notifyDataSetChanged()
                            }

                            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "NOT DOWNLOADED",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                }
            }

            override fun onFailure(call: Call<OrdersResponse>, t: Throwable) {
               Toast.makeText(this@MainActivity,"NOT DOWNLOADED",Toast.LENGTH_LONG).show()
            }
        })

        bindViews()

        viewModel.setStateUpdateListener(object : MainActivityViewModel.UpdateListener {
            override fun onUpdate(state: ItemListViewState) = renderItemList(state)
        })
    }

    private fun renderItemList(state: ItemListViewState) {

        // Update the Adapter
          adapter.update(itemList)
          adapter.notifyDataSetChanged()

    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ItemAdapter()
        recyclerView.adapter = adapter
    }
}
