package com.dung.onlinemarketnodejs.user

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.dung.onlinemarketnodejs.R
import com.dung.onlinemarketnodejs.adapter.ProductAdapter
import com.dung.onlinemarketnodejs.adapter.TechnologyAdapter
import com.dung.onlinemarketnodejs.base.BaseActivity
import com.dung.onlinemarketnodejs.model.Product
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main_screen.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

class MainScreenActivity : BaseActivity() {

    var localHost = "https://dung-online-market.herokuapp.com/"
    lateinit var mSocket: Socket

    // server send data
    var serverProductData = "server-product-data"

    // Technology
    lateinit var techList: ArrayList<Product>
    lateinit var techAdapter: TechnologyAdapter

    // all product
    lateinit var productList: ArrayList<Product>
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        setSupportActionBar(toolbar)

        initView()
        getProductFromServer()
        buttonOnClick()

    }

    private fun buttonOnClick() {
        tvNext.setOnClickListener {
            startActivity(Intent(this@MainScreenActivity, TechnologyActivity::class.java))
        }
    }

    private fun getProductFromServer() {
        mSocket.on(serverProductData, object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                runOnUiThread(object : Runnable {
                    override fun run() {
                        var jsonObject = args[0] as JSONObject
                        try {
                            var jsonArray = jsonObject.getJSONArray("data")
                            techList.clear()
                            productList.clear()
                            for (i in 0..jsonArray.length()) {
                                var jObject = jsonArray.getJSONObject(i)
                                var name = jObject.getString("name")
                                var price = jObject.getInt("price")
                                var image = jObject.getString("image")
                                var genre = jObject.getString("genre")
                                var des = jObject.getString("description")
                                if (genre == "Technology") {
                                    if (techList.size < 5) {
                                        techList.add(Product(name, price, image, genre, des))
                                    }
                                } else {
                                    productList.add(Product(name, price, image, genre, des))
                                }
                            }
                        } catch (e: JSONException) {
                        }
                        techAdapter.notifyDataSetChanged()
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

        techList = ArrayList()
        productList = ArrayList()

        techAdapter = TechnologyAdapter(this, techList,this)
        productAdapter = ProductAdapter(this, productList, this)

        var techLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var productLayout = GridLayoutManager(this, 2)

        rvTechnology.layoutManager = techLayout
        rvTechnology.adapter = techAdapter

        rvProduct.layoutManager = productLayout
        rvProduct.adapter = productAdapter

    }

    fun productClicked(position: Int){

    }

    fun techClicked(position: Int){

    }

}
