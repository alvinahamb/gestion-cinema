package com.example.demo.service;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.FilmGenreDTO;
import com.example.demo.dto.FilmVersionDTO;
import com.example.demo.dto.GenreDTO;
import com.example.demo.dto.LangueDTO;
import com.example.demo.dto.PaysDTO;
import com.example.demo.entity.Film;
import com.example.demo.entity.FilmGenre;
import com.example.demo.entity.FilmVersion;
import com.example.demo.entity.Genre;
import com.example.demo.entity.Langue;
import com.example.demo.entity.Pays;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.FilmVersionRepository;
import com.example.demo.repository.GenreRepository;
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

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FilmVersionRepository filmVersionRepository;

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
            
            if (filmDTO.getLangueOriginal() != null) {
                Optional<Langue> langue = languageRepository.findById(filmDTO.getLangueOriginal().getIdLangue());
                langue.ifPresent(film::setLangueOriginal);
            }
            
            if (filmDTO.getPays() != null) {
                Optional<Pays> pays = paysRepository.findById(filmDTO.getPays().getIdPays());
                pays.ifPresent(film::setPays);
            }
            
            film.setDateSortie(filmDTO.getDateSortie());
            film.setDateAjout(filmDTO.getDateAjout());
            film.setClassificationAge(filmDTO.getClassificationAge());
            
            // Mise à jour des genres
            if (filmDTO.getFilmGenres() != null && !filmDTO.getFilmGenres().isEmpty()) {
                List<FilmGenre> filmGenres = filmDTO.getFilmGenres().stream()
                        .map(fg -> {
                            FilmGenre fg_entity = new FilmGenre();
                            fg_entity.setFilm(film);
                            fg_entity.setGenre(genreRepository.findById(fg.getGenre().getIdGenre()).orElse(null));
                            return fg_entity;
                        })
                        .filter(fg -> fg.getGenre() != null)
                        .collect(Collectors.toList());
                film.setFilmGenres(filmGenres);
            } else if (filmDTO.getIdGenres() != null && !filmDTO.getIdGenres().isEmpty()) {
                List<FilmGenre> filmGenres = filmDTO.getIdGenres().stream()
                        .map(idGenre -> {
                            FilmGenre fg = new FilmGenre();
                            fg.setFilm(film);
                            fg.setGenre(genreRepository.findById(idGenre).orElse(null));
                            return fg;
                        })
                        .filter(fg -> fg.getGenre() != null)
                        .collect(Collectors.toList());
                film.setFilmGenres(filmGenres);
            }
            
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

    // ========== Film Genres Management ==========
    public List<FilmGenreDTO> getFilmGenresByFilmId(Integer filmId) {
        return filmRepository.findById(filmId)
                .map(film -> film.getFilmGenres().stream()
                        .map(this::convertFilmGenreToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public FilmGenreDTO addGenreToFilm(Integer filmId, Integer genreId) {
        Optional<Film> film = filmRepository.findById(filmId);
        Optional<Genre> genre = genreRepository.findById(genreId);
        
        if (film.isPresent() && genre.isPresent()) {
            FilmGenre filmGenre = new FilmGenre();
            filmGenre.setFilm(film.get());
            filmGenre.setGenre(genre.get());
            // Note: filmGenreRepository should be added to manage this separately
            film.get().getFilmGenres().add(filmGenre);
            filmRepository.save(film.get());
            return convertFilmGenreToDTO(filmGenre);
        }
        return null;
    }

    public boolean removeGenreFromFilm(Integer filmId, Integer genreId) {
        Optional<Film> film = filmRepository.findById(filmId);
        if (film.isPresent()) {
            film.get().getFilmGenres().removeIf(fg -> fg.getGenre().getIdGenre().equals(genreId));
            filmRepository.save(film.get());
            return true;
        }
        return false;
    }

    // ========== Film Versions Management ==========
    public List<FilmVersionDTO> getFilmVersionsByFilmId(Integer filmId) {
        return filmRepository.findById(filmId)
                .map(film -> film.getVersions().stream()
                        .map(this::convertFilmVersionToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public FilmVersionDTO addVersionToFilm(Integer filmId, FilmVersionDTO versionDTO) {
        Optional<Film> film = filmRepository.findById(filmId);
        if (film.isPresent() && versionDTO.getLangue() != null) {
            FilmVersion version = new FilmVersion();
            version.setFilm(film.get());
            version.setSousTitre(versionDTO.getSousTitre());
            if (versionDTO.getLangue() != null) {
                Optional<Langue> langue = languageRepository.findById(versionDTO.getLangue().getIdLangue());
                langue.ifPresent(version::setLangue);
            }
            filmVersionRepository.save(version);
            return convertFilmVersionToDTO(version);
        }
        return null;
    }

    public boolean removeVersionFromFilm(Integer versionId) {
        if (filmVersionRepository.existsById(versionId)) {
            filmVersionRepository.deleteById(versionId);
            return true;
        }
        return false;
    }

    private FilmGenreDTO convertFilmGenreToDTO(FilmGenre filmGenre) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setIdFilm(filmGenre.getFilm().getIdFilm());
        filmDTO.setTitre(filmGenre.getFilm().getTitre());
        
        GenreDTO genreDTO = new GenreDTO(filmGenre.getGenre().getIdGenre(), filmGenre.getGenre().getLibelle());
        
        return new FilmGenreDTO(filmGenre.getId(), filmDTO, genreDTO);
    }

    private FilmVersionDTO convertFilmVersionToDTO(FilmVersion version) {
        FilmVersionDTO dto = new FilmVersionDTO();
        dto.setId(version.getId());
        dto.setSousTitre(version.getSousTitre());
        
        if (version.getLangue() != null) {
            LangueDTO langueDTO = new LangueDTO(version.getLangue().getIdLangue(), 
                                               version.getLangue().getNom(), 
                                               version.getLangue().getCode());
            dto.setLangue(langueDTO);
        }
        
        return dto;
    }
    private FilmDTO convertToDTO(Film film) {
        // Create nested DTOs
        LangueDTO langueOriginal = film.getLangueOriginal() != null ? 
            new LangueDTO(film.getLangueOriginal().getIdLangue(), film.getLangueOriginal().getNom(), film.getLangueOriginal().getCode()) : null;
        PaysDTO pays = film.getPays() != null ? 
            new PaysDTO(film.getPays().getIdPays(), film.getPays().getNom()) : null;
        
        FilmDTO dto = new FilmDTO(film.getIdFilm(), film.getTitre(), film.getSynopsis(), 
                          film.getDuree(), langueOriginal, pays,
                          film.getDateSortie(), film.getDateAjout(), film.getClassificationAge());
        
        // Ajouter les genres via FilmGenreDTO
        if (film.getFilmGenres() != null && !film.getFilmGenres().isEmpty()) {
            List<FilmGenreDTO> filmGenres = film.getFilmGenres().stream()
                    .map(fg -> {
                        GenreDTO genreDTO = new GenreDTO(fg.getGenre().getIdGenre(), fg.getGenre().getLibelle());
                        return new FilmGenreDTO(fg.getId(), dto, genreDTO);
                    })
                    .collect(Collectors.toList());
            dto.setFilmGenres(filmGenres);
        }
        
        // Ajouter les versions via FilmVersionDTO
        if (film.getVersions() != null && !film.getVersions().isEmpty()) {
            List<FilmVersionDTO> versions = film.getVersions().stream()
                    .map(fv -> {
                        LangueDTO langueDTO = new LangueDTO(fv.getLangue().getIdLangue(), fv.getLangue().getNom(), fv.getLangue().getCode());
                        return new FilmVersionDTO(fv.getId(), dto, langueDTO, fv.getSousTitre());
                    })
                    .collect(Collectors.toList());
            dto.setVersions(versions);
        }
        
        return dto;
    }

    private Film convertToEntity(FilmDTO filmDTO) {
        Film film = new Film();
        film.setTitre(filmDTO.getTitre());
        film.setSynopsis(filmDTO.getSynopsis());
        film.setDuree(filmDTO.getDuree());
        
        if (filmDTO.getLangueOriginal() != null) {
            Optional<Langue> langue = languageRepository.findById(filmDTO.getLangueOriginal().getIdLangue());
            langue.ifPresent(film::setLangueOriginal);
        }
        
        if (filmDTO.getPays() != null) {
            Optional<Pays> pays = paysRepository.findById(filmDTO.getPays().getIdPays());
            pays.ifPresent(film::setPays);
        }
        
        film.setDateSortie(filmDTO.getDateSortie());
        film.setDateAjout(filmDTO.getDateAjout());
        film.setClassificationAge(filmDTO.getClassificationAge());
        
        // Ajouter les genres via FilmGenreDTO or idGenres
        if (filmDTO.getFilmGenres() != null && !filmDTO.getFilmGenres().isEmpty()) {
            List<FilmGenre> filmGenres = filmDTO.getFilmGenres().stream()
                    .map(fg -> {
                        FilmGenre fg_entity = new FilmGenre();
                        fg_entity.setFilm(film);
                        fg_entity.setGenre(genreRepository.findById(fg.getGenre().getIdGenre()).orElse(null));
                        return fg_entity;
                    })
                    .filter(fg -> fg.getGenre() != null)
                    .collect(Collectors.toList());
            film.setFilmGenres(filmGenres);
        } else if (filmDTO.getIdGenres() != null && !filmDTO.getIdGenres().isEmpty()) {
            List<FilmGenre> filmGenres = filmDTO.getIdGenres().stream()
                    .map(idGenre -> {
                        FilmGenre fg = new FilmGenre();
                        fg.setFilm(film);
                        fg.setGenre(genreRepository.findById(idGenre).orElse(null));
                        return fg;
                    })
                    .filter(fg -> fg.getGenre() != null)
                    .collect(Collectors.toList());
            film.setFilmGenres(filmGenres);
        }
        
        return film;
    }
}
