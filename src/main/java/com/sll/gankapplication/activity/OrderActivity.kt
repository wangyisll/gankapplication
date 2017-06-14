package com.sll.gankapplication.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.sll.gankapplication.R
import com.sll.gankapplication.adapter.OrderAdapter
import com.sll.gankapplication.constant.Constant
import kotlinx.android.synthetic.main.activity_order.*
import java.util.*

class OrderActivity : AppCompatActivity() {
    private var adapter:OrderAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setSupportActionBar(tb_sort)
        tb_sort.setNavigationIcon(R.drawable.arrow_back_black_24dp)
        tb_sort.setNavigationOnClickListener { onBackPressed() }

        var touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(p0: RecyclerView?, p1: RecyclerView.ViewHolder?): Int {
                val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlag, 0)
            }

            override fun onMove(p0: RecyclerView?, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                Collections.swap(Constant.sCategoryList, p1.adapterPosition, p2.adapterPosition)
                adapter?.notifyItemMoved(p1.adapterPosition, p2.adapterPosition)
                Constant.sCategoryListChanged =true
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder?, p1: Int) {

            }

        })
        touchHelper.attachToRecyclerView(rv_sort)
        adapter = OrderAdapter(touchHelper)
        rv_sort.adapter = adapter
        rv_sort.layoutManager = LinearLayoutManager(this)
    }
}
