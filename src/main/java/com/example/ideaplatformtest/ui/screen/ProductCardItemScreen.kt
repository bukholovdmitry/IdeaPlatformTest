package com.example.ideaplatformtest.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ideaplatformtest.R
import com.example.ideaplatformtest.data.ProductCardItem
import com.example.ideaplatformtest.ui.theme.ColorAlertDialog
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCardItemScreen(
    productCardItem: ProductCardItem,
    onDelete: (productCardItem: ProductCardItem) -> Unit,
    onChangeAmount: (productCardItemId: Int, newAmount: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = dimensionResource(R.dimen.size_s))
            .shadow(
                elevation = dimensionResource(R.dimen.size_xxs),
                shape = RoundedCornerShape(dimensionResource(R.dimen.size_s))
            )
            .background(Color.White, shape = RoundedCornerShape(dimensionResource(R.dimen.size_s)))

    ) {
        var isDeleteClicked by rememberSaveable { mutableStateOf(false) }
        var isChangeAmountClicked by rememberSaveable { mutableStateOf(false) }

        if (isDeleteClicked) {
            AlertDialogDeleteProductCardItem(onConfirmation = {
                onDelete(productCardItem)
                isDeleteClicked = false
            }, onDismissRequest = { isDeleteClicked = false })
        }

        if (isChangeAmountClicked) {
            AlertDialogChangeAmountProductCardItem(
                onConfirmation = { newAmount ->
                    onChangeAmount(productCardItem.id, newAmount)
                    isChangeAmountClicked = false
                },
                onDismissRequest = { isChangeAmountClicked = false },
                amount = productCardItem.amount
            )
        }

        Row(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.size_s))
        ) {
            Text(
                modifier = Modifier.padding(dimensionResource(R.dimen.size_s)),
                text = productCardItem.name,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(R.dimen.font_size_title).value.sp
            )
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            IconButton(onClick = {
                isChangeAmountClicked = true
            }) {
                Icon(
                    Icons.Filled.Create,
                    contentDescription = "",
                    modifier = Modifier,
                    tint = Color.Blue
                )
            }
            IconButton(onClick = {
                isDeleteClicked = true
            }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "",
                    modifier = Modifier,
                    tint = Color.Red
                )
            }
        }

        FlowRow(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.size_s))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_xxs)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_xxs))
        ) {
            productCardItem.tags.forEach { tag ->
                AssistChip(
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.size_xxs)),
                    onClick = {},
                    label = { Text(tag) },
                )
            }
        }

        val itemModifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.size_s),
                vertical = dimensionResource(R.dimen.size_xxs)
            )
            .weight(0.5f)

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.size_s)),
            maxItemsInEachRow = 2,
        ) {
            Text(
                modifier = itemModifier,
                text = stringResource(R.string.in_storage),
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(R.dimen.font_size_body).value.sp
            )
            Text(
                modifier = itemModifier,
                text = stringResource(R.string.date_added),
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(R.dimen.font_size_body).value.sp,
            )
            Text(
                modifier = itemModifier,
                text = productCardItem.amount.toString(),
                fontSize = dimensionResource(R.dimen.font_size_body).value.sp,
            )

            Text(
                modifier = itemModifier,
                text = SimpleDateFormat("dd.MM.yyyy").format(Date(productCardItem.time)),
                fontSize = dimensionResource(R.dimen.font_size_body).value.sp,
            )
        }
    }
}

@Composable
fun AlertDialogDeleteProductCardItem(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(icon = {
        Icon(Icons.Default.Warning, contentDescription = "")
    }, title = {
        Text(text = stringResource(R.string.product_card_item_delete_dialog_title))
    }, text = {
        Text(text = stringResource(R.string.product_card_item_delete_dialog_text))
    }, onDismissRequest = {

    }, confirmButton = {
        TextButton(onClick = {
            onConfirmation()
        }) {
            Text(stringResource(R.string.yes))
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text(stringResource(R.string.no))
        }
    })
}

@Composable
fun AlertDialogChangeAmountProductCardItem(
    onDismissRequest: () -> Unit, onConfirmation: (newAmount: Int) -> Unit, amount: Int
) {
    var changedAmount by rememberSaveable { mutableIntStateOf(amount) }

    AlertDialog(icon = {
        Icon(Icons.Default.Settings, contentDescription = "")
    }, title = {
        Text(text = stringResource(R.string.product_card_item_change_amount_dialog_title))
    }, text = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (changedAmount > 0) {
                    changedAmount--
                }
            }) {
                Icon(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.size_2xxl))
                        .height(dimensionResource(R.dimen.size_2xxl))
                        .border(
                            dimensionResource(R.dimen.size_xxs),
                            color = ColorAlertDialog,
                            shape = RoundedCornerShape(32.dp)
                        ),
                    painter = painterResource(R.drawable.remove),
                    contentDescription = "",
                    tint = ColorAlertDialog,
                )
            }
            Text(
                modifier = Modifier.padding(dimensionResource(R.dimen.size_xxl)),
                text = changedAmount.toString(),
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.font_size_alert_dialog).value.sp,
            )
            IconButton(onClick = { changedAmount++ }) {
                Icon(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.size_2xxl))
                        .height(dimensionResource(R.dimen.size_2xxl))
                        .border(
                            dimensionResource(R.dimen.size_xxs),
                            color = ColorAlertDialog,
                            shape = RoundedCornerShape(dimensionResource(R.dimen.size_2xxl))
                        ),
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = ColorAlertDialog,
                )
            }

        }
    }, onDismissRequest = {

    }, confirmButton = {
        TextButton(onClick = {
            onConfirmation(changedAmount)
        }) {
            Text(stringResource(R.string.accept))
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text(stringResource(R.string.cancel))
        }
    })
}