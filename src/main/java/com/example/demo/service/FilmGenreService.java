package com.example.demo.service;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.FilmGenreDTO;
import com.example.demo.dto.GenreDTO;
import com.example.demo.entity.FilmGenre;
import com.example.demo.repository.FilmGenreRepository;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmGenreService {
    @Autowired
    private FilmGenreRepository filmGenreRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<FilmGenreDTO> getAllFilmGenres() {
        return filmGenreRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FilmGenreDTO> getFilmGenreById(Integer id) {
        return filmGenreRepository.findById(id)
                .map(this::convertToDTO);
    }

    public FilmGenreDTO createFilmGenre(FilmGenreDTO filmGenreDTO) {
        FilmGenre filmGenre = convertToEntity(filmGenreDTO);
        FilmGenre savedFilmGenre = filmGenreRepository.save(filmGenre);
        return convertToDTO(savedFilmGenre);
    }

    public FilmGenreDTO updateFilmGenre(Integer id, FilmGenreDTO filmGenreDTO) {
        Optional<FilmGenre> existingFilmGenre = filmGenreRepository.findById(id);
        if (existingFilmGenre.isPresent()) {
            FilmGenre filmGenre = existingFilmGenre.get();
            // Assuming we update the relationships if needed
            FilmGenre updatedFilmGenre = filmGenreRepository.save(filmGenre);
            return convertToDTO(updatedFilmGenre);
        }
        return null;
    }

    public boolean deleteFilmGenre(Integer id) {
        if (filmGenreRepository.existsById(id)) {
            filmGenreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private FilmGenreDTO convertToDTO(FilmGenre filmGenre) {
        FilmDTO filmDTO = null;
        if (filmGenre.getFilm() != null) {
            filmDTO = new FilmDTO();
            filmDTO.setIdFilm(filmGenre.getFilm().getIdFilm());
            filmDTO.setTitre(filmGenre.getFilm().getTitre());
        }
        
        GenreDTO genreDTO = null;
        if (filmGenre.getGenre() != null) {
            genreDTO = new GenreDTO(filmGenre.getGenre().getIdGenre(), filmGenre.getGenre().getLibelle());
        }
        
        return new FilmGenreDTO(filmGenre.getId(), filmDTO, genreDTO);
    }

    private FilmGenre convertToEntity(FilmGenreDTO filmGenreDTO) {
        FilmGenre filmGenre = new FilmGenre();
        if (filmGenreDTO.getIdFilm() != null) {
            filmRepository.findById(filmGenreDTO.getIdFilm()).ifPresent(filmGenre::setFilm);
        }
        if (filmGenreDTO.getIdGenre() != null) {
            genreRepository.findById(filmGenreDTO.getIdGenre()).ifPresent(filmGenre::setGenre);
        }
        return filmGenre;
    }
}