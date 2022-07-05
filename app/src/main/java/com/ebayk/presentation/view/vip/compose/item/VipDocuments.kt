package com.ebayk.presentation.view.vip.compose.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ebayk.R
import com.ebayk.data.network.response.ad.model.Document
import com.ebayk.presentation.view.common.compose.constants.IMAGE_SIZE_SMALL
import com.ebayk.presentation.view.common.compose.constants.PADDING_HALF
import com.ebayk.presentation.view.common.compose.constants.PADDING_MEDIUM
import com.ebayk.presentation.view.common.compose.constants.PADDING_STANDARD
import com.ebayk.presentation.view.common.compose.item.BodyText
import com.ebayk.presentation.view.common.compose.item.ItemDivider

@Composable
internal fun VipDocuments(documents: List<Document>, onClick: (link: String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(PADDING_STANDARD.dp)
    ) {
        VipTitle(title = stringResource(id = R.string.vip_documents_section_header))
        ItemDivider(
            modifier = Modifier
                .padding(top = PADDING_STANDARD.dp)
        )
        documents.forEachIndexed { index, document ->
            DocumentItem(document) {
                onClick(it)
            }
            if (index != documents.lastIndex) ItemDivider()
        }
    }
}

@Composable
private fun DocumentItem(document: Document, onClick: (link: String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = PADDING_MEDIUM.dp)
            .clickable { onClick(document.link) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_document),
            contentDescription = null,
            modifier = Modifier
                .size(IMAGE_SIZE_SMALL.dp)
        )
        BodyText(
            text = document.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = PADDING_HALF.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.chevron),
            contentDescription = null,
            modifier = Modifier
                .size(IMAGE_SIZE_SMALL.dp)
        )
    }
}