package appSpring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import appSpring.repository.GenreRepository;

@Service
public class GenreService {

	@Autowired
	private GenreRepository repository;
}
