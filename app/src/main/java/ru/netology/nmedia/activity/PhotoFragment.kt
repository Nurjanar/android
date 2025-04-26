package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentPhotoBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PhotoFragment : Fragment() {
    private fun loadPhoto(name: String, image: ImageView) {
        if (name.isNotBlank()) {
            Glide.with(image)
                .load("${BuildConfig.BASE_URL}/media/${name}")
                .error(R.drawable.ic_photo_24dp)
                .timeout(10_000)
                .into(image)
        } else {
            image.setImageResource(R.drawable.ic_photo_24dp)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPhotoBinding.inflate(layoutInflater, container, false)
        val viewModel: PostViewModel by activityViewModels()

        val postId = arguments?.idArg?.toLong()

        viewModel.data.observe(viewLifecycleOwner) { state ->
            if (state.posts.isNotEmpty()) {
                val post = state.posts.find { it.id == postId }
                if (post != null) {
                    val photoName = post.attachment?.url?.trim().toString()
                    loadPhoto(photoName, binding.image)
                }
            }
        }

        return binding.root
    }

    companion object {
        var Bundle.idArg by StringArg
    }

}