package com.example.demo.service;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.FilmVersionDTO;
import com.example.demo.dto.LangueDTO;
import com.example.demo.entity.Film;
import com.example.demo.entity.FilmVersion;
import com.example.demo.entity.Langue;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.FilmVersionRepository;
import com.example.demo.repository.LangueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmVersionService {
    @Autowired
    private FilmVersionRepository filmVersionRepository;
    
    @Autowired
    private FilmRepository filmRepository;
    
    @Autowired
    private LangueRepository langueRepository;

    public List<FilmVersionDTO> getAllFilmVersions() {
        return filmVersionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FilmVersionDTO> getFilmVersionById(Integer id) {
        return filmVersionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<FilmVersionDTO> getFilmVersionsByFilmId(Integer filmId) {
        return filmVersionRepository.findAll().stream()
                .filter(fv -> fv.getFilm() != null && fv.getFilm().getIdFilm().equals(filmId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FilmVersionDTO createFilmVersion(FilmVersionDTO filmVersionDTO) {
        FilmVersion filmVersion = convertToEntity(filmVersionDTO);
        FilmVersion savedFilmVersion = filmVersionRepository.save(filmVersion);
        return convertToDTO(savedFilmVersion);
    }

    public FilmVersionDTO updateFilmVersion(Integer id, FilmVersionDTO filmVersionDTO) {
        Optional<FilmVersion> existingFilmVersion = filmVersionRepository.findById(id);
        if (existingFilmVersion.isPresent()) {
            FilmVersion filmVersion = existingFilmVersion.get();
            
            if (filmVersionDTO.getIdFilm() != null) {
                filmRepository.findById(filmVersionDTO.getIdFilm())
                    .ifPresent(filmVersion::setFilm);
            }
            
            if (filmVersionDTO.getIdLangue() != null) {
                langueRepository.findById(filmVersionDTO.getIdLangue())
                    .ifPresent(filmVersion::setLangue);
            }
            
            filmVersion.setSousTitre(filmVersionDTO.getSousTitre());
            FilmVersion updatedFilmVersion = filmVersionRepository.save(filmVersion);
            return convertToDTO(updatedFilmVersion);
        }
        return null;
    }

    public boolean deleteFilmVersion(Integer id) {
        if (filmVersionRepository.existsById(id)) {
            filmVersionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private FilmVersionDTO convertToDTO(FilmVersion filmVersion) {
        FilmDTO filmDTO = null;
        if (filmVersion.getFilm() != null) {
            Film film = filmVersion.getFilm();
            filmDTO = new FilmDTO();
            filmDTO.setIdFilm(film.getIdFilm());
            filmDTO.setTitre(film.getTitre());
        }
        
        LangueDTO langueDTO = null;
        if (filmVersion.getLangue() != null) {
            Langue langue = filmVersion.getLangue();
            langueDTO = new LangueDTO(langue.getIdLangue(), langue.getNom(), langue.getCode());
        }
        
        return new FilmVersionDTO(
            filmVersion.getId(),
            filmDTO,
            langueDTO,
            filmVersion.getSousTitre()
        );
    }

    private FilmVersion convertToEntity(FilmVersionDTO filmVersionDTO) {
        Film film = null;
        if (filmVersionDTO.getIdFilm() != null) {
            film = filmRepository.findById(filmVersionDTO.getIdFilm()).orElse(null);
        }
        
        Langue langue = null;
        if (filmVersionDTO.getIdLangue() != null) {
            langue = langueRepository.findById(filmVersionDTO.getIdLangue()).orElse(null);
        }
        
        return new FilmVersion(film, langue, filmVersionDTO.getSousTitre());
    }
}
