package org.snakeplanner.service;

import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.snakeplanner.dao.SnakeUserDao;
import org.snakeplanner.entity.SnakeUser;


@ApplicationScoped
public class SnakeUserService {

    @Inject SnakeUserDao snakeUserDao;

    
    public void saveUser(SnakeUser user) {        
        snakeUserDao.update(user);
    }

    public SnakeUser getUserById(UUID id) { 
        Optional<SnakeUser> optionalUser =  snakeUserDao.findById(id);  
        
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }else {
            throw new NotFoundException();    
        }
    }
        

    public void deleteUserById(UUID id) {
        snakeUserDao.delete(id);
    }
    
}
