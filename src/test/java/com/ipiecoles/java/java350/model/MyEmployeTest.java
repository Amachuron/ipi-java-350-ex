package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class MyEmployeTest {

    @Test
    public void testGetNombreAnneeAncienneteDateEmbaucheNull() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, (int) nbAnneeAnciennete);
    }

    @Test
    public void getNombreAnneeAncienneteNminus2() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, (int)anneeAnciennete);
    }


    @ParameterizedTest
    @CsvSource({
            "5, 'ddd', 2, 1.0, 1.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);
    }
}