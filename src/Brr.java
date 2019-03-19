import com.google.gson.Gson;

public class Brr {
    public static void main(String[] args) {
        int arr[][][] = new int[3][2][1];
        Gson gson = new Gson();
        String s = gson.toJson(arr);
        System.out.println(s);
    }
}
