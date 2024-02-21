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

import com.app.books.models.Authors;
import com.app.books.repositories.IAuthorsRepository;
import com.app.utils.response.R;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "default")
@CrossOrigin("*")
public class AuthorsController {
	private static final Logger logger= LoggerFactory.getLogger(AuthorsController.class);

	@Autowired
	IAuthorsRepository authorsRepository;

	@Operation(summary ="Creates a new author.")
	@PostMapping
	public R<Authors> addAuthor(@RequestBody Authors authors) {
		try {
			authorsRepository.save(authors);
		} catch (Exception e) {
			logger.error("Creates a new authors fails:" + e.getMessage());
		}

		return new R<Authors>().success();
	}

	@Operation(summary ="Update an existing authors.")
	@PutMapping("")
	public R<Authors> updateAuthor(@Parameter(description="Update an existing authors.")@RequestBody Authors authors) {
		try {
			authorsRepository.save(authors);
		} catch (Exception e) {
			logger.error("Update an existing authors fails:" + e.getMessage());
		}

		return new R<Authors>().success();
	}

	@Operation(summary ="Retrieve an existing Author.")
	@GetMapping("/{id}")
	public R<Authors> findAuthorById(@Parameter(description="A Author's id")@PathVariable String id) {
		Authors authors = null;
		try {
			authors = authorsRepository.findById(id).orElse(new Authors());
		} catch (Exception e) {
			logger.error("Retrieve an existing authors fails:" + e.getMessage());
		}

		return new R<Authors>().success().data(authors);
	}


	@Operation(summary ="Delete an existing Author.")
	@DeleteMapping(value = "/{id}")
	public R<Authors> deleteAuthor(@Parameter(description="Delete an existing Author.")@RequestParam(value = "id") final String id) {
		try {
			authorsRepository.deleteById(id);
		} catch (Exception e) {
			logger.error("Delete an existing Author fails:" + e.getMessage());
		}

		return new R<Authors>().success();
	}

	@Operation(summary ="Find the Author list")
	@GetMapping("")
	@ResponseBody
	public R<List<Authors>> findAuthors() {
		List<Authors> authorsList = null;
		try {
			authorsList = authorsRepository.findAll();

		} catch (Exception e) {
			logger.error("Find the Author list fails:" + e.getMessage());
		}

		return new R<List<Authors>>().success().data(authorsList);
	}
	
}