import React, { useEffect, useState } from 'react';
import BookService from '../services/BookService';
import BookForm from './BookForm';

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [editing, setEditing] = useState(null);
  const [adding, setAdding] = useState(false);

  const fetchBooks = () => {
    BookService.getAllBooks()
      .then(res => setBooks(res.data))
      .catch(() => alert('Error fetching books.'));
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const handleAdd = (book) => {
    BookService.createBook(book)
      .then(() => {
        fetchBooks();
        setAdding(false);
      }).catch(err => alert(err.response?.data || 'Failed to create book'));
  };

  const handleUpdate = (book) => {
    if (book.id) {
      BookService.updateBook(book.id, book)
        .then(() => {
          fetchBooks();
          setEditing(null);
        }).catch(err => alert(err.response?.data || 'Failed to update book'));
    }
  };

  const handleDelete = (id) => {
    BookService.deleteBook(id)
      .then(() => fetchBooks())
      .catch(() => alert('Failed to delete book.'));
  };

  return (
    <div className="book-container">
      <h2>Book Library</h2>
      {adding ? (
        <BookForm onSubmit={handleAdd} onCancel={() => setAdding(false)} />
      ) : (
        <button onClick={() => setAdding(true)}>Add New Book</button>
      )}

      <ul className="book-list">
        {books.map(book => (
          <li key={book.id}>
            {editing?.id === book.id ? (
              <BookForm
                initialData={editing}
                onSubmit={handleUpdate}
                onCancel={() => setEditing(null)}
              />
            ) : (
              <div className="book-card">
                <strong>{book.name}</strong> by {book.author} ({book.year}) - {book.genre}, {book.pages} pages
                <div className="actions">
                  <button onClick={() => setEditing(book)}>Edit</button>
                  <button onClick={() => handleDelete(book.id)}>Delete</button>
                </div>
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BookList;
