package ru.netology.nmedia.dao

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity WHERE isVisible = 1 ORDER BY id DESC")
    fun getAll(): Flow<List<PostEntity>>

    @Query("SELECT COUNT(*) == 0 FROM PostEntity")
    suspend fun isEmpty(): Boolean

    @Query("SELECT COUNT(*) FROM PostEntity")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<PostEntity>)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    suspend fun removeById(id: Long)

    @Query(
        """
             UPDATE PostEntity SET 
             likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
             likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
             WHERE id = :id;
             """
    )
    suspend fun likeById(id: Long)

    @Query("UPDATE PostEntity SET isVisible = :visible")
    suspend fun updateVisibility(visible: Boolean)

}
