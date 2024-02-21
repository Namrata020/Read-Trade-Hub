package com.app.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.books.models.Books;

public interface IBooksRepository extends JpaRepository<Books,String> {

}
