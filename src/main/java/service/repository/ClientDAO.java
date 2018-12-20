package service.repository;

import model.Client;
import model.Phone;

import java.util.List;

public interface ClientDAO {

    List<Client> getAllClients();

    Client get(int id);

    List<Phone> getAllPhonesForClient(int clientId);

    void saveClient(Client client);

    boolean removeClient(int clientId);
}
