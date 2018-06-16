package com.sdsmdg.aridj.lib

import android.app.Activity
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import com.sdsmdg.aridj.lib.transformations.CombineTransformation
import com.sdsmdg.aridj.lib.transformations.RotationTransformation
import com.sdsmdg.aridj.lib.transformations.ScaleTransformation
import java.util.*

class PopOutNavBuilder(private val activity: Activity, private val toolbar: Toolbar) {

    private val navDrawerLayout: PopOutNavLayout = PopOutNavLayout(activity)
    private var menuIds: ArrayList<Int>

    init {
        menuIds = ArrayList()
    }

    fun withMenus(menus: ArrayList<Int>): PopOutNavBuilder {
        this.menuIds = menus

        return this
    }

    fun withColors(backgroundColor: Int , itemHighlightColor: Int): PopOutNavBuilder {
        this.navDrawerLayout.navColor = backgroundColor
        this.navDrawerLayout.itemHighlightColor = itemHighlightColor

        return this
    }

    fun withDrawerClosed(isClosed: Boolean): PopOutNavBuilder {
        this.navDrawerLayout.isClosed = isClosed

        return this
    }

    fun withItemClickListener(itemClickedListener: (Int, View) -> Unit): PopOutNavBuilder {
        this.navDrawerLayout.navItemClickListener = itemClickedListener

        return this
    }

    fun build(): PopOutDrawer {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        val rootView = contentView.getChildAt(0)
        contentView.removeAllViews()
        prepareNavLayout(rootView)
        contentView.addView(navDrawerLayout)

        return navDrawerLayout
    }

    private fun prepareNavLayout(rootView: View): PopOutNavLayout {
        navDrawerLayout.id = R.id.pop_out_layout
        navDrawerLayout.setMainView(rootView)

        navDrawerLayout.setTransformation(CombineTransformation(
                Arrays.asList(ScaleTransformation(),
                        RotationTransformation())))

        navDrawerLayout.addMenus(menuIds)
        addDrawer(navDrawerLayout)
        navDrawerLayout.addView(rootView)

        return navDrawerLayout
    }

    private fun addDrawer(navDrawer: PopOutNavLayout) {
        val drawerLayout = PopOutNavDrawerLayout(activity, navDrawer)

        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        toggle.syncState()

        navDrawer.setDrawerListener(toggle)
    }
}