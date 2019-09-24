import java.util.ArrayList;
import java.util.HashMap;

public class tel {

    public static HashMap<String, ArrayList<String>> hm2 = new HashMap<>();

    public static void add(String surname, String tel) {
        ArrayList<String> numbers = hm2.get(surname);
        if (numbers == null) numbers = new ArrayList<>();
        numbers.add(tel);
        hm2.put(surname, numbers);
    }

    public static ArrayList<String> get(String surname) {
        return hm2.get(surname);
    }
}
