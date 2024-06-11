package com.example.SZP.tutorialsContorller;

import com.example.SZP.test.Tutorial;
import com.example.SZP.test.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tutorials")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    // Metoda do przekazania wszystkich tutoriali do widoku
    @GetMapping("/all")
    public String showAllTutorials(Model model) {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        model.addAttribute("tutorials", tutorials);
        return "allTutorials"; // Zwróć nazwę szablonu Thymeleaf
    }

    // Metoda do dodawania nowego tutoriala
    @PostMapping("/add")
    public String addTutorial(@ModelAttribute Tutorial tutorial) {
        tutorialRepository.save(tutorial);
        return "redirect:/tutorials/all"; // Przekierowanie na stronę wyświetlającą wszystkie tutoriale
    }

    // Metoda do przekazania formularza dodawania tutoriala
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("tutorial", new Tutorial());
        return "addTutorial";
    }

    // Metoda do aktualizacji istniejącego tutoriala
    @PostMapping("/update/{id}")
    public String updateTutorial(@PathVariable("id") long id, @ModelAttribute Tutorial tutorial) {
        if (tutorialRepository.existsById(id)) {
            tutorial.setId(id);
            tutorialRepository.save(tutorial);
            return "redirect:/tutorials/all"; // Przekierowanie na stronę wyświetlającą wszystkie tutoriale
        } else {
            return "redirect:/tutorials/all"; // W razie błędu przekierowanie na stronę wyświetlającą wszystkie tutoriale
        }
    }

    // Metoda do przekazania formularza edytowania tutoriala
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Optional<Tutorial> tutorial = tutorialRepository.findById(id);
        if (tutorial.isPresent()) {
            model.addAttribute("tutorial", tutorial.get());
            return "updateTutorial";
        } else {
            return "redirect:/tutorials/all"; // Przekierowanie na stronę wyświetlającą wszystkie tutoriale
        }
    }
}
