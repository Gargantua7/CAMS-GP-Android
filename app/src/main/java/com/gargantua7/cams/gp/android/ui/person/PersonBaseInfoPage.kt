package com.gargantua7.cams.gp.android.ui.person

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gargantua7.cams.gp.android.ui.component.compose.BoxTextField
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.util.matchPhone
import com.gargantua7.cams.gp.android.ui.util.matchWechat

/**
 * @author Gargantua7
 */
class PersonBaseInfoPage(private val viewModel: PersonBaseInfoViewModel, private val parent: ComposeActivity) {

    @Composable
    fun draw() {
        Name()
        Sex()
        Collage()
        Major()
        Tel()
    }

    @Composable
    fun Name() {
        BoxTextField(
            text = viewModel.name,
            onValueChange = { viewModel.name = it },
            placeholder = "Name",
            leadIcon = Icons.Filled.Person
        )
    }

    @Composable
    fun Sex() {
        Spacer(modifier = Modifier.height(10.dp))
        Row(Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = viewModel.sex != null && !viewModel.sex!!,
                onClick = { viewModel.sex = false },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary
                )
            )
            Icon(Icons.Filled.Male, "Male", tint = Color.Blue)
            RadioButton(
                selected = viewModel.sex != null && viewModel.sex!!,
                onClick = { viewModel.sex = true },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary
                )
            )
            Icon(Icons.Filled.Female, "Female", tint = Color.Red)
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Collage() {
        viewModel.getCollageList(parent.viewModel)
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            BoxTextField(
                text = viewModel.collage?.name ?: "",
                placeholder = "Collage",
                leadIcon = Icons.Filled.Apartment,
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                viewModel.majorMap.keys.forEach {
                    DropdownMenuItem(onClick = {
                        viewModel.collage = it
                        viewModel.major = null
                        expanded = false
                    }) {
                        Text(text = it.name)
                    }
                }
            }
        }
        if (viewModel.collage != null) Major()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Major() {
        viewModel.getMajorList(parent.viewModel)
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            BoxTextField(
                text = viewModel.major?.name ?: "",
                placeholder = "Major",
                leadIcon = Icons.Filled.Workspaces,
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                viewModel.majorMap[viewModel.collage]?.forEach {
                    DropdownMenuItem(onClick = {
                        viewModel.major = it
                        expanded = false
                    }) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }

    @Composable
    fun Tel() {
        BoxTextField(
            text = viewModel.phone,
            onValueChange = { viewModel.phone = it },
            placeholder = "Phone (Not Required)",
            leadIcon = Icons.Filled.Phone,
            isError = !matchPhone(viewModel.phone)
        )
        BoxTextField(
            text = viewModel.wechat,
            onValueChange = { viewModel.wechat = it },
            placeholder = "Wechat (Not Required)",
            leadIcon = Icons.Filled.Message,
            isError = !matchWechat(viewModel.wechat)
        )
    }

}
