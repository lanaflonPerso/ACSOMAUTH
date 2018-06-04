package fr.laposte.bscc.pi_premiss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.laposte.bscc.pi_premiss.model.User;
import fr.laposte.bscc.pi_premiss.repository.UserRepository;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UserRepository dao;

    @Override
    public Page<User> findPaginated(int page, int size) {
        return dao.findAll(new PageRequest(page, size));
    }

}