package com.sll.gankapplication.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.sll.gankapplication.R
import com.sll.gankapplication.`interface`.OnDatePikerSelectListener
import com.sll.gankapplication.adapter.MainVPAdapter
import com.sll.gankapplication.constant.Constant
import com.sll.gankapplication.util.RxPermissionUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var mMenu: Menu? = null
    private var datePikerListener: OnDatePikerSelectListener? = null
    private val mIconUnSelectIds = arrayOf(R.mipmap.tab_daily_unselect, R.mipmap.tab_sort_unselect, R.mipmap.tab_bonus_unselect, R.mipmap.tab_about_unselect)
    private val mIconSelectIds = arrayOf(R.mipmap.tab_daily_select, R.mipmap.tab_sort_select, R.mipmap.tab_bonus_select, R.mipmap.tab_about_select)
    private val mTabEntities = arrayListOf<CustomTabEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        setSupportActionBar(toolBar)

        initCommonTabLayout()

        initViewPager()

        RxPermissionUtils.createInstance(this)

    }

    private fun initViewPager() {
        vp_main.adapter = MainVPAdapter(supportFragmentManager)
        vp_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                ctl.currentTab = position
                when (position) {
                    0 -> {
                        showDateMenu()
                        hideFilter()
                    }
                    1 -> {
                        hideDateMenu()
                        showFilter()
                    }
                    else -> {
                        hideDateMenu()
                        hideFilter()
                    }
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        mMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        showDateMenu()
        hideFilter()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> startActivity<OrderActivity>()

            R.id.action_datepicker -> showDatePiker()

            R.id.action_search -> startActivity<SearchActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 显示时间选择对话框
     */
    private fun showDatePiker() {
        if (Constant.YEAR == -1 && Constant.MONTH == -1 && Constant.DAY == -1) {
            val c = Calendar.getInstance()
            Constant.YEAR = c.get(Calendar.YEAR)
            Constant.MONTH = c.get(Calendar.MONTH)
            Constant.DAY = c.get(Calendar.DAY_OF_MONTH)
        }
        DatePickerDialog(this, { _, year, month, day ->
            Constant.YEAR = year
            Constant.MONTH = month
            Constant.DAY = day
            Log.i(TAG, ": $year : $month : $day")
            datePikerListener?.onDatePiker(year, month, day)
        }, Constant.YEAR, Constant.MONTH, Constant.DAY).show()
    }

    fun setOnDatePikerListener(listener: OnDatePikerSelectListener) {
        datePikerListener = listener
    }

    /**
     * 隐藏菜单的排序按钮
     */
    private fun hideFilter() {
        mMenu?.findItem(R.id.action_filter)?.isVisible = false
    }

    /**
     * 显示菜单的排序按钮
     */
    private fun showFilter() {
        mMenu?.findItem(R.id.action_filter)?.isVisible = true
    }

    /**
     * 隐藏菜单的日期选择按钮
     */
    private fun hideDateMenu() {
        mMenu?.findItem(R.id.action_datepicker)?.isVisible = false
    }

    /**
     * 显示菜单的日期选择按钮
     */
    private fun showDateMenu() {
        mMenu?.findItem(R.id.action_datepicker)?.isVisible = true
    }

    /**
     * 初始化底部导航栏
     */
    fun initCommonTabLayout() {
        Constant.sTabTitles.indices.mapTo(mTabEntities) {
            object : CustomTabEntity {
                override fun getTabUnselectedIcon(): Int {
                    return mIconUnSelectIds[it]
                }

                override fun getTabSelectedIcon(): Int {
                    return mIconSelectIds[it]
                }

                override fun getTabTitle(): String {
                    return Constant.sTabTitles[it]
                }

            }
        }
        ctl.setTabData(mTabEntities)
        ctl.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                vp_main.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }

        })

    }

    override fun onBackPressed() {
        alert("要退出？", "提示") {
            positiveButton("确定") { dialog ->
                dialog.dismiss()
                finish()
            }
            negativeButton("取消") {
                it.dismiss()
            }
        }.show()
    }
}
