package com.app.books.repositories;

import com.app.books.models.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorsRepository extends JpaRepository<Authors, String>{

}
