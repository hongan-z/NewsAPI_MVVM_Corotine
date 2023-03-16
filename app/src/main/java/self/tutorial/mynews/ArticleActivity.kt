package self.tutorial.mynews

import Model.Article
import Model.MyDBNews
import ViewModel.DBViewModel
import ViewModel.MainViewModel
import adapter.ArticleAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.media.ImageReader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import self.tutorial.mynews.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    lateinit var artBinding: ActivityArticleBinding
    lateinit var getArticleAdapter : ArticleAdapter

    val dbviewModel: DBViewModel by viewModels()
    var dbtitle: String = ""
    var dbauthor: String = ""
    var dbcontent: String = ""
    var dbimage:String=""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artBinding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(artBinding.root)


        val article = intent.getParcelableExtra<Article>("Article")
        if(article!=null){
            Log.e("aritcle getPracel","get for main: ${article.title}")

            Glide.with(this).load(article.urlToImage).into(artBinding.imageViewNews)
            artBinding.source.text = article.source.name
            artBinding.tVauthor.text=article.author
            artBinding.tVurl.text=article.url
            artBinding.publishat.text=article.publishedAt
            artBinding.mytitleArti.text=article.title
            artBinding.textViewContent.text=article.content

            // database
            dbtitle= article.title.toString()
            dbauthor = article.author.toString()
            dbcontent = article.content.toString()
            dbimage = article.urlToImage
         

            artBinding.floatingActionButton.setOnClickListener {
                insertNewsToDatbase()


                val intent=Intent(this,SavedListDBActivity::class.java)
                startActivity(intent)

            }

        }


        artBinding.backarrow.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }


    }

    private fun insertNewsToDatbase() {
        Log.e("insert db","get: $dbtitle")
        Log.e("insert db","get : $dbcontent")

        if (inputCheck(dbtitle, dbauthor,dbcontent)){

//            getArticleAdapter.onItemClick={article ->
//                dbtitle= article.title
//                dbauthor = article.author
//                dbcontent = article.content
//                dbimage = article.urlToImage
//            }
//
//            val articl_news = MyDBNews(0, dbtitle, dbauthor,dbcontent,)
//            dbviewModel.addNews(articl_news)
//

            val news = intent.getParcelableExtra<Article>("Article")
            if(news!==null){
                dbtitle = news.title
                dbauthor = news.author
                dbcontent = news.content
                dbimage = news.urlToImage
            }
           Log.e("image article","get image from article ${dbimage}")

           // val insertnew = MyDBNews(100,dbtitle,dbauthor,dbcontent,dbimage)

            val insertnew = MyDBNews(0,dbtitle,dbauthor,dbcontent,dbimage)
            dbviewModel.addNews(insertnew)
            Toast.makeText(this,"Succefull add news to Database!",Toast.LENGTH_LONG).show()


        }else{
            Toast.makeText(this,"Failure insert to DB!",Toast.LENGTH_LONG).show()

        }


    }


    private fun inputCheck(dbtitle: String, dbauthor: String, dbcontent: String): Boolean {
         return !(TextUtils.isEmpty(dbtitle) && TextUtils.isEmpty(dbauthor) && TextUtils.isEmpty(dbcontent))
    }


}







