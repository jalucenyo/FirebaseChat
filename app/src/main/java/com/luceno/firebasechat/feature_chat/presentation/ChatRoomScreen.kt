package com.luceno.firebasechat.feature_chat.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.luceno.firebasechat.R
import com.luceno.firebasechat.feature_chat.domain.models.Message
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ChatRoomScreen (
    viewModel: ChatRoomViewModel = hiltViewModel(),
    onSingOut: () -> Unit,
) {

    val state by viewModel.state
    val messages = viewModel.messages

    val selectImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){ imageUri ->
        viewModel.selectedUploadImage(imageUri?.toString()!!)
    }

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.title )) },
            actions = {
                IconButton(onClick = { viewModel.onSingOut(onSingOut) }) {
                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Bar Menu")
                }
            }
        ) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            PreviewImage(
                image = state.uploadImageUri,
                isVisible = state.isShowUploadImage,
                isLoading = state.isSending,
                modifier = Modifier.weight(1f),
                onClose = { viewModel.closeUploadImage() }
            )

            PreviewImage(
                image = state.imageMessageUri,
                isVisible = state.isShowImageMessage,
                onClose = { viewModel.closeShowImageMessage() }
            )

            if(!state.isShowUploadImage) {
                LazyColumn(
                    reverseLayout = true,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(end = 16.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(messages) { it ->
                        if (viewModel.isCurrentUser(it))
                            MessageItemOwner(
                                message = it,
                                onClick = { messageClicked ->
                                    viewModel.showImageOfMessage(messageClicked.image)
                                })
                        else
                            MessageItemOther(
                                message = it,
                                onClick = { messageClicked ->
                                    viewModel.showImageOfMessage(messageClicked.image)
                                })
                    }
                }
            }

            BottomBar(
                onSend = viewModel::sendMessage,
                onSelectImage = { selectImage.launch("image/*") },
                sendEnable = viewModel.isSendEnabled(),
                onMessageChange = viewModel::onInputMessageChange,
                message = state.message
            )
        }
    }
}

@Composable
fun BottomBar(
    message: String,
    sendEnable: Boolean,
    onMessageChange: (String)-> Unit,
    onSend: () -> Unit,
    onSelectImage: () -> Unit
)
{
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text),
            value = message,
            onValueChange = onMessageChange )

        IconButton(onClick = onSend, enabled = sendEnable) {
            Icon(imageVector = Icons.Default.Send,
                contentDescription = "Send message")
        }
        IconButton(onClick = onSelectImage) {
            Icon(imageVector = Icons.Default.Image,
                contentDescription = "Select image")
        }

    }
}

@Composable
fun PreviewImage(
    image: String?,
    isLoading: Boolean = false,
    isVisible: Boolean = true,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
){
    if(isVisible) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxHeight()
                .fillMaxWidth()
                .composed { modifier }
        ) {
            if(isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            GlideImage(
                imageModel = image,
                contentScale = ContentScale.Fit,
                modifier = Modifier.alpha(if(isLoading) 0.2f else 1f)
            )
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close,
                    tint = Color.White,
                    contentDescription = "Close Preview Image")
            }
        }
    }
}


@Composable
fun MessageItemOwner(
    onClick: (message: Message) -> Unit,
    message: Message,
){
    Column() {
        Text(
            text = message.displayName.split(" ")[0],
            modifier = Modifier.align(alignment = Alignment.End)
        )
        MessageItem(
            message = message,
            onClick = onClick,
            color =  MaterialTheme.colors.primary,
            modifier = Modifier.padding(start = 96.dp)
        )
    }

}

@Composable
fun MessageItemOther(
    onClick: (message: Message) -> Unit,
    message: Message,
){
    Column() {
        Text(text = message.displayName.split(" ")[0])
        MessageItem(
            message = message,
            onClick = onClick,
            directionLeft = true,
            color = Color.LightGray,
            modifier = Modifier.padding(end = 96.dp))
    }

}

@Composable
fun MessageItem(
    message: Message,
    color: Color,
    directionLeft: Boolean = false,
    onClick: (message: Message) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        shape = RoundedCornerShape(
            topStart = if(directionLeft) 8.dp else 28.dp,
            topEnd = if(directionLeft) 28.dp else 8.dp ,
            bottomStart = 28.dp,
            bottomEnd = 28.dp
        ),
        backgroundColor = color,
        modifier = modifier
    ) {
        Column {
            GlideImage(
                imageModel = message.image,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .clickable { onClick(message) }
            )
            if(message.message?.isNotBlank() == true) {
                Text(
                    text = message.message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                )
            }
        }

    }
}

