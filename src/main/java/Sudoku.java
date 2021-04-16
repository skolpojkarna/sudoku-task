import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new ValidationException("args not found");
        }
        List<List<Integer>> listOfRows = new ArrayList<>();

        try (FileReader fileReader = new FileReader(args[0]);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                List<String> strings = Arrays.asList(line.split(","));
                listOfRows.add(validateRow(strings));
            }
        } catch (FileNotFoundException e) {
            throw new ValidationException("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listOfRows.size() != 9) {
            throw new ValidationException("wrong rows quantity");
        }
        validateColumn(listOfRows);
        validateBlocks(listOfRows);

        System.out.println(0);

    }

    private static List<Integer> validateRow(List<String> strings) {
        List<Integer> integers = new ArrayList<>();
        for (String string : strings) {
            Integer i;
            try {
                i = Integer.valueOf(string);
            } catch (NumberFormatException e) {
                throw new ValidationException("not a number");
            }
            if (i <= 0 && i >= 10) {
                throw new ValidationException("number should be between 0 and 10");
            }
            if (integers.contains(i)) {
                throw new ValidationException("duplicates at row");
            }
            integers.add(i);
        }
        if (integers.size()!=9){
            throw new ValidationException("wrong numbers quantity");
        }
        return integers;
    }

    private static void validateColumn(List<List<Integer>> listOfRows) {
        for (int i = 0; i < 9; i++) {
            List<Integer> integers = new ArrayList<>();
            for (List<Integer> list : listOfRows) {
                for (int j = i; j < i + 1; j++) {
                    if (integers.contains(list.get(i))) {
                        throw new ValidationException("duplicates at column");
                    }
                    integers.add(list.get(i));
                }
            }
        }
    }

    private static void validateBlocks(List<List<Integer>> listOfRows) {
        for (int i = 0; i < 9; i = i + 3) {
            List<Integer> integers = new ArrayList<>();
            for (List<Integer> list : listOfRows) {
                for (int j = i; j < i + 3; j++) {
                    if (integers.contains(list.get(j))) {
                        throw new ValidationException("duplicates at block");
                    }
                    integers.add(list.get(j));
                }
                if (integers.size() == 9) {
                    integers.clear();
                }
            }
        }
    }
}
