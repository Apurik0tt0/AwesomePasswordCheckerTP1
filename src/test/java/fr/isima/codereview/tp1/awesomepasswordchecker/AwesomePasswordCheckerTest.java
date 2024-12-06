/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fr.isima.codereview.tp1.awesomepasswordchecker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anbettoli
 */
public class AwesomePasswordCheckerTest {
  
  public AwesomePasswordCheckerTest() {
  }

  /**
   * Test of getInstance method, of class AwesomePasswordChecker.
   */
  @Test
  public void testGetInstance_File() throws Exception {

    //Test avec un fichier valide
    try {
        // Crée un fichier temporaire avec des données valides
        File validFile = File.createTempFile("valid", ".txt");
        try (FileWriter writer = new FileWriter(validFile)) {
            writer.write("1.0;2.0;3.0\n4.0;5.0;6.0");
        }

        // Vérifie que l'instance est correctement créée
        AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance(validFile);
        assertNotNull("L'instance créée avec un fichier valide ne doit pas être nulle.", checker);

        // Nettoyage
        validFile.delete();
    } catch (IOException e) {
        fail("Le test avec un fichier valide a échoué à cause d'une exception : " + e.getMessage());
    }

    // Test avec un fichier null
    assertThrows("Passer un fichier null devrait lever une NullPointerException.",
        NullPointerException.class, 
        () -> AwesomePasswordChecker.getInstance(null));

    // Test avec un fichier invalide
    File invalidFile = new File("non_existent_file.txt");
    assertThrows("Passer un fichier inexistant devrait lever une IOException.",
        IOException.class, 
        () -> AwesomePasswordChecker.getInstance(invalidFile));
  }

  /**
   * Test of getInstance method, of class AwesomePasswordChecker.
   */
  @Test
  public void testGetInstance_0args() throws Exception {
        try {
            // Appel de la méthode pour obtenir l'instance
            AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();

            // Vérifie que l'instance n'est pas nulle
            assertNotNull("L'instance créée avec un fichier valide ne doit pas être nulle.", checker);

        } catch (IOException e) {
            fail("Le test a échoué à cause d'une exception IOException : " + e.getMessage());
        }
  }

  /**
   * Test of maskAff method, of class AwesomePasswordChecker.
   */
  @Test
  public void testMaskAff() {
    System.out.println("maskAff");
    String password = "eA1!c&.";
    int[] expected = {1, 3, 5, 6, 2, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    
        
    try {
      AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
      int[] result = checker.maskAff(password);
      assertArrayEquals(expected, result);
    } catch (AssertionError e) {
      fail("The test case is a prototype.");
    } catch (Exception e) {
      fail("Instanciation impossible");
    }

    
  }

  /**
   * Test of getDIstance method, of class AwesomePasswordChecker.
   */
  @Test
  public void testGetDIstance() {

        try {
          AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
          
          //cluster1 = {1.0, 2.0, 3.0, 4.0};
          //cluster2 = {2.0, 3.0, 4.0, 5.0};
          double expectedDistance = 0 ;

          String password = "eA1!c&.";        
          double result = checker.getDIstance(password);

          assertEquals("La distance calculée doit correspondre à la distance minimale attendue.", expectedDistance, 0, 100);
        } catch (AssertionError e) {
          
          fail("The test case is a prototype.");
        } catch (Exception e) {
          fail(e.getMessage());
        }
        
  }



  /**
   * Test of computeMD5 method, of class AwesomePasswordChecker.
   */
  @Test
  public void testComputeMD5() {
    System.out.println("computeMD5");
    String input = "myawesomepassword";
    String expResult = "3729ad9ab30ed75be1f22a5f250f07ac";
    String result = AwesomePasswordChecker.computeMD5(input);
    assertEquals(expResult, result);
  }
  
  /**
   * Test de performance de la méthode computeMD5
   */
  @Test
  public void testPerformanceComputeMD5() {
    String input = "myawesomepassword".repeat(1000);
    long startTime = System.nanoTime();
    
    AwesomePasswordChecker.computeMD5(input);
    
    long endTime = System.nanoTime();
    long duration = endTime - startTime;
    
    System.out.println("Durée de computeMD5 pour une entrée longue : " + duration + " nanosecondes");
    
    assertTrue("Le temps d'exécution ne doit pas dépasser 1 seconde", duration < 1_000_000_000);
  }

}
