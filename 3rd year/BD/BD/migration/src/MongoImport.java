import java.io.*;


public class MongoImport {
    private static String bd = "testesclinicos";

    public static File[] finder(String dirName){
        File dir = new File(dirName);
        return dir.listFiles((dir1, filename) -> filename.endsWith(".csv"));
    }

    public static void main(String[] args){
        File[] x = finder(System.getProperty("java.io.tmpdir"));

        for (int i=0; i < x.length; i++) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(x[i]));
                String str = x[i].getName();
                ProcessBuilder processBuilder = new ProcessBuilder();
                String command = "mongoimport --db " + bd + " --collection "
                        + str.substring(0, str.lastIndexOf('.')) + " --type csv --file " + x[i] + " --fields " + br.readLine();
                System.out.println(command);
                processBuilder.command("cmd.exe", "/c", command);
                try {

                    Process process = processBuilder.start();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(process.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }

                    int exitCode = process.waitFor();
                    System.out.println("\nExited with error code : " + exitCode);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
