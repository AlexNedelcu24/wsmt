import React, { useState } from 'react';

const BookForm = ({ initialData, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState(initialData ?? {
    name: '',
    author: '',
    year: 2024,
    genre: '',
    pages: 100
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'pages' || name === 'year' ? +value : value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="book-form">
      <input name="name" placeholder="Title" value={formData.name} onChange={handleChange} required />
      <input name="author" placeholder="Author" value={formData.author} onChange={handleChange} required />
      <input name="year" type="number" placeholder="Year" value={formData.year} onChange={handleChange} />
      <input name="genre" placeholder="Genre" value={formData.genre} onChange={handleChange} />
      <input name="pages" type="number" placeholder="Pages" value={formData.pages} onChange={handleChange} />
      <button type="submit">Save</button>
      {onCancel && <button type="button" onClick={onCancel}>Cancel</button>}
    </form>
  );
};

export default BookForm;
