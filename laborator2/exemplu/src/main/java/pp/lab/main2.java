package pp.lab;

import org.graalvm.polyglot.*;

import java.io.*;

class Polyglot2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        File inputFile = new File("dataset.txt");

        Context polyglot;

        String scriptPath = "regression.py";
        String datasetPath = inputFile.getAbsolutePath().replace("\\", "/");
        String outputFilePath;

        String pythonCode;

        System.out.print("Output file name (without extension): ");
        String outputFileName = bufferedReader.readLine();

        System.out.print("Path where the image is saved: ");
        String path = bufferedReader.readLine();

        System.out.print("Dot color (ex: blue, red): ");
        String scatterColor = bufferedReader.readLine();

        System.out.print("Regression line color (ex: green, orange): ");
        String lineColor = bufferedReader.readLine();

        outputFilePath = path.replace("\\", "/") + "/";
        pythonCode = String.format("""
                import sys, subprocess
                subprocess.run(['python3', '%s', '%s', '%s', '%s', '%s', '%s'])
                """, scriptPath, datasetPath, outputFileName, outputFilePath, scatterColor, lineColor);

        polyglot = Context.newBuilder().allowAllAccess(true).build();
        polyglot.eval("python", pythonCode);
        polyglot.close();

        try {
            String fullOutputFilePath = path.replace("\\", "/") + "/" + outputFileName + ".png";
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec("cmd /c start " + fullOutputFilePath); // Open .png file in windows operating system
            } else {
                Runtime.getRuntime().exec("xdg-open " + fullOutputFilePath); // Open .png file in non-windows operating system
            }
        } catch (IOException e) {
            System.out.println("Error opening image: " + e.getMessage());
        }
    }
}