# Setup Guide - Bus Reservation System

## Prerequisites Check

You need either **Maven** OR an **IDE** (IntelliJ IDEA, Eclipse, VS Code with Java extensions) to run this project.

---

## Option 1: Install Maven (Recommended)

### Download & Install
1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract to: `C:\Program Files\Apache\maven`
3. Add to System PATH:
   - Open System Properties → Environment Variables
   - Edit `Path` variable
   - Add: `C:\Program Files\Apache\maven\bin`
4. Verify installation:
   ```bash
   mvn -version
   ```

### Then Run:
```bash
cd C:\Users\ARNAB\Desktop\Bus_Reservation
mvn clean install
mvn spring-boot:run
```

---

## Option 2: Use IDE (Easiest - No Maven Install Needed)

### IntelliJ IDEA (Community/Ultimate)
1. Open IntelliJ IDEA
2. File → Open → Select `Bus_Reservation` folder
3. Wait for Maven dependencies to download
4. Right-click on `BusReservationApplication.java`
5. Click "Run 'BusReservationApplication'"

### VS Code with Java Extension Pack
1. Install "Extension Pack for Java" in VS Code
2. Open `Bus_Reservation` folder
3. Open `BusReservationApplication.java`
4. Click "Run" button above the main method

### Eclipse/STS
1. File → Import → Maven → Existing Maven Projects
2. Select `Bus_Reservation` folder
3. Right-click project → Run As → Spring Boot App

---

## Option 3: Create Maven Wrapper (Run Once)

If you have Maven installed elsewhere or want a portable solution:

```bash
# This creates mvnw.cmd for Windows
mvn wrapper:wrapper
```

Then use:
```bash
.\mvnw.cmd spring-boot:run
```

---

## Database Setup

Before running, ensure MySQL is ready:

1. **Start MySQL Server** (port 3306)
2. **Create Database** (optional - app will auto-create):
   ```sql
   CREATE DATABASE bus_reservation;
   ```
3. **Update credentials** in `application.properties` if needed:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

---

## Quick Start (Using IDE - Easiest)

1. ✅ Install MySQL and start service
2. ✅ Download IntelliJ IDEA Community (free) or VS Code with Java
3. ✅ Open project in IDE
4. ✅ Run `BusReservationApplication.java`
5. ✅ Open browser: `http://localhost:8080`

---

## Troubleshooting

### Maven not found
- Install Maven OR use an IDE (no Maven install needed)

### MySQL Connection Error
- Check MySQL is running on port 3306
- Verify username/password in `application.properties`

### Port 8080 already in use
- Change port in `application.properties`:
  ```properties
  server.port=8081
  ```

---

## What's Next?

Once running:
1. Open `http://localhost:8080`
2. Click "Search Buses" to begin
3. Add buses via "Add Bus" menu
4. Test booking functionality

Enjoy your Bus Reservation System! 🚌
