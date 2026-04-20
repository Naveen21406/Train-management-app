import java.util.*;
import java.util.stream.Collectors;

/**
 * UC17 - Sort Bogie Names Using Arrangements
 * Demonstrates string sorting and name-based arrangements
 * Data Structure: ArrayList with custom string comparators
 */
public class UC17_SortBogieNamesUsingArrangements {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 17: Sort Bogie Names Using Arrangements");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Sort bogies by name with various arrangement techniques.");
        System.out.println("Data Structure Used: ArrayList with string comparisons");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR017", 15);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create bogies with various names
            String[] bogieData = {
                "B001:Zebra-Express:Passenger:150",
                "B002:Alpha-Coach:Passenger:120",
                "B003:Mumbai-Local:Passenger:100",
                "B004:Delta-Premium:Passenger:140",
                "B005:Beta-Comfort:Passenger:130",
                "B006:express-A:Passenger:110",
                "B007:Cargo-North:Cargo:200",
                "B008:GENERAL-COACH:Passenger:180",
                "B009:sleeper-class:Passenger:80",
                "B010:Arctic-Special:Passenger:160"
            };

            ArrayList<Bogie> allBogies = new ArrayList<>();
            System.out.println("\n✓ Adding bogies to train...");

            for (String data : bogieData) {
                String[] parts = data.split(":");
                Bogie bogie = new Bogie(parts[0], parts[2], Integer.parseInt(parts[3]), parts[1]);
                allBogies.add(bogie);
                train.addBogie(bogie);
            }

            // Display original order
            System.out.println("\n========== SORTING BY NAME ==========");
            System.out.println("\n1. ORIGINAL ORDER:");
            displayBogiesByName(allBogies);

            // Sort 1: Alphabetical ascending
            ArrayList<Bogie> sortAlpha = new ArrayList<>(allBogies);
            sortAlpha.sort((b1, b2) -> b1.getBogieName().compareTo(b2.getBogieName()));
            System.out.println("\n2. ALPHABETICAL ASCENDING (A-Z):");
            displayBogiesByName(sortAlpha);

            // Sort 2: Alphabetical descending
            ArrayList<Bogie> sortAlphaDesc = new ArrayList<>(allBogies);
            sortAlphaDesc.sort((b1, b2) -> b2.getBogieName().compareTo(b1.getBogieName()));
            System.out.println("\n3. ALPHABETICAL DESCENDING (Z-A):");
            displayBogiesByName(sortAlphaDesc);

            // Sort 3: Case-insensitive alphabetical
            ArrayList<Bogie> sortCaseInsensitive = new ArrayList<>(allBogies);
            sortCaseInsensitive.sort((b1, b2) -> 
                b1.getBogieName().compareToIgnoreCase(b2.getBogieName()));
            System.out.println("\n4. CASE-INSENSITIVE ALPHABETICAL:");
            displayBogiesByName(sortCaseInsensitive);

            // Sort 4: By name length then alphabetically
            ArrayList<Bogie> sortByLength = new ArrayList<>(allBogies);
            sortByLength.sort((b1, b2) -> {
                int lengthCompare = Integer.compare(b1.getBogieName().length(), 
                                                    b2.getBogieName().length());
                if (lengthCompare != 0) {
                    return lengthCompare;
                }
                return b1.getBogieName().compareTo(b2.getBogieName());
            });
            System.out.println("\n5. SORTED BY NAME LENGTH THEN ALPHABETICALLY:");
            displayBogiesByNameWithLength(sortByLength);

            // Sort 5: By word count then name
            ArrayList<Bogie> sortByWordCount = new ArrayList<>(allBogies);
            sortByWordCount.sort((b1, b2) -> {
                int words1 = b1.getBogieName().split("-").length;
                int words2 = b2.getBogieName().split("-").length;
                int wordCompare = Integer.compare(words1, words2);
                if (wordCompare != 0) {
                    return wordCompare;
                }
                return b1.getBogieName().compareTo(b2.getBogieName());
            });
            System.out.println("\n6. SORTED BY WORD COUNT (Hyphen-separated) THEN NAME:");
            displayBogiesByWordCount(sortByWordCount);

            // Sort 6: Natural sort (handles numbers better)
            ArrayList<Bogie> sortNatural = new ArrayList<>(allBogies);
            sortNatural.sort(new NaturalOrderComparator());
            System.out.println("\n7. NATURAL ORDER SORT (Number-aware):");
            displayBogiesByName(sortNatural);

            // Sort 7: By first letter then rest
            ArrayList<Bogie> sortByPrefix = new ArrayList<>(allBogies);
            sortByPrefix.sort((b1, b2) -> {
                String name1 = b1.getBogieName();
                String name2 = b2.getBogieName();
                
                Character first1 = Character.toUpperCase(name1.charAt(0));
                Character first2 = Character.toUpperCase(name2.charAt(0));
                
                int firstCompare = first1.compareTo(first2);
                if (firstCompare != 0) {
                    return firstCompare;
                }
                return name1.compareTo(name2);
            });
            System.out.println("\n8. GROUPED BY FIRST LETTER:");
            displayBogiesByFirstLetter(sortByPrefix);

            // Analysis
            System.out.println("\n========== NAME ANALYSIS ==========");
            analyzeNames(allBogies);

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void displayBogiesByName(ArrayList<Bogie> bogies) {
        int index = 1;
        for (Bogie bogie : bogies) {
            System.out.println(String.format("  %2d. %-8s | %-25s | %3d seats",
                    index, bogie.getBogieId(), bogie.getBogieName(), bogie.getCapacity()));
            index++;
        }
    }

    private static void displayBogiesByNameWithLength(ArrayList<Bogie> bogies) {
        int index = 1;
        for (Bogie bogie : bogies) {
            System.out.println(String.format("  %2d. %-8s | %-25s (Len: %2d) | %3d seats",
                    index, bogie.getBogieId(), bogie.getBogieName(), 
                    bogie.getBogieName().length(), bogie.getCapacity()));
            index++;
        }
    }

    private static void displayBogiesByWordCount(ArrayList<Bogie> bogies) {
        int index = 1;
        for (Bogie bogie : bogies) {
            int words = bogie.getBogieName().split("-").length;
            System.out.println(String.format("  %2d. %-8s | %-25s (Words: %d) | %3d seats",
                    index, bogie.getBogieId(), bogie.getBogieName(), words, bogie.getCapacity()));
            index++;
        }
    }

    private static void displayBogiesByFirstLetter(ArrayList<Bogie> bogies) {
        Character currentLetter = null;
        int index = 1;

        for (Bogie bogie : bogies) {
            char firstLetter = Character.toUpperCase(bogie.getBogieName().charAt(0));

            if (currentLetter == null || currentLetter != firstLetter) {
                if (currentLetter != null) {
                    System.out.println();
                }
                System.out.println("  " + firstLetter + ":");
                currentLetter = firstLetter;
                index = 1;
            }

            System.out.println(String.format("    %d. %-8s | %-22s | %3d seats",
                    index, bogie.getBogieId(), bogie.getBogieName(), bogie.getCapacity()));
            index++;
        }
    }

    private static void analyzeNames(ArrayList<Bogie> bogies) {
        System.out.println("Name Statistics:");

        int totalNameLength = bogies.stream()
                .mapToInt(b -> b.getBogieName().length())
                .sum();

        int avgNameLength = totalNameLength / bogies.size();

        int maxNameLength = bogies.stream()
                .mapToInt(b -> b.getBogieName().length())
                .max()
                .orElse(0);

        int minNameLength = bogies.stream()
                .mapToInt(b -> b.getBogieName().length())
                .min()
                .orElse(0);

        System.out.println("  Total Name Length: " + totalNameLength + " characters");
        System.out.println("  Average Name Length: " + avgNameLength + " characters");
        System.out.println("  Max Name Length: " + maxNameLength + " characters");
        System.out.println("  Min Name Length: " + minNameLength + " characters");

        // Most common starting letter
        Map<Character, Long> startLetters = bogies.stream()
                .collect(Collectors.groupingBy(
                        b -> Character.toUpperCase(b.getBogieName().charAt(0)),
                        Collectors.counting()
                ));

        System.out.println("\n  Starting Letters:");
        startLetters.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(e -> System.out.println("    " + e.getKey() + ": " + e.getValue() + " bogies"));

        // Names with hyphens
        long hyphenatedNames = bogies.stream()
                .filter(b -> b.getBogieName().contains("-"))
                .count();
        System.out.println("\n  Hyphenated Names: " + hyphenatedNames + " out of " + bogies.size());

        // Names with spaces
        long spacedNames = bogies.stream()
                .filter(b -> b.getBogieName().contains(" "))
                .count();
        System.out.println("  Names with Spaces: " + spacedNames + " out of " + bogies.size());
    }

    // Natural order comparator for number-aware sorting
    private static class NaturalOrderComparator implements Comparator<Bogie> {
        @Override
        public int compare(Bogie b1, Bogie b2) {
            return naturalCompare(b1.getBogieName(), b2.getBogieName());
        }

        private int naturalCompare(String s1, String s2) {
            int len1 = s1.length();
            int len2 = s2.length();
            int i1 = 0;
            int i2 = 0;

            while (i1 < len1 && i2 < len2) {
                char c1 = s1.charAt(i1);
                char c2 = s2.charAt(i2);

                if (Character.isDigit(c1) && Character.isDigit(c2)) {
                    int num1 = 0;
                    while (i1 < len1 && Character.isDigit(s1.charAt(i1))) {
                        num1 = num1 * 10 + (s1.charAt(i1) - '0');
                        i1++;
                    }

                    int num2 = 0;
                    while (i2 < len2 && Character.isDigit(s2.charAt(i2))) {
                        num2 = num2 * 10 + (s2.charAt(i2) - '0');
                        i2++;
                    }

                    int result = Integer.compare(num1, num2);
                    if (result != 0) {
                        return result;
                    }
                } else {
                    int result = Character.compare(c1, c2);
                    if (result != 0) {
                        return result;
                    }
                    i1++;
                    i2++;
                }
            }

            return Integer.compare(len1, len2);
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
