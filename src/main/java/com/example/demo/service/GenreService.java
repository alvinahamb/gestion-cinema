package com.example.demo.service;

import com.example.demo.dto.GenreDTO;
import com.example.demo.entity.Genre;
import com.example.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<GenreDTO> getGenreById(Integer id) {
        return genreRepository.findById(id)
                .map(this::convertToDTO);
    }

    public GenreDTO createGenre(GenreDTO genreDTO) {
        Genre genre = convertToEntity(genreDTO);
        Genre savedGenre = genreRepository.save(genre);
        return convertToDTO(savedGenre);
    }

    public GenreDTO updateGenre(Integer id, GenreDTO genreDTO) {
        Optional<Genre> existingGenre = genreRepository.findById(id);
        if (existingGenre.isPresent()) {
            Genre genre = existingGenre.get();
            genre.setLibelle(genreDTO.getLibelle());
            Genre updatedGenre = genreRepository.save(genre);
            return convertToDTO(updatedGenre);
        }
        return null;
    }

    public boolean deleteGenre(Integer id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private GenreDTO convertToDTO(Genre genre) {
        return new GenreDTO(genre.getIdGenre(), genre.getLibelle());
    }

    private Genre convertToEntity(GenreDTO genreDTO) {
        return new Genre(genreDTO.getLibelle());
    }
}
