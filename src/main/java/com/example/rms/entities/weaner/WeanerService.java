package com.example.rms.entities.weaner;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeanerService {
    WeanerRepository weanerRepository;

    public WeanerService(WeanerRepository weanerRepository) {
        this.weanerRepository = weanerRepository;
    }

    public List<Weaner> findAll() {
        return weanerRepository.findAll();
    }
    public List<Weaner> findAllWeaner(String filterText){
        if (filterText == null || filterText.isEmpty()) {
            return weanerRepository.findAll();
        } else {
            return weanerRepository.search(filterText);
        }
    }
    public long countWeaner() {
        return weanerRepository.count();
    }
    public void deleteWeaner(Weaner weaner) {
        weanerRepository.delete(weaner);
    }
    public void saveWeaner(Weaner weaner) {
        if (weaner == null) {
            System.err.println("Weaner is null.");
            return;
        }
        weanerRepository.save(weaner);
    }
}
