package com.app.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.books.models.Genres;

public interface IGenresRepository extends JpaRepository<Genres,String> {

}
