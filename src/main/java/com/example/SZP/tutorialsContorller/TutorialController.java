package com.example.SZP.tutorialsContorller;

import com.example.SZP.test.Tutorial;
import com.example.SZP.test.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tutorials")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    // Metoda do odczytu wszystkich tutoriali
    @GetMapping
    public ResponseEntity<List<Tutorial>> getAllTutorials() {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    // Metoda do odczytu pojedynczego tutoriala
    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        return tutorialData.map(tutorial -> new ResponseEntity<>(tutorial, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Metoda do dodawania nowego tutoriala
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.save(tutorial);
        return ResponseEntity.status(HttpStatus.CREATED).body(_tutorial);
    }

    // Metoda do aktualizacji istniejącego tutoriala
    @PutMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        if (tutorialRepository.existsById(id)) {
            Tutorial _tutorial = tutorialRepository.save(tutorial);
            return ResponseEntity.ok(_tutorial);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Metoda do usuwania tutoriala
    @DeleteMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Metoda do przekazania wszystkich tutoriali do widoku
    @GetMapping("/all")
    public String showAllTutorials(Model model) {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        model.addAttribute("tutorials", tutorials);
        return "allTutorials.html"; // Zwróć nazwę szablonu Thymeleaf
    }

    // Metoda do dodawania nowego tutoriala
    @PostMapping("/add")
    public String addTutorial(@ModelAttribute Tutorial tutorial) {
        tutorialRepository.save(tutorial);
        return "redirect:/tutorials/all"; // Przekierowanie na stronę wyświetlającą wszystkie tutoriale
    }
}
