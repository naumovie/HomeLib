create table user_book (
    user_id int8 not null references usr,
    book_id int8 not null references books,
    primary key (user_id, book_id)
)