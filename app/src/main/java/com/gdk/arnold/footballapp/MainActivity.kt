package com.gdk.arnold.footballapp

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.gdk.arnold.footballapp.R.array.*
import com.gdk.arnold.footballapp.Adapter
import com.gdk.arnold.footballapp.Item
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {
    var items: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()

        MainActivityUI(items).setContentView(this)
    }

    class MainActivityUI(val items: List<Item>) : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            relativeLayout {
                backgroundColor = Color.WHITE
                recyclerView {
                    val orientation = LinearLayoutManager.VERTICAL
                    layoutManager = LinearLayoutManager(context, orientation, true)
                    adapter = Adapter(context, items) {
                        startActivity<DetailActivity>(
                            "image" to it.image,
                            "name" to it.name, "info" to it.info
                        )
                    }
                }.lparams(width = matchParent)
            }
        }
    }

    private fun initData() {
        val image = resources.obtainTypedArray(club_image)
        val name = resources.getStringArray(club_name)
        val info = resources.getStringArray(club_info)
        items.clear()
        for (i in name.indices)
            items.add(Item(image.getResourceId(i, 0), name[i], info[i]))
        image.recycle()
    }
}
