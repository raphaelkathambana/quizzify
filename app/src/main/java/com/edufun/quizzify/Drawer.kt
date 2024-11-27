package com.edufun.quizzify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edufun.quizzify.ui.theme.Purple40
import kotlinx.coroutines.launch

// AppBar
@Composable
fun AppBar(drawerState: DrawerState, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Row(modifier = modifier
        .fillMaxWidth()
        .background(Purple40),
//        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        IconButton(
            onClick = {
                scope.launch{
                    drawerState.open()
                }
            }) {
            Icon(
                Icons.Outlined.Menu,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
            )
        }
        Text(
            text = "Quizzify",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(10.dp)
        )
    }
}

// Drawer
@Composable
fun DrawerTab(onQuizSelected: (String) -> Unit, onLogout: () -> Unit, onProfile: () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val dockerWidth = LocalConfiguration.current.screenWidthDp * 0.75

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Black,
                drawerTonalElevation = Dp.Hairline,

                modifier = Modifier
                    .width(dockerWidth.dp)
                    .drawWithContent {
                        drawContent()
                        drawLine(
                            color = Color.White,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.3.dp.toPx()
                        )
                    }
            ) {


                Spacer(modifier = Modifier.height(40.dp))
                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(20.dp)


                ) {
                    item {
                        TextButton(
                            onClick = onProfile,
                        ) {
                            Row {
                                Icon(
                                    Icons.Outlined.Person,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp),
                                )
                                Text(
                                    text = "Profile",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(start = 20.dp)
                                )
                            }
                        }
                    }
                    item {
                        TextButton(
                            onClick = onLogout,
                        ) {
                            Row {
                                Icon(
                                    Icons.Outlined.Settings,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp),
                                )
                                Text(
                                    text = "Settings",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(start = 20.dp)
                                )
                            }
                        }
                    }
                    item {
                        TextButton(
                            onClick = onLogout,
                        ) {
                            Row {
                                Icon(
                                    Icons.Outlined.Logout,
                                    tint = Color.White,
                                    contentDescription = null,
                                )
                                Text(
                                    text = "Logout", color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(start = 20.dp)
                                )
                            }
                        }

                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Column{
            AppBar(drawerState)
            MenuScreen(
                onQuizSelected = onQuizSelected,
            )
        }
    }
}
