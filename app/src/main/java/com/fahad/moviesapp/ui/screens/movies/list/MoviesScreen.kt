package com.fahad.moviesapp.ui.screens.movies.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fahad.domain.models.Movie
import com.fahad.moviesapp.ui.core.LoadingView
import com.fahad.moviesapp.ui.screens.movies.MoviesViewModel
import com.fahad.moviesapp.ui.screens.movies.list.item.MovieCard
import com.fahad.moviesapp.ui.screens.movies.state.MoviesUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    goToDetails: (Int) -> Unit
) {

    val viewState: MoviesUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyColumnListState = rememberLazyListState()

    // check if can paginate
    val isScrollToEnd by remember {
        derivedStateOf {
            lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyColumnListState.layoutInfo.totalItemsCount - 1
                    && viewModel.canPaginate
        }
    }

    /**
     * get movies from viewModel
     */

    // listen if pagination allowed
    LaunchedEffect(key1 = isScrollToEnd) {
        if (isScrollToEnd) {
            viewModel.getMovies()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getMovies()
    }

    /**
     * Handle Ui state from flow
     */

    when (val state = viewState) {
        is MoviesUiState.Loading -> LoadingView()
        is MoviesUiState.Success -> {
            HomeContent(
                state = lazyColumnListState,
                movies = state.movies ?: persistentListOf(),
                onMovieClick = goToDetails
            )
        }

        is MoviesUiState.Failed -> {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
    }

}

@Composable
fun HomeContent(
    state: LazyListState,
    movies: ImmutableList<Movie>,
    onMovieClick: (Int) -> Unit
) {
    // State to hold the search query
    var query by remember { mutableStateOf(TextFieldValue("")) }

    // Filter the movie list based on the search query
    val filteredMovies = if (query.text.isEmpty()) {
        movies
    } else {
        movies.filter { movie ->
            movie.title.contains(query.text, ignoreCase = true)
        }
    }

    // Get the first matching suggestion for inline display
    val suggestion = if (filteredMovies.isNotEmpty() && query.text.isNotEmpty()) {
        filteredMovies.first().title
    } else {
        ""
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Search TextField with inline suggestion in light grey
        BasicTextField(
            value = query,
            onValueChange = { newValue ->
                query = newValue
            },
            textStyle = TextStyle(color = Color.Black, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            decorationBox = { innerTextField ->
                Box {
                    if (query.text.isEmpty()) {
                        // Show the search hint when no text is typed
                        Text(
                            text = "Search Movies...",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Display the text field content
                    innerTextField()

                    // Show the suggestion inline in light grey, after the query text
                    if (suggestion.startsWith(query.text, ignoreCase = true) && query.text.isNotEmpty()) {
                        Text(
                            text = AnnotatedString(query.text + suggestion.removePrefix(query.text)),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
            }
        )

        // Movies LazyColumn with padding and spacing
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            state = state
        ) {
            items(filteredMovies, key = { it.id }) { movie ->
                MovieCard(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}

//@Composable
//fun HomeContent(
//    state: LazyListState,
//    movies: ImmutableList<Movie>,
//    onMovieClick: (Int) -> Unit
//) {
//    LazyColumn(
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(15.dp),
//        state = state
//    ) {
//        items(movies,
//            key = { it.id }) { movie ->
//            MovieCard(movie, onMovieClick)
//        }
//    }
//}


@Preview
@Composable
private fun HomeContentPreview() {
    HomeContent(
        state = rememberLazyListState(),
        movies = Movie.previewData.toImmutableList()
    ) {
    }
}