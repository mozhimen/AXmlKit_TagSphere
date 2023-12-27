package com.magicgoop.tagpshere.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.magicgoop.tagpshere.example.databinding.ActivityMainBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB


class MainActivity : BaseActivityVB<ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.viewpager.adapter = ExampleFragmentPagerAdapter(supportFragmentManager)
        vb.tlTabs.setupWithViewPager(vb.viewpager)

        setSupportActionBar(vb.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAbout) {
            openGithub()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun openGithub() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/magic-goop/tag-sphere"))
        packageManager?.let {
            intent.resolveActivity(it)?.let {
                startActivity(intent)
            }
        }
    }
}