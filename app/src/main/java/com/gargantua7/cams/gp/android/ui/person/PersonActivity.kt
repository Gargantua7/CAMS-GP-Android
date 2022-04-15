package com.gargantua7.cams.gp.android.ui.person

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.IconRow
import com.gargantua7.cams.gp.android.ui.component.fab.FAB
import com.gargantua7.cams.gp.android.ui.message.MessageActivity
import com.gargantua7.cams.gp.android.ui.repair.RepairActivity
import com.gargantua7.cams.gp.android.ui.util.toIntuitive

class PersonActivity : ExhibitComposeActivity<Person>(), FAB {

    override val id = "Person"

    override val viewModel by lazy { ViewModelProvider(this).get(PersonViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")
        if (id == null) finish()
        Log.d("RepairActivity onCreate", "id = $id")
        viewModel.id.value = id
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.id.value = viewModel.id.value
    }

    @Composable
    override fun exhibitContent(item: Person) {
        val flow by viewModel.repairs.observeAsState()
        val repairs = flow?.collectAsLazyPagingItems()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                PersonContent(item)
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            repairs?.let {
                items(it) { repair ->
                    repair?.let { re -> RepairItem(re) }
                }
            }
        }
    }

    @Composable
    override fun onItemRefresh() {
        viewModel.id.value = viewModel.id.value
    }

    @Composable
    override fun RowScope.rightComponents() {
        val user by CAMSApplication.user.observeAsState()
        val person by viewModel.item.observeAsState()
        user?.let {
            if (it.username == person?.username || it.permission > (person?.permission ?: 0)) {
                IconButton(onClick = {
                    Intent(this@PersonActivity, PersonEditActivity::class.java).let { i ->
                        i.putExtra("id", person?.username)
                        startActivity(i)
                    }
                }) {
                    Icon(Icons.Filled.Edit, "edit", tint = Color.White)
                }
            }
        }
    }

    @Composable
    override fun fab() {
        val user by CAMSApplication.user.observeAsState()
        val person by viewModel.item.observeAsState()
        user?.let {
            if (it.username != person?.username) super.fab(icons = Icons.Filled.Sms)
        }
    }

    override fun fabOnClick(context: ComposeActivity) {
        Intent(this, MessageActivity::class.java).let {
            it.putExtra("id", viewModel.id.value)
            it.putExtra("op", viewModel.item.value?.name)
            startActivity(it)
        }
    }

    @Composable
    fun PersonContent(person: Person) {
        Column(modifier = Modifier.background(MaterialTheme.colors.surface)) {
            Spacer(modifier = Modifier.size(15.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = person.name, fontSize = 36.sp)
                Spacer(modifier = Modifier.width(10.dp))
                if (person.permission > 0) {
                    Icon(Icons.Filled.Verified, "Verified", tint = MaterialTheme.colors.primary)
                } else {
                    if (!person.sex) {
                        Icon(Icons.Filled.Male, "Male", tint = Color.Blue)
                    } else {
                        Icon(Icons.Filled.Female, "Female", tint = Color.Red)
                    }
                }
            }
            IconRow(icon = Icons.Filled.Tag, text = person.username)
            IconRow(icon = Icons.Filled.Groups, text = person.dep)
            IconRow(icon = Icons.Filled.Badge, text = person.title)
            IconRow(icon = Icons.Filled.Apartment, text = "${person.collage} ${person.major}")
            person.phone?.let {
                IconRow(
                    icon = Icons.Filled.Phone, text = it,
                    onClick = {
                        Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it")).also { i ->
                            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(i)
                        }
                    }
                )
            }
            person.wechat?.let {
                IconRow(icon = Icons.Filled.Message, text = it)
            }

        }
    }

    @Composable
    fun RepairItem(repair: Repair) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .clickable {
                    Intent(this, RepairActivity::class.java).let { i ->
                        i.putExtra("id", repair.id)
                        startActivity(i)
                    }
                }
        ) {
            Column(
                modifier = Modifier.padding(15.dp, 10.dp),
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = repair.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
                Row {
                    Text(
                        text = repair.initTime.toIntuitive(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (repair.state) "OPEN" else "CLOSE",
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        Icons.Filled.Lens,
                        "Lens",
                        tint = if (repair.state) Color.Green else Color.Red,
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(0.dp))
                }
            }
        }
    }
}
