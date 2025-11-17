package com.example.randomuser_test_task.feature.userslist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.randomuser_test_task.R

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = stringResource(id = R.string.user_avatar),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}
