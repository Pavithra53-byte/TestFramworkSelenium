## Prerequisites

Make sure the following tools are installed and properly configured in your system:

- Java 17 or above
- Maven (Apache Maven 3.6+)
- Chrome browser
- Git (optional)
- Internet connection (for Maven dependencies)
- A modern IDE like IntelliJ IDEA or VS Code (recommended)

---

## Setup Instructions

1. **Clone the Repository:**
```bash
git clone https://github.com/your-username/SeleniumTestFramework.git
cd SeleniumTestFramework
```

2. **Install Dependencies**

Maven will automatically install all dependencies when you compile or run the project.

```bash
mvn clean install
```

---

3. **Project Build**

```bash
mvn compile
```

---

4.  **Run Tests (Headless)**

To run your TestNG tests from the command line:

```bash
mvn test
```

---

5. **Run the UI Server**

This launches a Spark Java web server to allow running tests from a web browser.

```bash
mvn compile exec:java -Dexec.mainClass="demo.TestRunnerServer"
```

Once it’s running, open your browser and go to:

```
http://localhost:4567/
```

Click the **"Run Tests"** button to trigger the tests via Maven and view real-time output on the screen.

---

## 📄 File Descriptions

- **`TestRunnerServer.java`**: Starts a lightweight HTTP server using Spark Java. Provides a browser-based UI to trigger tests.
- **`GoogleTest.java`**: Sample Selenium test case using TestNG.
- **`TestUtils.java`**: Helper methods like wait logic, screenshot capture, etc.
- **`Constants.java`**: Constants used throughout the framework (e.g., URLs, timeouts).
- **`DriverFactory.java`**: Manages WebDriver instances for different browsers and provides a centralized driver creation logic.
- **`testng.xml`**: TestNG suite configuration file.
- **`pom.xml`**: Maven project configuration file with dependencies for Selenium, TestNG, Spark Java, and WebDriverManager.

---

## Author
M Pavithra

<img width="1841" height="915" alt="Screenshot 2025-08-08 114459" src="https://github.com/user-attachments/assets/9d362ae0-331d-45a2-8bad-f2e2c8c71717" />

