package adapter

import Model.Article
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import self.tutorial.mynews.ArticleActivity
import self.tutorial.mynews.databinding.NewsItemBinding

class ArticleAdapter(
    val context: Context
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    var onItemClick: ((Article) -> Unit)? = null

    private lateinit var bindingOfItem: NewsItemBinding


    inner class ViewHolder:RecyclerView.ViewHolder(bindingOfItem.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        bindingOfItem = NewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleItem = differ.currentList[position]
        bindingOfItem.apply {
            tVtitle.text = articleItem.title
            tVdiscrible.text = articleItem.description
            Glide.with(context).load(articleItem.urlToImage).into(imageView)

        }
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(articleItem)
        }

        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

     val differ = AsyncListDiffer(this,differCallback)

}

//
//var onItemClick : ((Article)-> Unit)? = null
//
//inner class articleViewHolder(val binding: NewsElementBinding) : RecyclerView.ViewHolder(binding.root)
//
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): articleViewHolder {
//    return articleViewHolder(
//        NewsElementBinding.inflate(
//            LayoutInflater.from(parent.context),parent,false
//        )
//    )
//}
//
//override fun onBindViewHolder(holder: articleViewHolder, position: Int) {
//    var article = news.articles[position]
//    holder.binding.apply{
//        /*author.text = article.author
//        content.text = article.content
//        description.text = article.description
//        publishedAt.text = article. publishedAt
//        source.text = article.source.toString()
//        url.text = article.url*/
//        source.text = article.source.name
//        title.text = article.title
//        urlToImage.load(article.urlToImage)
//    }
//    holder.itemView.setOnClickListener{
//        onItemClick?.invoke(article)
//    }
//}
//
//override fun getItemCount(): Int {
//    return news.articles.size
//}

