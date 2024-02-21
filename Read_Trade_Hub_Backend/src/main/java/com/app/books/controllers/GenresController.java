package com.app.books.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.books.models.Genres;
import com.app.books.repositories.IGenresRepository;
import com.app.utils.response.R;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/genres")
@Tag(name = "default")
@CrossOrigin(origins = "http://localhost:3000")
public class GenresController {
	private static final Logger logger= LoggerFactory.getLogger(GenresController.class);

	@Autowired
	IGenresRepository genresRepository;

	@Operation(summary ="Find the genres list")
	@GetMapping("")
	@ResponseBody
	public R<List<Genres>> findGenres() {
	List<Genres> genresList = null;
		try {
			genresList = genresRepository.findAll();

		} catch (Exception e) {
			logger.error("Find the genres list fails:" + e.getMessage());
		}

		return new R<List<Genres>>().success().data(genresList);
	}
	
}