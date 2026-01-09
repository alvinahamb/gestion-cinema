package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Client;
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
        return new ClientDTO(client.getIdClient(), client.getNom(), client.getPrenom(), 
                           client.getDateNaissance(), client.getEmail(), client.getTelephone());
    }

    private Client convertToEntity(ClientDTO clientDTO) {
        return new Client(clientDTO.getNom(), clientDTO.getPrenom(), clientDTO.getDateNaissance(), 
                         clientDTO.getEmail(), clientDTO.getTelephone());
    }
}
