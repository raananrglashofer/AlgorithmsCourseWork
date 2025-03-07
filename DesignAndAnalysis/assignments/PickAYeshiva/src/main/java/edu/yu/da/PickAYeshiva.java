package edu.yu.da;

import java.util.*;

public class PickAYeshiva extends PickAYeshivaBase{
    private double[] facultyRatioRankings;
    private double[] cookingRatioRankings;
    private List<Yeshiva> allYeshivas = new ArrayList<>();
    private List<Yeshiva> validYeshivaChoices = new ArrayList<>();
    private class Yeshiva{      // implements Comparable<Yeshiva>
        private double facultyRanking;
        private double cookingRanking;
        public Yeshiva(double facultyRanking, double cookingRanking){
            this.facultyRanking = facultyRanking;
            this.cookingRanking = cookingRanking;
        }

        @Override
        public boolean equals(Object obj){
            if(this == obj){
                return true;
            }
            if(obj == null || getClass() != obj.getClass()){
                return false;
            }

            Yeshiva otherYeshiva = (Yeshiva) obj;
            return (facultyRanking == otherYeshiva.facultyRanking && cookingRanking == otherYeshiva.cookingRanking);
        }

        @Override
        public int hashCode() { // maybe change the hash function and don't use objects
            return Objects.hash(facultyRanking, cookingRanking);
        }
    }
    /** Constructor which supplies the yeshiva rankings in terms of two factors
     * of interest.  The constructor executes a divide-and-conquer algorithm to
     * determine the minimum number of yeshiva-to-yeshiva comparisons required to
     * make a "which yeshiva to attend" decision.  The getters can be accessed in
     * O(1) time after the constructor executes successfully.
     *
     * It is the client's responsibility to ensure that no pair of
     * facultyRatioRankings and cookingRankings values are duplicates.
     *
     * @param facultyRatioRankings Array whose ith element is the value of the
     * ith yeshiva with respect to its faculty-to-student ratio (Rabbeim etc).
     * Client maintains ownership.  Can't be null and must be same length as the
     * other parameter.
     * @param cookingRankings Array whose ith element is the value of the ith
     * yeshiva with respect to the quality of the cooking.  Client maintains
     * ownership.  Can't be null and must be same length as other parameter.
     * @throws IllegalArgumentException if pre-conditions are violated.
     */
    public PickAYeshiva(double[] facultyRatioRankings, double[] cookingRankings) {
        super(facultyRatioRankings, cookingRankings);
        //long startTime = System.nanoTime();
        if(facultyRatioRankings == null || cookingRankings == null || facultyRatioRankings.length != cookingRankings.length){
            throw new IllegalArgumentException();
        }
        if(facultyRatioRankings.length == 0 || cookingRankings.length == 0){
            throw new IllegalArgumentException();
        }
        // make all Yeshivas
        for(int i = 0; i < facultyRatioRankings.length; i++){
            Yeshiva yeshiva = new Yeshiva(facultyRatioRankings[i], cookingRankings[i]);
            this.allYeshivas.add(yeshiva);
        }
        //long addYeshivaTime = System.nanoTime();
        //long afterAddingTime = addYeshivaTime - startTime;
        //System.out.println("After Adding Yeshiva Time: " + afterAddingTime / 1000000000.0 + " seconds");
        Comparator<Yeshiva> comparator = new Comparator<>() {
            // sorts by faculty ranking and then tiebreaker is cooking ranking
            // sorts in descending order
            @Override
            public int compare(Yeshiva y1, Yeshiva y2) {
                if(y1.facultyRanking == y2.facultyRanking){
                    if(y1.cookingRanking > y2.cookingRanking){
                        return -1;
                    } else{
                        return 1;
                    }
                }
                if(y1.facultyRanking > y2.facultyRanking){
                    return -1;
                } else{
                    return 1;
                }
            }
        };
        Collections.sort(this.allYeshivas, comparator);
        //long sortTime = System.nanoTime();
        //long afterSortingTime = sortTime - startTime;
        //System.out.println("After Sorting By Faculty Time: " + afterSortingTime / 1000000000.0 + " seconds");
        List<Yeshiva> sortedList = mergeSort(this.allYeshivas);
        //long postAlgorithm = System.nanoTime();
        //long afterMerge = postAlgorithm - startTime;
        //System.out.println("After Merging Time: " + afterMerge / 1000000000.0 + " seconds");
        for(int i = 0; i < sortedList.size(); i++){
            if(sortedList.get(i) != null){
                this.validYeshivaChoices.add(sortedList.get(i));
            }
        }
//        Yeshiva yeshiva = allYeshivas.get(0); // best faculty yeshiva
//        this.validYeshivaChoices.add(yeshiva);
//        for(int j = 1; j < allYeshivas.size(); j++){
//            if(yeshiva.cookingRanking <= allYeshivas.get(j).cookingRanking){
//                checkAgainstOtherYeshivas(allYeshivas.get(j));
//            }
//        }
        // put scores in arrays
        this.facultyRatioRankings = new double[validYeshivaChoices.size()];
        this.cookingRatioRankings = new double[validYeshivaChoices.size()];
        for(int k = 0; k < validYeshivaChoices.size(); k++){
            this.facultyRatioRankings[k] = validYeshivaChoices.get(k).facultyRanking;
            this.cookingRatioRankings[k] = validYeshivaChoices.get(k).cookingRanking;
        }
        //long done = System.nanoTime();
        //long allDone = done - startTime;
        //System.out.println("Finished Time: " + allDone / 1000000000.0 + "seconds");
    }

