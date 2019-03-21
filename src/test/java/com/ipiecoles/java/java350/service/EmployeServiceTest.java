package com.ipiecoles.java.java350.service;

        import com.ipiecoles.java.java350.exception.EmployeException;
        import com.ipiecoles.java.java350.model.Employe;
        import com.ipiecoles.java.java350.model.Entreprise;
        import com.ipiecoles.java.java350.model.NiveauEtude;
        import com.ipiecoles.java.java350.model.Poste;
        import com.ipiecoles.java.java350.repository.EmployeRepository;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.*;
        import org.mockito.junit.jupiter.MockitoExtension;

        import javax.persistence.EntityExistsException;
        import java.time.LocalDate;

        import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    public void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //When
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Assertions.assertEquals("T00001",e.getMatricule());
        Assertions.assertEquals(nom,e.getNom());
        Assertions.assertEquals(prenom,e.getPrenom());
        Assertions.assertEquals(LocalDate.now(),e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE,e.getPerformance());
        // 1521,22 * 1,2 * 1.0 = 1825,46
        Assertions.assertEquals(1825.46,(double)e.getSalaire());
        Assertions.assertEquals(tempsPartiel,e.getTempsPartiel());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterLastMatricule00345() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("M00346")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //When
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        //Si employé existe dans la base avec les bonnes infos
        Assertions.assertEquals("M00346",e.getMatricule());
        Assertions.assertEquals(nom,e.getNom());
        Assertions.assertEquals(prenom,e.getPrenom());
        Assertions.assertEquals(LocalDate.now(),e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE,e.getPerformance());
        // 1521,22 * 1,2 * 1.0 = 1825,46
        Assertions.assertEquals(1064.85,(double)e.getSalaire());
        Assertions.assertEquals(tempsPartiel,e.getTempsPartiel());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterLastMatricule99999() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        try {
            Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            //Then
            Assertions.assertEquals("Limite des 100000 matricules atteintes !", e1.getMessage());
        }
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterExistingEmploye() throws EmployeException{

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());

        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("Aurait du planter");
        } catch (EntityExistsException e) {
            Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD", e.getMessage());
        }
    }
}