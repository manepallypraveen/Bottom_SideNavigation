package com.bottomsidenavigation

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.bottomsidenavigation.custom_dialog.ExitAlertDialog
import com.bottomsidenavigation.databinding.ActivitySideBottomNavigationBinding
import com.bottomsidenavigation.ui.FragmentSideNavigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DashWithSideBottomNaviagtionActivity : AppCompatActivity() {

    lateinit var binding: ActivitySideBottomNavigationBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BottomSideNavigation)
        super.onCreate(savedInstanceState)

        binding = ActivitySideBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews(binding)
        observeNavElements(binding, navHostFragment.navController)

        mDrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val actionDrawerToggle =
            ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout?.addDrawerListener(actionDrawerToggle)
        actionDrawerToggle.syncState()
        val fragmentSideNavigation = FragmentSideNavigation()
        supportFragmentManager.beginTransaction()
            .add(R.id.diagramEditorSlideInMenuFragmentHolder, fragmentSideNavigation)
            .commit()

        setupDrawer()

//        binding.toolbarLayout.infoImg.setOnClickListener {
//            Toast.makeText(applicationContext, "Clicking on Toolbar", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun refreshCurrentFragment() {
        val id = navController.currentDestination?.id
        navController.popBackStack(id!!, true)
        navController.navigate(id)
    }

    private fun initViews(binding: ActivitySideBottomNavigationBinding) {
        navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        with(navHostFragment.navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }

    }

    private fun observeNavElements(
        binding: ActivitySideBottomNavigationBinding,
        navController: NavController
    ) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
//                    supportActionBar!!.setDisplayShowTitleEnabled(true)
//                    showBothNavigation()
//                    supportActionBar!!.setDisplayShowTitleEnabled(true)
//                    hideBothNavigation()
                    showBothNavigation()
                    showToolbarNavigation()
                }
                R.id.navigation_dashboard -> {
                    showBothNavigation()
                    showToolbarNavigation()
                }
                R.id.navigation_notifications -> {
                    showBothNavigation()
                    showToolbarNavigation()
                }
                else -> {
                    showBothNavigation()
                    supportActionBar!!.setDisplayShowTitleEnabled(true)
                }
            }
        }


        binding.bottomNavView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private fun showToolbarNavigation() {
        binding.toolbar.visibility = View.VISIBLE
    }

    private fun hideBothNavigation() {
        binding.bottomNavView.visibility = View.GONE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        binding.toolbar.visibility = View.GONE
    }

    private fun showBothNavigation() {
        binding.toolbar.visibility = View.VISIBLE
        binding.bottomNavView.visibility = View.VISIBLE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun setupNavControl() {
        binding.bottomNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {//   navController.navigateUp()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            if (navController.currentDestination?.id == R.id.navigation_home) {
                ExitAlertDialog.newInstance("DashBoardScreenActivity", applicationContext)
                    .show(supportFragmentManager, ExitAlertDialog.TAG)
            } else if (navController.currentDestination?.id == R.id.navigation_dashboard) {
                navController.navigate(R.id.navigation_home)
            } else if (navController.currentDestination?.id == R.id.navigation_notifications) {
                navController.navigate(R.id.navigation_home)
            } else {
                super.onBackPressed()
                finish()
            }
        }
    }

    private fun logout() {
        MaterialAlertDialogBuilder(
            this,
            R.style.Theme_BottomNavWithSideNav_Dialog_Alert
        ).setTitle(this.resources.getString(R.string.logout))
            .setMessage(this.resources.getString(R.string.would_you_like_to_logout))
            .setPositiveButton(
                this.resources.getString(R.string.logout)
            )
            { _, _ ->
                binding.drawerLayout.closeDrawers()
                finish()
            }.setNegativeButton(
                this.resources.getString(R.string.cancel)
            ) { dialog, _ -> dialog.cancel() }.show()
    }

    private fun setupDrawer() {
        mDrawerToggle = object : ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            R.string.open,
            R.string.close
        ) {
            /** Called when a drawer has settled in a completely open state.  */
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
//                supportActionBar.setTitle(R.string.film_genres)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state.  */
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
//                supportActionBar.setTitle(mActivityTitle)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }
        mDrawerToggle?.setDrawerIndicatorEnabled(true)
        mDrawerLayout!!.setDrawerListener(mDrawerToggle)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (mDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else item.onNavDestinationSelected(navHostFragment.navController) ||
                super.onOptionsItemSelected(item)
    }

    fun hanldeSideNavigationClicks(from: String) {
        binding.drawerLayout.closeDrawers()
        if (from.equals(resources.getString(R.string.logout))) {
            logout()
        } else if (from.equals(resources.getString(R.string.close_drawer))) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun closeApp() {
        finish()
    }


}