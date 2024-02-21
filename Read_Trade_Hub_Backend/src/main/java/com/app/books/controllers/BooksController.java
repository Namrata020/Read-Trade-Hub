package com.app.books.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.books.models.Books;
import com.app.books.repositories.IBooksRepository;
import com.app.utils.response.R;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books")
@Tag(name = "default")
@CrossOrigin(origins = "http://localhost:3000")
public class BooksController {
	private static final Logger logger= LoggerFactory.getLogger(BooksController.class);

	@Autowired
	IBooksRepository booksRepository;

	@Operation(summary ="Creates a new books.")
	@PostMapping
	public R<Books> addBook(@RequestBody Books books) {
		try {
			booksRepository.save(books);
		} catch (Exception e) {
			logger.error("Creates a new books fails:" + e.getMessage());
		}

		return new R<Books>().success();
	}

	@Operation(summary ="Update an existing books.")
	@PutMapping("")
	public R<Books> updateBook(@Parameter(description="Update an existing books.")@RequestBody Books books) {
		try {
			booksRepository.save(books);
		} catch (Exception e) {
			logger.error("Update an existing books fails:" + e.getMessage());
		}

		return new R<Books>().success();
	}

	@Operation(summary ="Retrieve an existing Book.")
	@GetMapping("/{id}")
	public R<Books> findBookById(@Parameter(description="A Book's id")@PathVariable String id) {
		Books books = null;
		try {
			books = booksRepository.findById(id).orElse(new Books());
		} catch (Exception e) {
			logger.error("Retrieve an existing books fails:" + e.getMessage());
		}

		return new R<Books>().success().data(books);
	}


	@Operation(summary ="Delete an existing Book.")
	@DeleteMapping(value = "/{id}")
	public R<Books> deleteBook(@Parameter(description="Delete an existing Book.")@RequestParam(value = "id") final String id) {
		try {
			booksRepository.deleteById(id);
		} catch (Exception e) {
			logger.error("Delete an existing Book fails:" + e.getMessage());
		}

		return new R<Books>().success();
	}

	@Operation(summary ="Find the Book list")
	@GetMapping("")
	@ResponseBody
	public R<List<Books>> findBooks() {
		List<Books> booksList = null;
		try {
			booksList = booksRepository.findAll();

		} catch (Exception e) {
			logger.error("Find the Book list fails:" + e.getMessage());
		}

		return new R<List<Books>>().success().data(booksList);
	}
	
}