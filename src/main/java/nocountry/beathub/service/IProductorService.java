package nocountry.beathub.service;

import nocountry.beathub.exception.*;
import nocountry.beathub.model.Artista;
import nocountry.beathub.model.Productor;

import java.util.List;

public interface IProductorService {

    boolean registerProductor(Productor productor)throws ProductorExistException, HibernateOperationException;

    boolean loginUser(String username, String password)throws UsernameNotFoundException, IncorrectPasswordException, HibernateOperationException;


    public List<Productor> findAllProductores();
}
