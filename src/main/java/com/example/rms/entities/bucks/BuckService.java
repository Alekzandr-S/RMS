package com.example.rms.entities.bucks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuckService {

    BuckRepository buckRepository;

    public BuckService(BuckRepository buckRepository) {
        this.buckRepository = buckRepository;
    }
    public List<Buck> findAllBucks(String filterText){
        if (filterText == null || filterText.isEmpty()) {
            return buckRepository.findAll();
        } else {
            return buckRepository.search(filterText);
        }
    }

    public List<Buck> getAllBucks() {
       return buckRepository.findAll();
    }
    public long countBuck() {
        return buckRepository.count();
    }
    public void deleteBuck(Buck buck) {
        buckRepository.delete(buck);
    }
    public void saveBuck(Buck buck) {
        if (buck == null) {
            System.err.println("Buck is null.");
            return;
        }
        buckRepository.save(buck);
    }

    public long countBucks() {
        return buckRepository.count();
    }
}
