package project.moms.recycler.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.moms.recycler.databinding.ItemMarsBinding
import project.moms.recycler.models.MarsPhoto

class PhotoAdapter(private val onClick: (MarsPhoto) -> Unit) :
    RecyclerView.Adapter<ListPhotoMarsViewHolder>()
{

    private var list: List<MarsPhoto> = emptyList()

    fun setList(data: List<MarsPhoto>) {
        this.list = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPhotoMarsViewHolder {
        val binding = ItemMarsBinding.inflate(LayoutInflater.from(parent.context))
        return ListPhotoMarsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListPhotoMarsViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {

            rover.text = "Rover: ${item.rover.name}"
            camera.text = "Camera: ${item.camera.name}"
            sol.text = "Sol: ${item.sol}"
            date.text = "Date: ${item.earthDate}"

            item.let {
                Glide
                    .with(imageView.context)
                    .load(it.imgSrc)
                    .centerCrop()
                    .into(imageView)
            }
            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}

class ListPhotoMarsViewHolder(val binding: ItemMarsBinding) :
    RecyclerView.ViewHolder(binding.root)

