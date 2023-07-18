import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dataapiexercise.databinding.ListPhoneItemBinding
import com.example.dataapiexercise.database.PhoneSong
import androidx.recyclerview.widget.RecyclerView

class PhoneSongAdapter(private val songs: List<PhoneSong>) :
    RecyclerView.Adapter<PhoneSongAdapter.PhoneSongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneSongViewHolder {
        val binding = ListPhoneItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhoneSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneSongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song, position)
    }

    override fun getItemCount(): Int = songs.size

    inner class PhoneSongViewHolder(private val binding: ListPhoneItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: PhoneSong, position: Int) {
            binding.position.text = (position+1).toString()
            binding.songName.text = song.name
            binding.phoneArtist.text = song.artist
        }
    }
}
