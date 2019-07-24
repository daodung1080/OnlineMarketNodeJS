package com.dung.onlinemarketnodejs.user

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.dung.onlinemarketnodejs.R
import com.dung.onlinemarketnodejs.adapter.MoreAdapter
import com.dung.onlinemarketnodejs.base.BaseActivity
import com.dung.onlinemarketnodejs.model.Product
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_technology.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

class TechnologyActivity : BaseActivity() {

    var localHost = "https://dung-online-market.herokuapp.com/"
    lateinit var mSocket: Socket


    lateinit var productAdapter: MoreAdapter
    lateinit var productList: ArrayList<Product>
    // server send data
    var serverProductData = "server-product-data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_technology)

        setSupportActionBar(toolbar)

        // Create toolbar with new back button
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)

        initView()
        getAllProduct()


    }

    private fun getAllProduct() {
        mSocket.on(serverProductData, object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                runOnUiThread(object : Runnable {
                    override fun run() {
                        var jsonObject = args[0] as JSONObject
                        try {
                            var jsonArray = jsonObject.getJSONArray("data")
                            productList.clear()
                            for (i in 0..jsonArray.length()) {
                                var jObject = jsonArray.getJSONObject(i)
                                var name = jObject.getString("name")
                                var price = jObject.getInt("price")
                                var image = jObject.getString("image")
                                var genre = jObject.getString("genre")
                                var des = jObject.getString("description")
                                if (genre == "Technology") {
                                    productList.add(Product(name, price, image, genre, des))
                                }
                            }
                        } catch (e: JSONException) { }
                        productAdapter.notifyDataSetChanged()
                    }
                })
            }
        })
    }

    private fun initView() {

        try {
            mSocket = IO.socket(localHost)
        } catch (e: URISyntaxException) {
            showMessage("Loi Link $e", clMain)
        }
        mSocket.connect()

        productList = ArrayList()

        productAdapter = MoreAdapter(this, productList, this)

        var productLayout = GridLayoutManager(this, 2)

        rvMoreTechnology.layoutManager = productLayout
        rvMoreTechnology.adapter = productAdapter

    }

    fun productClicked(position: Int) {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
