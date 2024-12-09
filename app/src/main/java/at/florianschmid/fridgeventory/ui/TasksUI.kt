package at.florianschmid.fridgeventory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.florianschmid.fridgeventory.data.Item
import at.florianschmid.fridgeventory.ui.add.TaskAddScreen
import at.florianschmid.fridgeventory.ui.edit.TaskEditScreen
import at.florianschmid.fridgeventory.ui.theme.Black
import at.florianschmid.fridgeventory.ui.theme.Purple80
import at.florianschmid.fridgeventory.ui.theme.Typography

enum class ContactRoutes(val route: String) {
    Home("task/home"),
    Detail("task/details/{contactId}"),
    Edit("task/details/{contactId}/edit"),
    Add("task/new")
}

@Composable
fun TodoApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ContactRoutes.Home.route,
        modifier = modifier
    ) {
        composable(ContactRoutes.Home.route) {
            ContactsHomeScreen(
                onEditClick = {
                    navController.navigate(ContactRoutes.Edit.route.replace("{contactId}", "$it"))
                },
                onAddClick = {
                    navController.navigate(ContactRoutes.Add.route) // Navigate to Add route
                },
                onCardClick = {
                    navController.navigate(ContactRoutes.Detail.route.replace("{contactId}", "$it"))
                }
            )
        }
        composable(
            route = ContactRoutes.Detail.route,
            arguments = listOf(navArgument("contactId") {
                type = NavType.IntType
            })
        ) {
            TaskDetailsScreen()
        }

        composable(
            route = ContactRoutes.Add.route
        ) {
            TaskAddScreen(){
                navController.navigateUp()
            }
        }

        composable(
            route = ContactRoutes.Edit.route,
            arguments = listOf(navArgument("contactId") {
                type = NavType.IntType
            })
        ) {
            TaskEditScreen() {
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun ContactsHomeScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onEditClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onCardClick: (Int) -> Unit
) {
    val state by taskViewModel.tasksUiState.collectAsStateWithLifecycle()

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // We use lazy column for dynamic lists or large lists
        // it will only draw the visible contacts
        Text("Tasks", style = Typography.titleLarge)

        Spacer(Modifier.height(16.dp))

        Row(Modifier.align(Alignment.End)) {
            IconButton(onAddClick) {
                Icon(Icons.Default.Add, "Add task", tint = Black)
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(state.items) { index, task ->
                TaskListItem(task,
                onCardClick = {
                    onCardClick(task.id)
                },
                onEditClick = {
                    onEditClick(task.id)
                },
                onCheckedChange = { isChecked ->
                        taskViewModel.onTaskCheckedChanged(task, isChecked)
                    },

                )
            }
        }
    }
}

@Composable
fun TaskListItem(item: Item, onCardClick: () -> Unit,
                 onEditClick: ()->Unit,
                 onCheckedChange: (Boolean) -> Unit,
                 modifier: Modifier = Modifier) {
    OutlinedCard(
        onClick = { onCardClick() }, modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = Purple80,)    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(item.name, style = Typography.headlineMedium, color = Black)
            Row(){
                Checkbox(
                    checked = item.status,
                    onCheckedChange = onCheckedChange
                )
                IconButton(onEditClick) {
                    Icon(Icons.Outlined.Edit, "Edit task", tint = Black)
                }
            }
        }
    }
}

@Composable
fun TaskDetailsScreen(modifier: Modifier = Modifier, taskUpdateViewModel: TaskUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val detailUiState by taskUpdateViewModel.detailUiState.collectAsStateWithLifecycle()

    TaskDetails(detailUiState.item, modifier)
}


@Composable
fun TaskDetails(item: Item, modifier: Modifier = Modifier) {
    OutlinedCard(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(item.name, style = Typography.headlineMedium)
            Column {
                Text("Description: ${item.desc}", style = Typography.headlineSmall)
                Spacer(Modifier.width(20.dp))
                Text("Due Date: ${item.dueDate}", style = Typography.headlineSmall)
            }
        }
    }
}

@Preview
@Composable
private fun TaskDetailsPreview() {
    TaskDetails(Item(0, "Task1", "this is a short description", "20.11.2024",false))
}

@Preview
@Composable
private fun TaskListItemPreview() {
    TaskListItem(Item(0, "Task1", "this is a short task description", "20.11.2024",false), {}, {}, {})
}