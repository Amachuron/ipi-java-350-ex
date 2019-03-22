package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    public EmployeService employeService;

    @Autowired
    public EmployeRepository employeRepository;

    @Test
    public void testIntegrationEmbaucheEmploye() throws Exception{

        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //Given avec de vraies données d'entrées
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When avec appel des vraies méthodes de repository...
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        Employe e = employeRepository.findByMatricule("T12346");

        //Then avec de vraies vérifications...
        Assertions.assertNotNull(e);
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals("T12346", e.getMatricule());
        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
        //1521.22 * 1.2 * 1.0
        Assertions.assertEquals(1825.46, (double) e.getSalaire());
    }
}