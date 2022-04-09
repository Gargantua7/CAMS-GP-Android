package com.gargantua7.cams.gp.android.ui.component.page

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.logic.model.Person

/**
 * @author Gargantua7
 */
abstract class PersonsPage : ListPage<Person>() {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun listItem(person: Person) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .padding(5.dp, 2.5.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp)
                )
                .fillMaxWidth(),
            onClick = { itemOnClick(person, context) }
        ) {
            Column(
                modifier = Modifier.padding(15.dp, 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = person.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    if (person.permission > 0) {
                        Icon(Icons.Filled.Verified, "V", Modifier.size(18.dp), MaterialTheme.colors.primary)
                    }
                }
                if (person.permission > 0) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        if (person.permission in 1..3) {
                            Text(
                                text = person.dep,
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.secondary
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                        Text(
                            text = person.title,
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                }
                Text(
                    text = person.username,
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }

    override fun itemOnClick(item: Person, context: Context) {

    }
}