    private List<Yeshiva> mergeSort(List<Yeshiva> yeshivas) {
        if (yeshivas == null || yeshivas.size() <= 1) { // might delete
            return yeshivas; // Already sorted
        }

        int middle = yeshivas.size() / 2;
        List<Yeshiva> left = new ArrayList<>(yeshivas.subList(0, middle));
        List<Yeshiva> right = new ArrayList<>(yeshivas.subList(middle, yeshivas.size()));

        // Recursive calls for left and right halves
        left = mergeSort(left);
        right = mergeSort(right);

        // Merge the sorted halves
        return merge(left, right);
    }


    private List<Yeshiva> merge(List<Yeshiva> left, List<Yeshiva> right) {
        List<Yeshiva> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if(left.get(i) == null){
                i++;
            } else if(right.get(j) == null){
                j++;
            } else if (left.get(i).cookingRanking < right.get(j).cookingRanking) {
                merged.add(left.get(i++));
            } else {
                merged.add(null); // Set smaller element to null
                j++;
            }
        }

        // Set remaining elements of left list to null, if any
        while (i < left.size()) {
            merged.add(left.get(i++));
            //i++;
        }

        // Copy remaining elements of right list, if any
        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }


    // (left.get(i).facultyRanking == right.get(i).facultyRanking && left.get(i).cookingRanking < right.get(j).cookingRanking)
    //                    || (left.get(i).facultyRanking != right.get(i).facultyRanking && left.get(i).cookingRanking <= right.get(j).cookingRanking)

//    private void checkAgainstOtherYeshivas(Yeshiva yeshiva){
//        for(Yeshiva yesh : this.validYeshivaChoices){
//            if(yeshiva.facultyRanking <= yesh.facultyRanking && yeshiva.cookingRanking <= yesh.cookingRanking){
//                return; // there is a yeshiva that beats it
//            }
//        }
//        // if not beaten then it's valid
//        this.validYeshivaChoices.add(yeshiva);
//    }
    /** Returns an array of yeshiva faculty ranking ratio values that MUST be
     * evaluated (along with the yeshiva's cooking rankings) to make the best
     * "which yeshiva to attend" decision.
     *
     * @return An array, that together with the other getter, represents the
     * MINIMUM set of yeshivos that must be evaluated.  The ith element of this
     * array MUST BE associated with the ith element of the other getter's array.
     * //@see getCookingRankings
     */
    @Override
    public double[] getFacultyRatioRankings() {
        return this.facultyRatioRankings;
    }

    /** Returns an array of yeshiva cooking ranking values that MUST be evaluated
     * (along with the yeshiva's faculty ratio rankings) to make the best "which
     * yeshiva to attend" decision.
     *
     * @return An array, that together with the other getter, represents the
     * MINIMUM set of yeshivos that must be evaluated.  The ith element of this
     * array MUST BE associated with the ith element of the other getter's array.
     */// @see getFacultyRatioRankings
    // *
    @Override
    public double[] getCookingRankings() {
        return this.cookingRatioRankings;
    }
}
