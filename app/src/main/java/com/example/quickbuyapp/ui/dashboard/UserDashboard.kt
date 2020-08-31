package com.example.quickbuyapp.ui.dashboard

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import android.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.EventBus.BestDealItemClick
import com.example.quickbuyapp.EventBus.CategoryClick
import com.example.quickbuyapp.EventBus.PopularProductClick
import com.example.quickbuyapp.EventBus.ProductItemClick
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.CategoryModel
import com.example.quickbuyapp.model.PopularCategoryModel
import com.example.quickbuyapp.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dmax.dialog.SpotsDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UserDashboard : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController : NavController
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private var dialog:AlertDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_category,
                R.id.nav_gallery,
                R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop(){
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    fun OnCategorySelected(event : CategoryClick){
        if(event.isSuccess){
            //Toast.makeText(this , "Click to"+event.category.name,Toast.LENGTH_SHORT).show()
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_product_list)
        }
    }

    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    fun OnFoodSelected(event : ProductItemClick){
        if(event.isSuccess){
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_product_detail)
        }
    }

    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    fun OnPopularProductClick(event : PopularProductClick){
        if(event.popularCategoryModel != null){

            dialog!!.show()

            FirebaseDatabase.getInstance()
                .getReference("Category")
                .child(event.popularCategoryModel.category_id!!)
                .addListenerForSingleValueEvent(object : ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            Common.categorySelected = snapshot.getValue(CategoryModel::class.java)

                            // Load Product
                            FirebaseDatabase.getInstance()
                                .getReference("Category")
                                .child(event.popularCategoryModel.category_id!!)
                                .child("product")
                                .orderByChild("item_id")
                                .equalTo(event.popularCategoryModel.item_id)
                                .limitToLast(1)
                                .addListenerForSingleValueEvent(object : ValueEventListener{

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if(snapshot.exists()){
                                            for(productSnapshot in snapshot.children){
                                                Common.productSelected = productSnapshot.getValue(
                                                    ProductModel::class.java)
                                                navController.navigate(R.id.nav_product_detail)
                                            }
                                        }
                                        else{
                                            Toast.makeText(this@UserDashboard,"Item doesn't exists",Toast.LENGTH_SHORT).show()
                                        }
                                        dialog!!.dismiss()
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        dialog!!.dismiss()
                                        Toast.makeText(this@UserDashboard,""+error.message,Toast.LENGTH_SHORT).show()
                                    }

                                })
                        }
                        else{
                            dialog!!.dismiss()
                            Toast.makeText(this@UserDashboard,"Item doesn't exists",Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        dialog!!.dismiss()
                        Toast.makeText(this@UserDashboard,""+error.message,Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    fun OnBestDealItemClick(event : BestDealItemClick){
        if(event.bestDealModel != null){

            dialog!!.show()

            FirebaseDatabase.getInstance()
                .getReference("Category")
                .child(event.bestDealModel.category_id!!)
                .addListenerForSingleValueEvent(object : ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            Common.categorySelected = snapshot.getValue(CategoryModel::class.java)

                            // Load Product
                            FirebaseDatabase.getInstance()
                                .getReference("Category")
                                .child(event.bestDealModel.category_id!!)
                                .child("product")
                                .orderByChild("item_id")
                                .equalTo(event.bestDealModel.item_id)
                                .limitToLast(1)
                                .addListenerForSingleValueEvent(object : ValueEventListener{

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if(snapshot.exists()){
                                            for(productSnapshot in snapshot.children){
                                                Common.productSelected = productSnapshot.getValue(
                                                    ProductModel::class.java)
                                                navController.navigate(R.id.nav_product_detail)
                                            }
                                        }
                                        else{
                                            Toast.makeText(this@UserDashboard,"Item doesn't exists",Toast.LENGTH_SHORT).show()
                                        }
                                        dialog!!.dismiss()
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        dialog!!.dismiss()
                                        Toast.makeText(this@UserDashboard,""+error.message,Toast.LENGTH_SHORT).show()
                                    }

                                })
                        }
                        else{
                            dialog!!.dismiss()
                            Toast.makeText(this@UserDashboard,"Item doesn't exists",Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        dialog!!.dismiss()
                        Toast.makeText(this@UserDashboard,""+error.message,Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }
}