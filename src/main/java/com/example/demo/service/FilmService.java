package com.example.demo.service;

import com.example.demo.dto.FilmDTO;
import com.example.demo.entity.Film;
import com.example.demo.entity.Langue;
import com.example.demo.entity.Pays;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.LangueRepository;
import com.example.demo.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;
    
    @Autowired
    private LangueRepository languageRepository;
    
    @Autowired
    private PaysRepository paysRepository;

    public List<FilmDTO> getAllFilms() {
        return filmRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FilmDTO> getFilmById(Integer id) {
        return filmRepository.findById(id)
                .map(this::convertToDTO);
    }

    public FilmDTO createFilm(FilmDTO filmDTO) {
        Film film = convertToEntity(filmDTO);
        Film savedFilm = filmRepository.save(film);
        return convertToDTO(savedFilm);
    }

    public FilmDTO updateFilm(Integer id, FilmDTO filmDTO) {
        Optional<Film> existingFilm = filmRepository.findById(id);
        if (existingFilm.isPresent()) {
            Film film = existingFilm.get();
            film.setTitre(filmDTO.getTitre());
            film.setSynopsis(filmDTO.getSynopsis());
            film.setDuree(filmDTO.getDuree());
            
            if (filmDTO.getIdLangueOriginal() != null) {
                Optional<Langue> langue = languageRepository.findById(filmDTO.getIdLangueOriginal());
                langue.ifPresent(film::setLangueOriginal);
            }
            
            if (filmDTO.getIdPays() != null) {
                Optional<Pays> pays = paysRepository.findById(filmDTO.getIdPays());
                pays.ifPresent(film::setPays);
            }
            
            film.setDateSortie(filmDTO.getDateSortie());
            film.setDateAjout(filmDTO.getDateAjout());
            film.setClassificationAge(filmDTO.getClassificationAge());
            
            Film updatedFilm = filmRepository.save(film);
            return convertToDTO(updatedFilm);
        }
        return null;
    }

    public boolean deleteFilm(Integer id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private FilmDTO convertToDTO(Film film) {
        return new FilmDTO(film.getIdFilm(), film.getTitre(), film.getSynopsis(), 
                          film.getDuree(), 
                          film.getLangueOriginal() != null ? film.getLangueOriginal().getIdLangue() : null,
                          film.getPays() != null ? film.getPays().getIdPays() : null,
                          film.getDateSortie(), film.getDateAjout(), film.getClassificationAge());
    }

    private Film convertToEntity(FilmDTO filmDTO) {
        Film film = new Film();
        film.setTitre(filmDTO.getTitre());
        film.setSynopsis(filmDTO.getSynopsis());
        film.setDuree(filmDTO.getDuree());
        
        if (filmDTO.getIdLangueOriginal() != null) {
            Optional<Langue> langue = languageRepository.findById(filmDTO.getIdLangueOriginal());
            langue.ifPresent(film::setLangueOriginal);
        }
        
        if (filmDTO.getIdPays() != null) {
            Optional<Pays> pays = paysRepository.findById(filmDTO.getIdPays());
            pays.ifPresent(film::setPays);
        }
        
        film.setDateSortie(filmDTO.getDateSortie());
        film.setDateAjout(filmDTO.getDateAjout());
        film.setClassificationAge(filmDTO.getClassificationAge());
        
        return film;
    }
}
