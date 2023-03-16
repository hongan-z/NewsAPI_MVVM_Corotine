package self.tutorial.mynews

import ViewModel.MainViewModel
import adapter.ArticleAdapter
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import self.tutorial.mynews.databinding.ActivityMainBinding
import utils.Events

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    val viewModel: MainViewModel by viewModels()
    var input = "sports"
    var sortby: String = "relevancy"
    lateinit var getAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.myFavorite.setOnClickListener {
            val intent = Intent(this,SavedListDBActivity::class.java)
            startActivity(intent)
        }

        setAdapter()
        binding.newRecyclerview.adapter = getAdapter

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.relevantRadio -> sortby = "relevancy"
                R.id.popularRadio -> sortby = "popularity"
                R.id.recentRadio -> sortby = "publishedAt"
            }
        }



        viewModel.get(input,sortby)
        binding.NewsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var input = query
                viewModel.get(query,sortby)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //viewModel.get(newText.toString())
                return true
            }
        })


        if (input == "") {
            binding.newsprogressBar.visibility = View.INVISIBLE
        }

        viewModel.allListof.observe(this, Observer {
            when (it) {
                is Events.Loading -> {
                    binding.newsprogressBar.visibility = View.VISIBLE
                }

                is Events.Success -> {
                    binding.newsprogressBar.visibility = View.GONE
                    it.let {
                        it.data?.let { it1->
                              getAdapter.differ.submitList(it1.articles)
                        }

                    }
                }

                is Events.Error -> {
                    it.let {
                        binding.rcvMsg.text = it.msg.toString()
                    }
                }

                else -> {

                }
            }
        })


    }

    private fun setAdapter() = binding.newRecyclerview.apply {

        layoutManager = LinearLayoutManager(this@MainActivity)

        getAdapter = ArticleAdapter(this@MainActivity)

        getAdapter.onItemClick ={
            val intent = Intent(this@MainActivity, ArticleActivity::class.java)
            Log.e(" data","get my title : ${it.title.toString()}")
            Log.e(" data","get my author  : ${it.author}")
            Log.e(" data","get my content  : ${it.content}")
            Log.e(" data","get my publisheAt : ${it.publishedAt}")
            intent.putExtra("Article",it)
            startActivity(intent)
        }

        adapter = getAdapter
    }



}


