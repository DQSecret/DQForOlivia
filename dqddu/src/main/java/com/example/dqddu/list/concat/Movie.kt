package com.example.dqddu.list.concat

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

data class Movie(
    val id: Long,
    val cnm: String, val enm: String = "", val posterUrl: String = "",
    val intro: String,
    val actors: List<Actor>
) {

    companion object {
        val simple = Movie(
            1222234,
            "我的姐姐",
            "Sister",
            "http://p0.meituan.net/movie/e2ecb7beb8dadc9f07f2fad9820459f92275588.jpg",
            "一场意外车祸把父母带走，也把素未谋面的亲弟弟带到姐姐的面前。在一系列风波过后，姐姐原本来自原生家庭的伤痛慢慢被治愈，她也成长为更好的自己。",
            List(15) { Actor.getSimple() }
        )
    }

    @Parcelize
    data class Actor(val id: Long, val name: String, val role: String) : Parcelable {

        companion object {
            fun getSimple(): Actor {
                return Actor(Random.nextLong(1, 1000), "演员", "角色")
            }

            val DiffCallback = object : DiffUtil.ItemCallback<Actor>() {

                override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Actor,
                    newItem: Actor
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }

        fun roleFormat() = "饰:${role}"
    }
}
