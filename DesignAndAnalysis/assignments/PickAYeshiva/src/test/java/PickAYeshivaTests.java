import org.junit.Test;
import org.junit.Assert;
import edu.yu.da.PickAYeshivaBase;
import edu.yu.da.PickAYeshiva;

import java.util.*;
import java.util.Collections;

import static org.junit.Assert.*;
public class PickAYeshivaTests {

    @Test
    public void arraysAreNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            double[] cooking = new double[1];
            cooking[0] = 4;
            PickAYeshiva test = new PickAYeshiva(null, cooking);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            double[] faculty = new double[1];
            faculty[0] = 4;
            PickAYeshiva test = new PickAYeshiva(faculty, null);
        });
    }

    @Test
    public void arraysAreNotSameLength(){
        double[] cooking = new double[1];
        cooking[0] = 4;
        double[] faculty = new double[2];
        faculty[0] = 4;
        faculty[1] = 3;
        assertThrows(IllegalArgumentException.class, () -> {
            PickAYeshiva test = new PickAYeshiva(faculty, cooking);
        });
    }

    @Test
    public void simpleLeffTest(){
        double [] facultyRatioRankings = {0, 1, 2};
        double [] cookingRankings = {3, 2, 1};
        PickAYeshivaBase pay = new PickAYeshiva (facultyRatioRankings, cookingRankings);
        // rearranging to use equals to compare arrays
        Arrays.sort(facultyRatioRankings);
        double [] reversedFaculty = new double[facultyRatioRankings.length];
        double [] reversedCooking = new double[facultyRatioRankings.length];
        for(int i = 0; i < reversedFaculty.length; i++){
            reversedFaculty[i] = facultyRatioRankings[reversedFaculty.length-1-i];
            reversedCooking[i] = cookingRankings[reversedCooking.length-1-i];
        }
        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), reversedFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), reversedCooking));
    }

    @Test
    public void randomMediumTest(){
        double [] facultyRatioRankings = {1, 4, 3, 5, 7, 10, 3, 8, 6, 11, 9, 2, 3, 15, 1};
        double [] cookingRankings = {2, 6, 2, 5, 9, 4, 12, 7, 6, 4, 9, 2, 1, 1, 20};
        PickAYeshivaBase pay = new PickAYeshiva (facultyRatioRankings , cookingRankings);
        // Yeshivas 7, 10, 11, 14, 15 are all valid options
        double [] facultyAnswer = {15, 11, 9, 3, 1}; // ordered the way it comes
        double [] cookingAnswer = {1, 4, 9, 12, 20}; // ordered the way it comes
        for(int i = 0; i < pay.getCookingRankings().length; i++){
            System.out.println("Faculty Ghetto : " + pay.getFacultyRatioRankings()[i]);
            System.out.println("Cooking Ghetto : " + pay.getCookingRankings()[i]);
        }
        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), facultyAnswer));
        assertTrue(Arrays.equals(pay.getCookingRankings(), cookingAnswer));
    }

    @Test
    public void leffTimeTest(){
        // should take roughly 20 seconds
        double[] faculty = new double[28000000];
        double[] cooking = new double[28000000];
        for(int i = 0; i < faculty.length; i++){
            faculty[i] = (Math.random() * (faculty.length - 1) + 1);
            cooking[i] = (Math.random() * (faculty.length - 1) + 1);
        }

        long payStart = System.nanoTime();
        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);
        long payEnd = System.nanoTime();
        long payTime = payEnd - payStart;
        System.out.println("Algorithm Time: " + payTime / 1000000000.0 + "seconds");
        assertTrue((payTime / 1000000000.0) <= 20.0);
    }

    @Test
    public void shouldDebunkMyInitialIdea(){
        // This test must be better than 10000000000 comparisons
        double[] faculty = new double[100000];
        double[] cooking = new double[100000];
        for(int i = 0; i < faculty.length; i++){
            faculty[i] = faculty.length - i;
            cooking[i] = i;
        }
        long bruteForceStart = System.nanoTime();
        // might need to add more meat to this
        for(int j = 0; j < faculty.length; j++){
            for(int k = 0; k < faculty.length; k++){
                if(k != j){
                    boolean check = faculty[j] > faculty[k];
                }
            }
        }
        long bruteForceEnd = System.nanoTime();
        long bruteForceTime = bruteForceEnd - bruteForceStart;
        long payStart = System.nanoTime();
        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);
        long payEnd = System.nanoTime();
        long payTime = payEnd - payStart;
        // no yeshivas are taken off
        // checking lengths because I don't feel like rearranging it all
        assertEquals(faculty.length, pay.getFacultyRatioRankings().length);
        assertEquals(cooking.length, pay.getCookingRankings().length);
        // algorithm beats brute force implementation
        System.out.println("Brute Force Time: " + bruteForceTime / 1000000000.0 + "seconds");
        System.out.println("Algorithm Time: " + payTime / 1000000000.0 + "seconds");
        assertTrue(bruteForceTime > payTime);
    }

    @Test
    public void yeshivaTest(){
        double[] faculty = {5,5};
        double[] cooking = {6,7};

        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);

        double[] newFaculty = pay.getFacultyRatioRankings();
        double[] newCooking = pay.getCookingRankings();
        double[] answerFaculty = {5};
        double[] answerCooking = {7};

        for(int i = 0; i < pay.getCookingRankings().length; i++){
            System.out.println("Faculty Ghetto : " + pay.getFacultyRatioRankings()[i]);
            System.out.println("Cooking Ghetto : " + pay.getCookingRankings()[i]);
        }

        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), answerFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), answerCooking));

    }

    @Test
    public void verySimpleTest(){
        double[] faculty = {5, 4, 2};
        double[] cooking = {6, 7, 2};

        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);

        double[] newFaculty = pay.getFacultyRatioRankings();
        double[] newCooking = pay.getCookingRankings();
        double[] answerFaculty = {5, 4};
        double[] answerCooking = {6, 7};

        for(int i = 0; i < pay.getCookingRankings().length; i++){
            System.out.println("Faculty Ghetto : " + pay.getFacultyRatioRankings()[i]);
            System.out.println("Cooking Ghetto : " + pay.getCookingRankings()[i]);
        }
        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), answerFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), answerCooking));

    }

    @Test
    public void aLittleLessSimpleTest(){
        double[] faculty = {5, 4, 2, 8, 9};
        double[] cooking = {6, 7, 2, 1, 2};

        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);

        double[] newFaculty = pay.getFacultyRatioRankings();
        double[] newCooking = pay.getCookingRankings();
        double[] answerFaculty = {9, 5, 4};
        double[] answerCooking = {2, 6, 7};

        for(int i = 0; i < pay.getCookingRankings().length; i++){
            System.out.println("Faculty Ghetto : " + pay.getFacultyRatioRankings()[i]);
            System.out.println("Cooking Ghetto : " + pay.getCookingRankings()[i]);
        }
        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), answerFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), answerCooking));

    }

    @Test
    public void randomNumbersFromTheFellasExceptNadiv(){
        double[] faculty = {69, 666, 99, 114, 91, 11, 18, 62, 1000, 1114};
        double[] cooking = {420, 18, 34, 82, 64, 79, 44, 68, 114, 7};

        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);

        double[] newFaculty = pay.getFacultyRatioRankings();
        double[] newCooking = pay.getCookingRankings();
        double[] answerFaculty = {1114, 1000, 69};
        double[] answerCooking = {7, 114, 420};

        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), answerFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), answerCooking));
    }

    @Test
    public void sameFacultyScore(){
        double[] faculty = {6, 6, 6, 6, 6};
        double[] cooking = {2, 5, 7, 3, 8};

        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);

        double[] newFaculty = pay.getFacultyRatioRankings();
        double[] newCooking = pay.getCookingRankings();
        double[] answerFaculty = {6};
        double[] answerCooking = {8};

        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), answerFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), answerCooking));
    }

    @Test
    public void sameCookingScore(){
        double[] faculty = {2, 5, 7, 3, 8};
        double[] cooking = {6, 6, 6, 6, 6};

        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);

        double[] newFaculty = pay.getFacultyRatioRankings();
        double[] newCooking = pay.getCookingRankings();
        double[] answerFaculty = {8};
        double[] answerCooking = {6};

        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), answerFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), answerCooking));
    }
    @Test
    public void negativeRanking(){
        double[] faculty = {-2, 5, 7, 3, 8};
        double[] cooking = {6, 4, -6, 0, 1};

        PickAYeshiva pay = new PickAYeshiva(faculty, cooking);

        double[] newFaculty = pay.getFacultyRatioRankings();
        double[] newCooking = pay.getCookingRankings();
        double[] answerFaculty = {8, 5, -2};
        double[] answerCooking = {1, 4, 6};
        for(int i = 0; i < pay.getCookingRankings().length; i++){
            System.out.println("Faculty Ghetto : " + pay.getFacultyRatioRankings()[i]);
            System.out.println("Cooking Ghetto : " + pay.getCookingRankings()[i]);
        }

        assertTrue(Arrays.equals(pay.getFacultyRatioRankings(), answerFaculty));
        assertTrue(Arrays.equals(pay.getCookingRankings(), answerCooking));
    }

    @Test
    public void emptyArrays(){
        assertThrows(IllegalArgumentException.class, () -> {
            double[] faculty = {};
            double[] cooking = {6, 4, -6, 0, 1};
            PickAYeshiva test = new PickAYeshiva(faculty, cooking);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            double[] faculty = {6, 4, -6, 0, 1};
            double[] cooking = {};
            PickAYeshiva test = new PickAYeshiva(faculty, cooking);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            double[] faculty = {};
            double[] cooking = {};
            PickAYeshiva test = new PickAYeshiva(faculty, cooking);
        });
    }

}
