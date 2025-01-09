package com.vr.stringgeneratorcp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.vr.stringgeneratorcp.db.StringsDataBase
import com.vr.stringgeneratorcp.model.RandomTextData
import com.vr.stringgeneratorcp.ui.theme.StringGeneratorCpTheme
import com.vr.stringgeneratorcp.viewModel.MainViewModel
import com.vr.stringgeneratorcp.viewModel.MainViewmodelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            StringsDataBase::class.java,
            name = "random_string_db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val myViewModelFactory =
            MainViewmodelFactory(application, db)
        viewModel = ViewModelProvider(this, myViewModelFactory).get(MainViewModel::class.java)
        setContent {
            StringGeneratorCpTheme {
                ScaffoldWithTopBar()
            }
        }

    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScaffoldWithTopBar() {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("String Generator")
                    }
                )
            },
        ) { innerPadding ->
            MainScreen(innerPadding)
        }
    }

    @Composable
    fun MainScreen(innerPadding: PaddingValues) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputFieldWithButton()
            LoadList()
        }

    }

    @Composable
    fun LoadList() {
        var stringsList by remember {
            mutableStateOf(listOf<RandomTextData>())
        }
        viewModel.getGeneratedString().observe(this) {
            stringsList = it
            if (it.isNotEmpty()) {
                viewModel.deleteDuplicateRecords()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 15.dp, 16.dp, 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(stringsList) { data ->
                    Column(modifier = Modifier.padding(5.dp)) {
                        Text(text = "String: ${data.value}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "String Length: ${data.length}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Created on: ${data.created}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    viewModel.delete(data)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) { Text("Delete") }
                        }

                        HorizontalDivider(Modifier.padding(6.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun InputFieldWithButton() {
        var mText by remember {
            mutableStateOf("")
        }
        val mContext = LocalContext.current
        val focusManager = LocalFocusManager.current

        Column() {
            TextField(value = mText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                maxLines = 1,
                placeholder = {
                    Text("Enter String Count")
                },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { mText = it },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "sdsd",
                        modifier = Modifier
                            .offset(x = 10.dp)
                            .clickable {
                                mText = ""
                            }
                    )
                }

            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 0.dp, 15.dp, 0.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    validateInput(mContext, mText)
                },
            ) { Text("Generate String") }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.deletAll()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) { Text("Delete Records") }
        }

    }

    private fun validateInput(context: Context, value: String) {
        if (value.isEmpty()) {
            Toast.makeText(context, "Please string limit", Toast.LENGTH_SHORT).show()
        } else if (value.length > 3) {
            Toast.makeText(context, "Please string limit less than 999", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.generateString(value.toInt()).observe(this, { data ->
                data.randomText?.let {
                    viewModel.update(it)
                }
            })
        }
    }


}

