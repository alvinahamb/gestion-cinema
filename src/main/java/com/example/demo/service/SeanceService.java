package com.example.demo.service;

import com.example.demo.dto.FilmDTO;
import com.example.demo.dto.FilmVersionDTO;
import com.example.demo.dto.LangueDTO;
import com.example.demo.dto.SalleDTO;
import com.example.demo.dto.SeanceDTO;
import com.example.demo.dto.StatusDTO;
import com.example.demo.dto.TypeStatutDTO;
import com.example.demo.entity.Seance;
import com.example.demo.repository.SeanceRepository;
import com.example.demo.repository.FilmVersionRepository;
import com.example.demo.repository.SalleRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.LangueRepository;
import com.example.demo.repository.PaiementDetailRepository;
import com.example.demo.repository.DiffusionSeanceRepository;
import com.example.demo.repository.FactureSocieteRepository;
import com.example.demo.repository.VenteProduitRepository;
import com.example.demo.entity.FilmVersion;
import com.example.demo.entity.Salle;
import com.example.demo.entity.Status;
import com.example.demo.entity.Film;
import com.example.demo.entity.Langue;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;
    
    @Autowired
    private FilmVersionRepository filmVersionRepository;
    
    @Autowired
    private SalleRepository salleRepository;
    
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LangueRepository langueRepository;

    @Autowired
    private SalleService salleService;

    @Autowired
    private PaiementDetailRepository paiementDetailRepository;

    @Autowired
    private DiffusionSeanceRepository diffusionSeanceRepository;

    @Autowired
    private FactureSocieteRepository factureSocieteRepository;

    @Autowired
    private VenteProduitRepository venteProduitRepository;

    public List<SeanceDTO> getAllSeances() {
        return seanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SeanceDTO> getSeanceById(Integer id) {
        return seanceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<SeanceDTO> searchByFilmTitre(String filmTitre) {
        return seanceRepository.findAll().stream()
                .filter(seance -> seance.getFilmVersion() != null &&
                                seance.getFilmVersion().getFilm() != null &&
                                seance.getFilmVersion().getFilm().getTitre().toLowerCase().contains(filmTitre.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SeanceDTO> searchSeances(String filmTitre, LocalDate dateDebut, LocalTime heureDebut, LocalDate dateFin, LocalTime heureFin) {
        return seanceRepository.findAll().stream()
                .filter(seance -> {
                    // Filtre par titre du film
                    if (filmTitre != null && !filmTitre.trim().isEmpty()) {
                        if (seance.getFilmVersion() == null || seance.getFilmVersion().getFilm() == null ||
                            !seance.getFilmVersion().getFilm().getTitre().toLowerCase().contains(filmTitre.toLowerCase())) {
                            return false;
                        }
                    }

                    // Filtre par date de début
                    if (dateDebut != null && seance.getDateSeance().isBefore(dateDebut)) {
                        return false;
                    }

                    // Filtre par date de fin
                    if (dateFin != null && seance.getDateSeance().isAfter(dateFin)) {
                        return false;
                    }

                    // Filtre par heure de début
                    if (heureDebut != null && seance.getHeureDebut().isBefore(heureDebut)) {
                        return false;
                    }

                    // Filtre par heure de fin
                    if (heureFin != null && seance.getHeureDebut().isAfter(heureFin)) {
                        return false;
                    }

                    return true;
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SeanceDTO createSeance(SeanceDTO seanceDTO) {
        Seance seance = convertToEntity(seanceDTO);
        Seance savedSeance = seanceRepository.save(seance);
        return convertToDTO(savedSeance);
    }

    public SeanceDTO updateSeance(Integer id, SeanceDTO seanceDTO) {
        Optional<Seance> existingSeance = seanceRepository.findById(id);
        if (existingSeance.isPresent()) {
            Seance seance = existingSeance.get();
            seance.setHeureDebut(seanceDTO.getHeureDebut());
            seance.setAvecSousTitre(seanceDTO.getAvecSousTitre());
            seance.setDateSeance(seanceDTO.getDateSeance());
            seance.setTarif(seanceDTO.getTarif());
            
            Seance updatedSeance = seanceRepository.save(seance);
            return convertToDTO(updatedSeance);
        }
        return null;
    }

    public boolean deleteSeance(Integer id) {
        if (seanceRepository.existsById(id)) {
            seanceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ========== Seance Associated Data Management ==========
    public List<SeanceDTO> getSeancesByFilmVersionId(Integer filmVersionId) {
        return seanceRepository.findAll().stream()
                .filter(s -> s.getFilmVersion() != null && s.getFilmVersion().getId().equals(filmVersionId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SeanceDTO> getSeancesBySalleId(Integer salleId) {
        return seanceRepository.findAll().stream()
                .filter(s -> s.getSalle() != null && s.getSalle().getIdSalle().equals(salleId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SeanceDTO updateSeanceWithDetails(Integer id, SeanceDTO seanceDTO) {
        Optional<Seance> existingSeance = seanceRepository.findById(id);
        if (existingSeance.isPresent()) {
            Seance seance = existingSeance.get();
            
            seance.setHeureDebut(seanceDTO.getHeureDebut());
            seance.setAvecSousTitre(seanceDTO.getAvecSousTitre());
            seance.setDateSeance(seanceDTO.getDateSeance());
            seance.setTarif(seanceDTO.getTarif());
            
            // Update FilmVersion
            if (seanceDTO.getFilmVersion() != null) {
                Optional<FilmVersion> filmVersion = filmVersionRepository.findById(seanceDTO.getFilmVersion().getId());
                filmVersion.ifPresent(seance::setFilmVersion);
            }
            
            // Update Salle
            if (seanceDTO.getSalle() != null) {
                Optional<Salle> salle = salleRepository.findById(seanceDTO.getSalle().getIdSalle());
                salle.ifPresent(seance::setSalle);
            }
            
            // Update Status
            if (seanceDTO.getStatus() != null) {
                Optional<Status> status = statusRepository.findById(seanceDTO.getStatus().getIdStatus());
                status.ifPresent(seance::setStatus);
            }
            
            Seance updatedSeance = seanceRepository.save(seance);
            return convertToDTO(updatedSeance);
        }
        return null;
    }
    private SeanceDTO convertToDTO(Seance seance) {
        FilmVersionDTO filmVersionDTO = null;
        if (seance.getFilmVersion() != null) {
            FilmDTO filmDTO = null;
            if (seance.getFilmVersion().getFilm() != null) {
                Film film = seance.getFilmVersion().getFilm();
                filmDTO = new FilmDTO();
                filmDTO.setIdFilm(film.getIdFilm());
                filmDTO.setTitre(film.getTitre());
            }
            
            LangueDTO langueDTO = null;
            if (seance.getFilmVersion().getLangue() != null) {
                Langue langue = seance.getFilmVersion().getLangue();
                langueDTO = new LangueDTO(langue.getIdLangue(), langue.getNom(), langue.getCode());
            }
            
            filmVersionDTO = new FilmVersionDTO(
                seance.getFilmVersion().getId(),
                filmDTO,
                langueDTO,
                seance.getFilmVersion().getSousTitre()
            );
        }
        
        SalleDTO salleDTO = null;
        BigDecimal valeurMaximale = BigDecimal.ZERO;
        if (seance.getSalle() != null) {
            Optional<SalleDTO> salleDTOOpt = salleService.getSalleById(seance.getSalle().getIdSalle());
            if (salleDTOOpt.isPresent()) {
                salleDTO = salleDTOOpt.get();
                valeurMaximale = salleDTO.getValeurMaximale() != null ? salleDTO.getValeurMaximale() : BigDecimal.ZERO;
            } else {
                salleDTO = new SalleDTO();
                salleDTO.setIdSalle(seance.getSalle().getIdSalle());
                salleDTO.setNomSalle(seance.getSalle().getNomSalle());
                salleDTO.setCapacite(seance.getSalle().getCapacite());
                salleDTO.setLigne(seance.getSalle().getLigne());
                salleDTO.setColonne(seance.getSalle().getColonne());
            }
        }
        
        StatusDTO statusDTO = null;
        if (seance.getStatus() != null) {
            TypeStatutDTO typeStatutDTO = new TypeStatutDTO();
            typeStatutDTO.setIdTypeStatut(seance.getStatus().getTypeStatut().getIdTypeStatut());
            typeStatutDTO.setLibelle(seance.getStatus().getTypeStatut().getLibelle());
            
            statusDTO = new StatusDTO(
                seance.getStatus().getIdStatus(),
                seance.getStatus().getLibelle(),
                seance.getStatus().getCode(),
                seance.getStatus().getOrdre(),
                typeStatutDTO
            );
        }
        
        // Calculer le chiffre d'affaires
        BigDecimal chiffreAffaire = paiementDetailRepository.sumMontantBySeanceId(seance.getIdSeance());
        
        // Calculer le montant généré par les diffusions de pub
        BigDecimal montantDiffusionPub = diffusionSeanceRepository.sumMontantDiffusionBySeanceId(seance.getIdSeance());
        
        // Calculer les totaux payés et restants pour les diffusions pub de cette séance
        BigDecimal totalPubPaye = factureSocieteRepository.sumTotalPayeBySeanceId(seance.getIdSeance());
        BigDecimal totalPubRestant = factureSocieteRepository.sumTotalRestantBySeanceId(seance.getIdSeance());
        
        // Calculer le CA des produits vendus pour cette séance
        BigDecimal chiffreAffaireProduits = venteProduitRepository.sumTotalBySeanceId(seance.getIdSeance());
        
        SeanceDTO dto = new SeanceDTO(seance.getIdSeance(), 
                            filmVersionDTO,
                            salleDTO,
                            seance.getHeureDebut(),
                            seance.getAvecSousTitre(),
                            seance.getDateSeance(),
                            seance.getTarif(),
                            statusDTO,
                            valeurMaximale);
        dto.setChiffreAffaire(chiffreAffaire);
        dto.setMontantDiffusionPub(montantDiffusionPub);
        dto.setTotalPubPaye(totalPubPaye);
        dto.setTotalPubRestant(totalPubRestant);
        dto.setChiffreAffaireProduits(chiffreAffaireProduits);
        
        return dto;
    }

    private Seance convertToEntity(SeanceDTO seanceDTO) {
        Seance seance = new Seance();

        // Convertir les relations
        if (seanceDTO.getFilmVersion() != null) {
            System.out.println("DEBUG: FilmVersion ID = " + seanceDTO.getFilmVersion().getId());
            if (seanceDTO.getFilmVersion().getId() != null) {
                Optional<FilmVersion> filmVersionOpt = filmVersionRepository.findById(seanceDTO.getFilmVersion().getId());
                if (filmVersionOpt.isPresent()) {
                    seance.setFilmVersion(filmVersionOpt.get());
                    System.out.println("DEBUG: FilmVersion trouvée et assignée");
                } else {
                    System.out.println("DEBUG: FilmVersion avec ID " + seanceDTO.getFilmVersion().getId() + " NON trouvée");
                    throw new RuntimeException("FilmVersion avec ID " + seanceDTO.getFilmVersion().getId() + " non trouvée");
                }
            } else {
                System.out.println("DEBUG: FilmVersion ID est null");
            }
        } else {
            System.out.println("DEBUG: FilmVersion est null");
        }

        if (seanceDTO.getSalle() != null) {
            System.out.println("DEBUG: Salle ID = " + seanceDTO.getSalle().getIdSalle());
            if (seanceDTO.getSalle().getIdSalle() != null) {
                Optional<Salle> salleOpt = salleRepository.findById(seanceDTO.getSalle().getIdSalle());
                if (salleOpt.isPresent()) {
                    seance.setSalle(salleOpt.get());
                    System.out.println("DEBUG: Salle trouvée et assignée");
                } else {
                    System.out.println("DEBUG: Salle avec ID " + seanceDTO.getSalle().getIdSalle() + " NON trouvée");
                    throw new RuntimeException("Salle avec ID " + seanceDTO.getSalle().getIdSalle() + " non trouvée");
                }
            } else {
                System.out.println("DEBUG: Salle ID est null");
            }
        } else {
            System.out.println("DEBUG: Salle est null");
        }

        if (seanceDTO.getStatus() != null && seanceDTO.getStatus().getIdStatus() != null) {
            Optional<Status> statusOpt = statusRepository.findById(seanceDTO.getStatus().getIdStatus());
            if (statusOpt.isPresent()) {
                seance.setStatus(statusOpt.get());
            } else {
                throw new RuntimeException("Status avec ID " + seanceDTO.getStatus().getIdStatus() + " non trouvé");
            }
        }

        // Copier les propriétés simples
        seance.setHeureDebut(seanceDTO.getHeureDebut());
        seance.setAvecSousTitre(seanceDTO.getAvecSousTitre());
        seance.setDateSeance(seanceDTO.getDateSeance());
        seance.setTarif(seanceDTO.getTarif());

        return seance;
    }
}

