package com.delet_dis.madmeditation.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageCard(
  @PrimaryKey(autoGenerate = true)
  var id: Long?,

  var imageFilename: String,

  var time: String
)