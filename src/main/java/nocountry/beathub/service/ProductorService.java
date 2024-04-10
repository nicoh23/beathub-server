package nocountry.beathub.service;

import nocountry.beathub.exception.*;
import nocountry.beathub.model.Artista;
import nocountry.beathub.model.Productor;
import nocountry.beathub.repository.IProductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductorService implements IProductorService{

    @Autowired
    private IProductorRepository productorRepository;


    @Override
    public boolean registerProductor(Productor productor) throws ProductorExistException, HibernateOperationException {
        if (productorRepository.existsByUsername(productor.getName())) {
            throw new ProductorExistException("Ya se encuentra el cliente : " + productor.getName());
        }

        try {
            productorRepository.save(productor);
        } catch (Exception e) {
            throw new HibernateOperationException("Error interto: " + e.getMessage());
        }


        return true;
    }

    @Override
    public boolean loginUser(String username, String password) throws UsernameNotFoundException, IncorrectPasswordException, HibernateOperationException {
        Optional<Artista> userOptional;
        try {
            userOptional = productorRepository.findByUsername(username);
        } catch (Exception e) {
            throw new HibernateOperationException("Error con hibertane: " + e.getMessage());
        }

        if (userOptional.isPresent()) {
            Artista user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return true;
            } else {
                throw new IncorrectPasswordException("Contraseña incorrecta para el usuario: " + username);
            }
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }

    @Override
    public List<Productor> findAllProductores() {
        List<Productor> productores = productorRepository.findAll();
        return productores;
    }
}
