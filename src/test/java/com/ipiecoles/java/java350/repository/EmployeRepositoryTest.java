package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@DataJpaTest
//@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Test
    public void testFindLastMatriculeNull(){

        //Given
        Employe e = new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(e);

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        assertEquals("12345", lastMatricule);
    }

    @Test
    public void testFindLastMatriculeMultiple(){

        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("40325", lastMatricule);
    }
}