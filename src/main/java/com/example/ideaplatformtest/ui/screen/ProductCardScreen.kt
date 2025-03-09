package com.example.ideaplatformtest.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.ideaplatformtest.R
import com.example.ideaplatformtest.ui.ProductCardViewModel
import com.example.ideaplatformtest.ui.theme.ColorBackgroundMain
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductCardScreen(
    modifier: Modifier = Modifier,
    productCardViewModel: ProductCardViewModel = koinViewModel()
) {
    var searchName by rememberSaveable { mutableStateOf("") }
    val productCardItems by productCardViewModel.uiState.collectAsState()

    if (searchName != "") {
        productCardViewModel.filterData(searchName.trim())
    } else {
        productCardViewModel.fetchData()
    }

    Column(
        modifier = Modifier
            .background(ColorBackgroundMain)
            .padding(horizontal = dimensionResource(R.dimen.size_s)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_s)),
    ) {
        Spacer(Modifier.padding(top = dimensionResource(R.dimen.size_xxs)))

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = searchName,
            onValueChange = { searchName = it },
            label = {
                Text(
                    color = Color.DarkGray,
                    text = stringResource(R.string.product_search)
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            trailingIcon = {
                if (searchName != "") {
                    IconButton(
                        onClick = {
                            searchName = ""
                        },
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
            }
        )

        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(productCardItems) { productCardItem ->
                ProductCardItemScreen(
                    productCardItem = productCardItem,
                    onDelete = { item -> productCardViewModel.deleteProductCardItem(item) },
                    onChangeAmount = { productCardItemId, newAmount ->
                        productCardViewModel.changeAmountProductCardItem(
                            productCardItemId,
                            newAmount
                        )
                    }
                )
                Spacer(Modifier.padding(top = dimensionResource(R.dimen.size_xxs)))
            }
        }
    }
}