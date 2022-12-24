package com.example.rms.entities.does;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoeService {
    private final DoeRepository doeRepository;

    public DoeService(DoeRepository doeRepository) {
        this.doeRepository = doeRepository;

    }

    public List<Doe> findAllDoes(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return doeRepository.findAll();
        } else {
            return doeRepository.search(filterText);
        }
    }

    public List<Doe> getAllDoes() {
        return doeRepository.findAll();
    }
    public List<Doe> findAll_placeBox(Boolean text) {
        return doeRepository.searchPlaceB(text);
    }
    public List<Doe> findAll_checkPregnancy(Boolean text) {
        return doeRepository.searchP(text);
    }

    public long countDoes() {
        return doeRepository.count();
    }

    public void saveDoe(Doe doe) {
        if (doe == null) {
            System.err.println("Doe is null");
            return;
        }
        doeRepository.save(doe);
    }

    public void deleteDoe(Doe doe) {
        doeRepository.delete(doe);
    }


}
