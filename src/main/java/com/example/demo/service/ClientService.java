package com.example.demo.service;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Client;
import com.example.demo.repository.CategorieClientRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CategorieClientRepository categorieClientRepository;

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClientDTO> getClientById(Integer id) {
        return clientRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }

    public ClientDTO updateClient(Integer id, ClientDTO clientDTO) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            client.setNom(clientDTO.getNom());
            client.setPrenom(clientDTO.getPrenom());
            client.setDateNaissance(clientDTO.getDateNaissance());
            client.setEmail(clientDTO.getEmail());
            client.setTelephone(clientDTO.getTelephone());
            if (clientDTO.getCategorieClientId() != null) {
                categorieClientRepository.findById(clientDTO.getCategorieClientId()).ifPresent(client::setCategorieClient);
            } else {
                client.setCategorieClient(null);
            }
            Client updatedClient = clientRepository.save(client);
            return convertToDTO(updatedClient);
        }
        return null;
    }

    public boolean deleteClient(Integer id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ClientDTO convertToDTO(Client client) {
        CategorieClientDTO categorieClient = client.getCategorieClient() != null ? 
            new CategorieClientDTO(client.getCategorieClient().getIdCategorieClient(), 
                                  client.getCategorieClient().getLibelle(),
                                  client.getCategorieClient().getDescription()) : null;
        ClientDTO clientDTO = new ClientDTO(client.getIdClient(), client.getNom(), client.getPrenom(), 
                           client.getDateNaissance(), client.getEmail(), client.getTelephone(), categorieClient);
        clientDTO.setCategorieClientId(client.getCategorieClient() != null ? client.getCategorieClient().getIdCategorieClient() : null);
        return clientDTO;
    }

    private Client convertToEntity(ClientDTO clientDTO) {
        Client client = new Client(clientDTO.getNom(), clientDTO.getPrenom(), clientDTO.getDateNaissance(), 
                                 clientDTO.getEmail(), clientDTO.getTelephone(), null);
        if (clientDTO.getCategorieClientId() != null) {
            categorieClientRepository.findById(clientDTO.getCategorieClientId()).ifPresent(client::setCategorieClient);
        }
        return client;
    }
}
