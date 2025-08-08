import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class TestRunnerServer {

    public static void main(String[] args) {
        port(4567);

        // Home Page with Run Button
        get("/", (req, res) -> {
            res.type("text/html");
            return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Selenium Test Runner</title>
                    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
                    <style>
                        body {
                            font-family: 'Roboto', sans-serif;
                            background-color: #f2f4f8;
                            text-align: center;
                            margin-top: 60px;
                            color: #333;
                        }
                        h2 {
                            color: #2c3e50;
                        }
                        button {
                            background-color: #3498db;
                            border: none;
                            color: white;
                            padding: 12px 24px;
                            font-size: 16px;
                            border-radius: 6px;
                            cursor: pointer;
                            transition: background-color 0.3s;
                        }
                        button:hover {
                            background-color: #2980b9;
                        }
                        form {
                            margin-top: 20px;
                        }
                    </style>
                </head>
                <body>
                    <h2>✅ Selenium Test Runner is Live</h2>
                    <p>Click the button below to run your Maven test cases.</p>
                    <form method="post" action="/run-tests">
                        <button type="submit">▶ Run Tests</button>
                    </form>
                </body>
                </html>
            """;
        });

        // Endpoint to trigger test run
        post("/run-tests", (req, res) -> {
            StringBuilder output = new StringBuilder();

            try {
                // 🛠️ Use your Maven installation path
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "C:\\Users\\malla\\Downloads\\apache-maven-3.9.6\\bin\\mvn.cmd", "test"
                );
                processBuilder.directory(new File(".")); // Run from project root
                processBuilder.redirectErrorStream(true); // Merge stdout + stderr

                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                process.waitFor(); // Wait for test execution to finish

            } catch (Exception e) {
                output.append("❌ Error occurred: ").append(e.getMessage());
            }

            // HTML-escape the test output
            String escapedOutput = output.toString()
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");

            res.type("text/html");
            return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Test Results</title>
                    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
                    <style>
                        body {
                            font-family: 'Roboto', sans-serif;
                            background-color: #f7f9fc;
                            padding: 30px;
                            color: #2c3e50;
                        }
                        h2 {
                            text-align: center;
                        }
                        .output {
                            white-space: pre-wrap;
                            background-color: #1e1e1e;
                            color: #d4d4d4;
                            padding: 20px;
                            border-radius: 8px;
                            font-family: monospace;
                            max-height: 500px;
                            overflow-y: auto;
                            margin-top: 30px;
                            box-shadow: 0 0 10px rgba(0,0,0,0.1);
                        }
                        .back {
                            margin-top: 20px;
                            text-align: center;
                        }
                        a {
                            color: #3498db;
                            text-decoration: none;
                        }
                        a:hover {
                            text-decoration: underline;
                        }
                    </style>
                </head>
                <body>
                    <h2>📄 Test Execution Results</h2>
                    <div class="output">""" + escapedOutput + "</div>" +
                "<div class='back'><br><a href='/'>← Back to Home</a></div>" +
                "</body></html>";
        });

        System.out.println("✅ TestRunnerServer started at http://localhost:4567");
    }
}
