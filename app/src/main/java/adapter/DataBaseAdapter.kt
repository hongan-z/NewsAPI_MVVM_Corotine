package adapter

import Model.Article
import Model.MyDBNews
import android.content.Context
import android.util.Log
import android.view.ContentInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.database_news_item.view.*
import self.tutorial.mynews.databinding.DatabaseNewsItemBinding

class DataBaseAdapter(
    val context: Context):RecyclerView.Adapter<DataBaseAdapter.DBViewHolder>() {

    private lateinit var binding: DatabaseNewsItemBinding
    var onItemClick: ((MyDBNews) -> Unit)? = null


    inner class DBViewHolder():RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBViewHolder {
        binding = DatabaseNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DBViewHolder()

    }

    override fun onBindViewHolder(holder: DBViewHolder, position: Int) {

        val currentlist = differ.currentList[position]
        binding.apply {
            tVtitle.text = currentlist.title
            tVauthor.text = currentlist.author
            tVcontent.text = currentlist.content


            Glide.with(context).load(currentlist.urlImage).into(dbimageView)
           Log.d("db adpter","get:${dbimageView}")
           // Picasso.get().load(currentlist.urlImage).into(dbimageView)
        }

        holder.itemView.icon_delete_item.setOnClickListener {
            onItemClick?.invoke(currentlist)
        }


    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    private val differCall = object :DiffUtil.ItemCallback<MyDBNews>(){
        override fun areItemsTheSame(oldItem: MyDBNews, newItem: MyDBNews): Boolean {
          return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MyDBNews, newItem: MyDBNews): Boolean {
          return oldItem == newItem
        }

    }

    val  differ = AsyncListDiffer(this, differCall)

}