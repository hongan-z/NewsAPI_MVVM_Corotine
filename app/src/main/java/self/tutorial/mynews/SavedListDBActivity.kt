package self.tutorial.mynews

import Model.MyDBNews
import ViewModel.DBViewModel


import adapter.DataBaseAdapter
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import self.tutorial.mynews.databinding.ActivitySavedListDbactivityBinding


class SavedListDBActivity : AppCompatActivity() {

    lateinit var getDBadpter: DataBaseAdapter


    lateinit var binding: ActivitySavedListDbactivityBinding
    val delelistViewModel: DBViewModel by viewModels()
    var deletitle: String? = null
    var deleauthor: String? = null
    var delecontent: String? = null
    var deleimage: String? = null
    var isBtnVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedListDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        binding.DBListRecyclerview.adapter = getDBadpter

        delelistViewModel.allnews.observe(this, Observer {
            it?.let {
                getDBadpter.differ.submitList(it)
            }

        })

        swipeToDelete()


        binding.deleAllIcon.setOnClickListener {

            if(isBtnVisible){
                binding.deleAllIcon.visibility = View.INVISIBLE
                binding.tvdeletall.visibility = View.INVISIBLE
                isBtnVisible = false
            }else{
                binding.deleAllIcon.visibility = View.VISIBLE
                binding.tvdeletall.visibility = View.VISIBLE
                isBtnVisible =true
            }

            deleteAllNewsFromDB()

            //startActivity(Intent(this, MainActivity::class.java))
        }



        binding.backarrow.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    private fun deleteAllNewsFromDB() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            delelistViewModel.deleteAll()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All News from DB")
        builder.setMessage("Are you want to delete all news?")
        builder.create().show()

    }


    private fun setAdapter() = binding.DBListRecyclerview.apply {

        layoutManager = LinearLayoutManager(this@SavedListDBActivity)
        getDBadpter = DataBaseAdapter(this@SavedListDBActivity)
        getDBadpter.onItemClick = { mydatanews ->


            delelistViewModel.deleteItemsNews(mydatanews)

        }

        adapter = getDBadpter


    }



    private fun inputCheck(deletitle: String, deleauthor: String, delecontent: String): Boolean {
        return !(TextUtils.isEmpty(deletitle) && TextUtils.isEmpty(deleauthor) && TextUtils.isEmpty(
            delecontent
        ))
    }


    private fun swipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val postion =viewHolder.adapterPosition
                val news_items = getDBadpter.differ.currentList[postion]

                Log.e("On Swiped", "check title : ${news_items.title}")
                Log.e("On Swiped", "check author : ${news_items.author}")

                //val news = MyDBNews(0, news_items.title, news_items.author, news_items.content)
                delelistViewModel.deleteItemsNews(news_items)


                Snackbar.make(
                    binding.dbRootView,
                    "News ' ${news_items.title} ' Deleted",
                    Snackbar.LENGTH_LONG
                ).apply {
                    setAction("Undo"){
                         delelistViewModel.addNews(news_items)
                    }
                    show()
                }


            }

        }).attachToRecyclerView(binding.DBListRecyclerview)
    }


}
