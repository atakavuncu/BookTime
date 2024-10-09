package com.atakavuncu.booktime.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakavuncu.booktime.data.model.book.Book
import com.atakavuncu.booktime.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _books.value = emptyList()
            } else {
                _books.value = repository.searchBooks(query)
            }
        }
    }
}